package com.example.PFE.controller;
import com.example.PFE.config.PdfFooter;
import com.example.PFE.dto.AssignTechnicienRequest;
import com.example.PFE.model.*;

import com.example.PFE.repository.MachineRepo;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.security.Principal;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;

import org.springframework.web.bind.annotation.*;


import java.util.Date;

import com.example.PFE.model.Tissu;
import com.example.PFE.repository.ScanEventRepo;
import com.example.PFE.repository.TissuRepo;
import com.example.PFE.repository.UtilisateurRepository;
import com.example.PFE.service.FileStorageService;
import com.example.PFE.service.QrService;
import com.example.PFE.service.TimeService;
import com.example.PFE.service.UtilisateurService;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tissus")
public class TissuController {
    private final TissuRepo tissuRepo;
    private final ScanEventRepo scanRepo;
    private final TimeService timeService;
    private final FileStorageService fs;
    private final QrService qr;
    private final MachineRepo machineRepo;
    private final UtilisateurRepository utilisateurRepo;

    public TissuController(TissuRepo tissuRepo,MachineRepo machineRepo, ScanEventRepo scanRepo, TimeService timeService,
                           FileStorageService fs, QrService qr,
                           UtilisateurRepository utilisateurRepo) {
        this.tissuRepo=tissuRepo; this.scanRepo=scanRepo; this.timeService=timeService; this.fs=fs; this.qr=qr;
        this.utilisateurRepo = utilisateurRepo; this.machineRepo = machineRepo;
    }



    @GetMapping
    public List<Tissu> list(@RequestParam(required=false) String q){
        if (q==null || q.isBlank()) return tissuRepo.findAll();
        return tissuRepo.findByCodeContainingIgnoreCase(q);
    }

    // ✅ création tissu + (option) upload fichier


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Tissu createMultipart(@RequestPart("data") Tissu tissu,
                                 @RequestPart(value="file", required=false) MultipartFile file) throws Exception {

        tissu.setCreatedAt(new Date());
        tissu.setUpdatedAt(new Date());
        tissu.setStatut(StatutTissu.EN_STOCK);

        // vérifier email client
        if (tissu.getClientEmail() == null || tissu.getClientEmail().isBlank()) {
            throw new RuntimeException("Email client obligatoire");
        }

        // convertir email en lowercase
        String email = tissu.getClientEmail().toLowerCase();

        // vérifier que le client existe
        Utilisateur client = utilisateurRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client introuvable: " + email));

        // enregistrer les infos client
        tissu.setClientEmail(email);
        tissu.setClientId(client.getId());
        tissu.setClientNom(client.getNom()); // adapte حسب champ dans Utilisateur

        if (tissu.getDemande() != null)
            tissu.getDemande().setDateLancement(null);

        if (tissu.getCurrentStepIndex() == null)
            tissu.setCurrentStepIndex(0);

        if (file != null && !file.isEmpty()) {
            String path = fs.save(file, "demande");
            tissu.setDemandeFilePath(path);
        }

        return tissuRepo.save(tissu);
    }
    // ✅ download fichier demande uploadé



    @GetMapping("/{id}/demande-pdf")
    public ResponseEntity<Resource> generateDemandePdf(@PathVariable String id) throws Exception {

        Tissu t = tissuRepo.findById(id).orElseThrow();
        DemandeLancement d = t.getDemande(); // ✅ peut être null

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 36, 36, 36, 36);PdfWriter writer = PdfWriter.getInstance(doc, baos);
        writer.setPageEvent(new PdfFooter());
        doc.open();
// 📌 Ajouter le logo


        URL urlLeft  = getClass().getResource("/static/loo.png");
        URL urlRight = getClass().getResource("/static/cettex.png");

        if (urlLeft == null)  throw new RuntimeException("Logo gauche introuvable !");
        if (urlRight == null) throw new RuntimeException("Logo droite introuvable !");

        Image logoLeft = Image.getInstance(urlLeft);
        Image logoRight = Image.getInstance(urlRight);

        logoLeft.scaleToFit(120, 60);
        logoRight.scaleToFit(120, 60);

// ✅ tableau 2 colonnes
        PdfPTable header = new PdfPTable(2);
        header.setWidthPercentage(100);
        header.setWidths(new float[]{1f, 1f}); // gauche / droite

        PdfPCell leftCell = new PdfPCell(logoLeft);
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        PdfPCell rightCell = new PdfPCell(logoRight);
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        header.addCell(leftCell);
        header.addCell(rightCell);

// ✅ ajouter en haut du document
        doc.add(header);
        doc.add(new Paragraph(" ")); // petit espace sous les logos














        doc.add(new Paragraph(" ")); // espace
        Font title = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font h = new Font(Font.HELVETICA, 12, Font.BOLD);
        Font normal = new Font(Font.HELVETICA, 11);






        // ✅ Titre
        Paragraph pTitle = new Paragraph("DEMANDE DE LANCEMENT", title);
        pTitle.setAlignment(Element.ALIGN_CENTER);
        doc.add(pTitle);
        doc.add(new Paragraph(" "));

        // ✅ Infos tissu
        doc.add(new Paragraph("Article : " + safe(t.getCode()), h));
        doc.add(new Paragraph("Statut : " + safe(String.valueOf(t.getStatut())), normal));
        doc.add(new Paragraph("Créé le : " + formatDate(df, t.getCreatedAt()), normal));
        doc.add(new Paragraph("Mis à jour : " + formatDate(df, t.getUpdatedAt()), normal));
        doc.add(new Paragraph(" "));

        // ✅ Tableau demande
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setWidths(new float[]{30f, 70f});

        addRow(table, "Date réception", d == null ? "" : formatDate(df, d.getDateReception()));
        addRow(table, "Date lancement", d == null ? "" : formatDate(df, d.getDateLancement()));
        addRow(table, "Délai", d == null ? "" : safe(d.getDelai()));
        addRow(table, "Support", d == null ? "" : safe(d.getSupport()));
        addRow(table, "Composition", d == null ? "" : safe(d.getComposition()));
        addRow(table, "Process", d == null ? "" : safe(d.getProcess()));
        addRow(table, "N° Demande", d == null ? "" : safe(d.getNumeroDemande()));
        addRow(table, "Disignation", d == null ? "" : safe(d.getDisignateur()));
        addRow(table, "Client", d == null ? "" : safe(d.getClient()));
        addRow(table, "Email Client", t == null ? "" : safe(t.getClientEmail()));
        addRow(table, "Réf support client", d == null ? "" : safe(d.getReferenceSupportClient()));
        addRow(table, "couleur Envoyee", d == null ? "" : safe(d.getCouleurEnvoyee()));
        addRow(table, "Couleur Demandee", d == null ? "" : safe(d.getColorantDemandee()));
        addRow(table, "Standard Client", d == null ? "" : safe(d.getStandardClient()));
        addRow(table, "Recette", d == null ? "" : safe(d.getCodeRecette()));
        addRow(table, "Quantité", d == null ? "" : safe(d.getDimensions()));
        addRow(table, "Prix", d == null ? "" : safe(d.getPrix()));
        addRow(table, "Remarque", d == null ? "" : safe(d.getRemarques()));
        doc.add(table);

        // ✅ Workflow machines (routeMachineIds)
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Workflow machines (ordre) :", h));
        if (t.getRouteMachineIds() == null || t.getRouteMachineIds().isEmpty()) {
            doc.add(new Paragraph("-", normal));
        } else {
            com.lowagie.text.List list = new com.lowagie.text.List(com.lowagie.text.List.ORDERED);
            int i = 1;
            for (String mid : t.getRouteMachineIds()) {
                String machineCode = machineRepo.findById(mid)
                        .map(m -> m.getCode())
                        .orElse(mid);

                String line = i + ". " + safe(machineCode);
                if (t.getCurrentStepIndex() != null && t.getCurrentStepIndex() == (i - 1)) {
                    line += "   (étape actuelle)";
                }

                list.add(new ListItem(line, normal));
                i++;
            }
            doc.add(list);
        }

        // ✅ Remarques
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Remarques :", h));
        doc.add(new Paragraph(d == null ? "" : safe(d.getRemarques()), normal));

        // ✅ Livraison
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Livraison :", h));
        doc.add(new Paragraph("Livré le : " + formatDate(df, t.getDeliveredAt()), normal));

        doc.close();

        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=demande_" + safeFilename(t.getCode()) + ".pdf")
                .body(resource);
    }

    private static void addRow(PdfPTable table, String label, String value) {
        Font labelFont = new Font(Font.HELVETICA, 11, Font.BOLD);
        Font valueFont = new Font(Font.HELVETICA, 11);

        PdfPCell c1 = new PdfPCell(new Phrase(label, labelFont));
        c1.setPadding(6);
        c1.setBackgroundColor(new java.awt.Color(240, 240, 240));
        table.addCell(c1);

        PdfPCell c2 = new PdfPCell(new Phrase(value == null ? "" : value, valueFont));
        c2.setPadding(6);
        table.addCell(c2);
    }

    private static String safe(String s) { return s == null ? "" : s; }

    private static String formatDate(SimpleDateFormat df, java.util.Date d) {
        return d == null ? "" : df.format(d);
    }

    private static String safeFilename(String s) {
        if (s == null || s.isBlank()) return "tissu";
        return s.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }
    // ✅ QR tissu
    @GetMapping("/{id}/qr")
    public ResponseEntity<byte[]> qrTissu(@PathVariable String id) throws Exception {
        Tissu t = tissuRepo.findById(id).orElseThrow();
        byte[] png = qr.generatePng("TISSU:"+t.getId(), 300);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(png);

    }

    // ✅ détails : temps par machine + events
    @GetMapping("/{id}/details")
    public Map<String,Object> details(@PathVariable String id){
        Tissu t = tissuRepo.findById(id).orElseThrow();
        var totals = timeService.computeSecondsByMachine(id);
        var events = scanRepo.findByTissuIdOrderByTsAsc(id);
        return Map.of("tissu", t, "secondsByMachine", totals, "events", events);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tissu> updateSimple(
            @PathVariable String id,
            @RequestBody Tissu updated
    ) {

        Tissu existing = tissuRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tissu non trouvé"));

        existing.setCode(updated.getCode());
        existing.setUpdatedAt(new Date());

        if (updated.getDemande() != null) {

            if (existing.getDemande() == null) {
                existing.setDemande(new DemandeLancement());
            }

            DemandeLancement oldD = existing.getDemande();
            DemandeLancement newD = updated.getDemande();

            oldD.setSupport(newD.getSupport());
            oldD.setReferenceSupportClient(newD.getReferenceSupportClient());
            oldD.setCodeRecette(newD.getCodeRecette());
            oldD.setTypeColorant(newD.getTypeColorant());
            oldD.setDimensions(newD.getDimensions());
            oldD.setNumeroDemande(newD.getNumeroDemande());
            oldD.setClient(newD.getClient());
            oldD.setDelai(newD.getDelai());
            oldD.setRemarques(newD.getRemarques());
        }

        return ResponseEntity.ok(tissuRepo.save(existing));
    }
    @GetMapping("/by-client/{clientId}")
    public List<Tissu> byClient(@PathVariable String clientId) {
        return tissuRepo.findByClientId(clientId);
    }

    // GET /api/tissus/by-technicien/{techId}
    @GetMapping("/by-technicien/{techId}")
    public List<Tissu> byTechnicien(@PathVariable String techId) {
        return tissuRepo.findByLastTechnicienId(techId);
    }
    @PatchMapping("/{id}/assign-technicien")
    public ResponseEntity<Tissu> assignTechnicien(
            @PathVariable String id,
            @RequestBody AssignTechnicienRequest req
    ) {
        Tissu t = tissuRepo.findById(id).orElse(null);
        if (t == null) return ResponseEntity.notFound().build();

        if (req.getTechnicienId() == null || req.getTechnicienId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        t.setLastTechnicienId(req.getTechnicienId());
        t.setLastTechnicienNom(req.getTechnicienNom());

        // exemple: statut
        t.setStatut(StatutTissu.EN_STOCK);

        t.setUpdatedAt(new Date());
        return ResponseEntity.ok(tissuRepo.save(t));
    }



//    @GetMapping("/my")
//    public List<Tissu> myTissus(Principal principal) {
//
//        if (principal == null) {
//            throw new RuntimeException("Non authentifié");
//        }
//
//        // email du client depuis JWT
//        String email = principal.getName();
//        if (email == null) throw new RuntimeException("Email introuvable");
//
//        // ✅ lowercase pour matcher ce que tu as enregistré dans Tissu
//        email = email.toLowerCase();
//
//        // ✅ On filtre directement par email stocké dans le tissu
//        return tissuRepo.findByClientEmail(email);
//    }

    @GetMapping("/my")
    public List<Tissu> myTissus(
            Principal principal,
            @RequestParam(required = false) String q
    ) {
        if (principal == null) {
            throw new RuntimeException("Non authentifié");
        }

        String email = principal.getName();
        if (email == null) throw new RuntimeException("Email introuvable");

        email = email.toLowerCase();

        List<Tissu> tissus = tissuRepo.findByClientEmail(email);

        if (q == null || q.isBlank()) {
            return tissus;
        }

        String search = q.toLowerCase().trim();

        return tissus.stream()
                .filter(t -> {
                    DemandeLancement d = t.getDemande();

                    String code = t.getCode() == null ? "" : t.getCode().toLowerCase();

                    String ref = d == null || d.getReferenceSupportClient() == null
                            ? ""
                            : d.getReferenceSupportClient().toLowerCase();

                    String support = d == null || d.getSupport() == null
                            ? ""
                            : d.getSupport().toLowerCase();

                    String dateReception = d == null || d.getDateReception() == null
                            ? ""
                            : new java.text.SimpleDateFormat("dd/MM/yyyy").format(d.getDateReception()).toLowerCase();

                    String dateReceptionIso = d == null || d.getDateReception() == null
                            ? ""
                            : new java.text.SimpleDateFormat("yyyy-MM-dd").format(d.getDateReception()).toLowerCase();

                    return code.contains(search)
                            || ref.contains(search)
                            || support.contains(search)
                            || dateReception.contains(search)
                            || dateReceptionIso.contains(search);
                })
                .toList();
    }
    @GetMapping("/{id}/details-client")
    public Map<String, Object> detailsClient(@PathVariable String id, Principal principal) {

        if (principal == null) throw new RuntimeException("Non authentifié");

        String email = principal.getName();
        if (email == null) throw new RuntimeException("Email introuvable");
        email = email.toLowerCase();

        Tissu t = tissuRepo.findById(id).orElseThrow();

        // ✅ Vérifier que ce tissu appartient au client (par email)
        if (t.getClientEmail() == null || !t.getClientEmail().equalsIgnoreCase(email)) {
            throw new RuntimeException("Accès interdit");
        }

        var totals = timeService.computeSecondsByMachine(id);
        var events = scanRepo.findByTissuIdOrderByTsAsc(id);

        return Map.of("tissu", t, "secondsByMachine", totals, "events", events);
    }
}

package com.example.PFE.service;

import com.example.PFE.model.Rappel;
import com.example.PFE.repository.RappelRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class RappelService {

    private final RappelRepository repo;
    private final MailrappelService mail;

    public RappelService(RappelRepository repo, MailrappelService mail) {
        this.repo = repo;
        this.mail = mail;
    }

    // CRUD helpers
    public List<Rappel> list(String q) {
        var all = repo.findAll();
        if (q == null || q.isBlank()) return all;
        String s = q.toLowerCase();
        return all.stream().filter(r ->
                (r.getNom() != null && r.getNom().toLowerCase().contains(s)) ||
                        (r.getDescription() != null && r.getDescription().toLowerCase().contains(s))
        ).toList();
    }

    public Rappel create(Rappel r) {
        r.setId(null);
        r.setDone(false);
        r.setCreatedAt(new Date());
        r.setUpdatedAt(new Date());
        return repo.save(r);
    }

    public Rappel update(String id, Rappel body) {
        return repo.findById(id).map(r -> {
            r.setNom(body.getNom());
            r.setDescription(body.getDescription());
            r.setAdminEmail(body.getAdminEmail());
            r.setTechnicienEmail(body.getTechnicienEmail());
            r.setProchaineDate(body.getProchaineDate());
            r.setFrequenceJours(body.getFrequenceJours());
            r.setUpdatedAt(new Date());
            return repo.save(r);
        }).orElseThrow(() -> new RuntimeException("Rappel introuvable"));
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public Rappel markDone(String id, boolean done) {
        return repo.findById(id).map(r -> {
            r.setDone(done);
            r.setUpdatedAt(new Date());

            // Si done = true et rappel récurrent => calcul prochaine date
            if (done && r.getFrequenceJours() != null && r.getFrequenceJours() > 0) {
                r.setProchaineDate(r.getProchaineDate().plusDays(r.getFrequenceJours()));
                r.setDone(false); // redevient actif pour le prochain cycle
                // reset flags mails
                r.setLastPreSent(null);
                r.setLastDueSent(null);
                r.setLastEscalSent(null);
            }
            return repo.save(r);
        }).orElseThrow(() -> new RuntimeException("Rappel introuvable"));
    }

    // ✅ Scheduler: tourne chaque heure (tu peux mettre chaque 10 min si tu veux)
    @Scheduled(cron = "0 * * * * *")

    public void processRappels() {
        LocalDate today = LocalDate.now();

        List<Rappel> actifs = repo.findByDoneFalse();

        for (Rappel r : actifs) {
            if (r.getProchaineDate() == null) continue;

            LocalDate due = r.getProchaineDate();
            LocalDate pre = due.minusDays(1);

            // 1) J-1
            if (today.equals(pre) && (r.getLastPreSent() == null || !r.getLastPreSent().equals(today))) {
                sendToBoth(r, "🔔 Rappel maintenance pour demain  (J-1)",
                        "Bonjour,\n\nRappel: " + r.getNom() +
                                "\nDate: " + due +
                                "\nDescription: " + safe(r.getDescription()) +
                                "\n\nMerci.");
                r.setLastPreSent(today);
                r.setUpdatedAt(new Date());
                repo.save(r);
            }

            // 2) Jour J
            if (today.equals(due) && (r.getLastDueSent() == null || !r.getLastDueSent().equals(today))) {
                sendToBoth(r, "🚨 Maintenance pour aujourd'hui  (Jour J)",
                        "Bonjour,\n\nMaintenance : " + r.getNom() +
                                "\nDate: " + due +
                                "\nDescription: " + safe(r.getDescription()) +
                                "\n\nMerci de confirmer DONE dans l'application.");
                r.setLastDueSent(today);
                r.setUpdatedAt(new Date());
                repo.save(r);
            }

            // 3) Escalation si pas done (ex: lendemain du due)
            LocalDate escal = due.plusDays(1);
            if (today.equals(escal) && (r.getLastEscalSent() == null || !r.getLastEscalSent().equals(today))) {
                mail.send(r.getAdminEmail(),
                        "❗ Escalation: maintenance non confirmée",
                        "Bonjour,\n\nLe rappel \"" + r.getNom() + "\" n'est pas marqué DONE.\n" +
                                "Date prévue: " + due + "\n\nMerci de vérifier.");
                r.setLastEscalSent(today);
                r.setUpdatedAt(new Date());
                repo.save(r);
            }
        }
    }

    private void sendToBoth(Rappel r, String subject, String text) {
        // admin
        mail.send(r.getAdminEmail(), subject, text);
        // technicien (optionnel)
        if (r.getTechnicienEmail() != null && !r.getTechnicienEmail().isBlank()) {
            mail.send(r.getTechnicienEmail(), subject, text);
        }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
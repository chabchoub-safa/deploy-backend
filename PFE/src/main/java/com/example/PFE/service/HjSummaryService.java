package com.example.PFE.service;

import com.example.PFE.dto.HjSummaryRow;
import com.example.PFE.model.*;
import com.example.PFE.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HjSummaryService {

    private final AssTechRepository assTechRepository;
    private final DiagnosticRepository diagnosticRepository;
    private final FormationRepository formationRepository;
    private final SuiviITPRepository suiviITPRepository;
    private final SuiviPlanActionRepository suiviPlanActionRepository;

    public HjSummaryService(
            AssTechRepository assTechRepository,
            DiagnosticRepository diagnosticRepository,
            FormationRepository formationRepository,
            SuiviITPRepository suiviITPRepository,
            SuiviPlanActionRepository suiviPlanActionRepository
    ) {
        this.assTechRepository = assTechRepository;
        this.diagnosticRepository = diagnosticRepository;
        this.formationRepository = formationRepository;
        this.suiviITPRepository = suiviITPRepository;
        this.suiviPlanActionRepository = suiviPlanActionRepository;
    }

    public List<HjSummaryRow> buildSummary() {
        Map<String, HjSummaryRow> map = new LinkedHashMap<>();

        for (SuiviITP item : suiviITPRepository.findAll()) {
            addRow(map, item.getCategorie(), "ITS", item.getComplements());
        }

        for (SuiviPlanAction item : suiviPlanActionRepository.findAll()) {
            addRow(map, item.getCategorie(), "SUIVI PLAN ACTION", item.getComplements());
        }

        for (AssTech item : assTechRepository.findAll()) {
            addRow(map, item.getCat(), "ASS TECH", item.getComplements());
        }

        for (Diagnostic item : diagnosticRepository.findAll()) {
            addRow(map, item.getCat(), "DIAGNOSTIC", item.getComplements());
        }

        for (Formation item : formationRepository.findAll()) {
            addRow(map, item.getCatThemes(), "FORMATION", item.getComplements());
        }

        return new ArrayList<>(map.values());
    }
    private void addRow(
            Map<String, HjSummaryRow> map,
            String cat,
            String nature,
            List<Complement> complements
    ) {
        String safeCat = normalizeCat(cat);
        String safeNature = normalizeNature(nature);

        String key = safeCat + "||" + safeNature;

        HjSummaryRow row = map.computeIfAbsent(
                key,
                k -> new HjSummaryRow(safeCat, safeNature)
        );

        if (complements == null || complements.isEmpty()) {
            return;
        }

        for (Complement c : complements) {
            if (c == null) continue;

            String nomOriginal = c.getNom() == null ? "" : c.getNom().trim();
            double valeur = parseDouble(c.getValeur());

            if (nomOriginal.isEmpty() || valeur == 0) continue;

            row.addValeur(nomOriginal, valeur);
        }
    }
//    private void addRow(
//            Map<String, HjSummaryRow> map,
//            String cat,
//            String nature,
//            List<Complement> complements
//    ) {
//        String safeCat = normalizeCat(cat);
//        String safeNature = normalizeNature(nature);
//
//        String key = safeCat + "||" + safeNature;
//
//        HjSummaryRow row = map.computeIfAbsent(
//                key,
//                k -> new HjSummaryRow(safeCat, safeNature)
//        );
//
//        if (complements == null || complements.isEmpty()) {
//            return;
//        }
//
//        for (Complement c : complements) {
//            if (c == null) continue;
//
//            String nom = normalizeName(c.getNom());
//            double valeur = parseDouble(c.getValeur());
//
//            if (valeur == 0) continue;
//            for (Complement c : complements) {
//                if (c == null) continue;
//
//                String nomOriginal = c.getNom() == null ? "" : c.getNom().trim();
//                double valeur = parseDouble(c.getValeur());
//
//                if (nomOriginal.isEmpty() || valeur == 0) continue;
//
//                row.addValeur(nomOriginal, valeur);
//            }
////            switch (nom) {
////                case "abdlhmid":
////                case "abdelhamid":
////                case "abdelhmid":
////
////                    row.setAbdlhmid(row.getAbdlhmid() + valeur);
////                    break;
////
////                case "insaf":
////                    row.setInsaf(row.getInsaf() + valeur);
////                    break;
////
////                case "rachida":
////                    row.setRachida(row.getRachida() + valeur);
////                    break;
////
////                case "majdi":
////                    row.setMajdi(row.getMajdi() + valeur);
////                    break;
////
////                case "chourouk":
////                    row.setChourouk(row.getChourouk() + valeur);
////                    break;
////
////                default:
////                    System.out.println("Nom complément non reconnu : " + c.getNom());
////                    break;
////            }
//        }
//    }

    private String normalizeCat(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "SANS CAT";
        }
        return value.trim().toUpperCase();
    }

    private String normalizeNature(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "SANS NATURE";
        }
        return value.trim().toUpperCase();
    }

//    private String normalizeName(String value) {
//        if (value == null) return "";
//
//        return value
//                .trim()
//                .toLowerCase()
//                .replace(" ", "")
//                .replace("_", "")
//                .replace("-", "");
//    }

    private double parseDouble(String value) {
        if (value == null) return 0.0;

        String v = value.trim();
        if (v.isEmpty()) return 0.0;

        v = v.replace(",", ".");

        try {
            return Double.parseDouble(v);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
package com.example.PFE.service;

import com.example.PFE.dto.GeneralResumeRowDto;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GeneralResumeService {

    private final MongoTemplate mongoTemplate;

    public GeneralResumeService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<GeneralResumeRowDto> getResume(Double multiplicateur) {
        double mult = (multiplicateur == null || multiplicateur <= 0) ? 350.0 : multiplicateur;

        // ✅ Champs corrects selon chaque collection
        Map<String, Totals> diagMap = aggregateByCat("diagnostics", "cat", "hj", "caDtHt");
        Map<String, Totals> assTechMap = aggregateByCat("ass_tech", "cat", "hj", "caDt");
        Map<String, Totals> formationMap = aggregateByCat("formations", "catThemes", "hj", "montantDt");

        Set<String> allCats = new TreeSet<>();
        allCats.addAll(diagMap.keySet());
        allCats.addAll(assTechMap.keySet());
        allCats.addAll(formationMap.keySet());

        List<GeneralResumeRowDto> rows = new ArrayList<>();

        for (String cat : allCats) {
            Totals formation = formationMap.getOrDefault(cat, new Totals());
            Totals assTech = assTechMap.getOrDefault(cat, new Totals());
            Totals diagnostic = diagMap.getOrDefault(cat, new Totals());

            // FORMATION
            rows.add(new GeneralResumeRowDto(
                    cat,
                    "FORMATION",
                    formation.hj,          // colonne HJ = somme hj
                    formation.dt,          // colonne DT = somme montantDt
                    formation.dt * mult    // total = somme hj * 350
            ));

            // ASS TECH
            rows.add(new GeneralResumeRowDto(
                    cat,
                    "ASS-TECH",
                    assTech.hj,            // colonne HJ = somme hj
                    assTech.dt,            // colonne DT = somme caDt
                    assTech.dt * mult      // total = somme dt * 350
            ));

            // DIAGNOSTIC
            rows.add(new GeneralResumeRowDto(
                    cat,
                    "DIAGNOSTIC",
                    diagnostic.hj,         // colonne HJ = somme hj
                    diagnostic.dt,         // colonne DT = somme caDtHt
                    diagnostic.dt * mult   // total = somme dt * 350
            ));
        }

        return rows;
    }

    private Map<String, Totals> aggregateByCat(
            String collectionName,
            String catField,
            String hjField,
            String dtField
    ) {
        List<Document> docs = mongoTemplate.findAll(Document.class, collectionName);
        Map<String, Totals> grouped = new LinkedHashMap<>();

        for (Document d : docs) {
            String cat = readString(d, catField);

            if (cat == null || cat.isBlank()) continue;

            double hj = readNumber(d, hjField);
            double dt = readNumber(d, dtField);

            Totals totals = grouped.computeIfAbsent(cat.trim(), k -> new Totals());
            totals.hj += hj;
            totals.dt += dt;
        }

        return grouped;
    }

    private String readString(Document d, String field) {
        Object v = d.get(field);
        if (v == null) return null;

        String s = String.valueOf(v).trim();
        if (s.isBlank() || s.equalsIgnoreCase("null")) return null;

        return s;
    }

    private double readNumber(Document d, String field) {
        Object v = d.get(field);
        if (v == null) return 0;

        if (v instanceof Number n) {
            return n.doubleValue();
        }

        try {
            String s = String.valueOf(v).trim().replace(",", ".");
            if (s.isBlank()) return 0;
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0;
        }
    }

    private static class Totals {
        double hj = 0;
        double dt = 0;
    }
}
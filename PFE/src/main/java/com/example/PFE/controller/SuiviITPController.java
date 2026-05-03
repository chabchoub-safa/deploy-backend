package com.example.PFE.controller;

import com.example.PFE.model.SuiviITP;
import com.example.PFE.service.SuiviITPService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suivi-itp")

public class SuiviITPController {

    private final SuiviITPService service;

    public SuiviITPController(SuiviITPService service) {
        this.service = service;
    }

    // Liste + recherche optionnelle
    // Exemple: GET /api/suivi-itp?q=test
    @GetMapping
    public ResponseEntity<List<SuiviITP>> getAll(
            @RequestParam(required = false) String q
    ) {
        return ResponseEntity.ok(service.getAll(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuiviITP> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuiviITP> create(@RequestBody SuiviITP item) {
        return ResponseEntity.ok(service.create(item));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuiviITP> update(
            @PathVariable String id,
            @RequestBody SuiviITP item
    ) {
        return ResponseEntity.ok(service.update(id, item));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
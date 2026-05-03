package com.example.PFE.controller;

import com.example.PFE.model.Diagnostic;
import com.example.PFE.service.DiagnosticService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnostics")

public class DiagnosticController {

    private final DiagnosticService service;

    public DiagnosticController(DiagnosticService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Diagnostic>> getAll(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(service.getAll(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diagnostic> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Diagnostic> create(@RequestBody Diagnostic item) {
        return ResponseEntity.ok(service.create(item));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Diagnostic> update(@PathVariable String id, @RequestBody Diagnostic item) {
        return ResponseEntity.ok(service.update(id, item));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
package com.example.PFE.controller;

import com.example.PFE.model.Formation;
import com.example.PFE.service.FormationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formations")

public class FormationController {

    private final FormationService service;

    public FormationController(FormationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Formation>> getAll(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(service.getAll(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Formation> create(@RequestBody Formation item) {
        return ResponseEntity.ok(service.create(item));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Formation> update(@PathVariable String id, @RequestBody Formation item) {
        return ResponseEntity.ok(service.update(id, item));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
package com.example.PFE.controller;

import com.example.PFE.model.SuiviPlanAction;
import com.example.PFE.service.SuiviPlanActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suivi-plan-action")

public class SuiviPlanActionController {

    private final SuiviPlanActionService service;

    public SuiviPlanActionController(SuiviPlanActionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SuiviPlanAction>> getAll(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(service.getAll(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuiviPlanAction> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuiviPlanAction> create(@RequestBody SuiviPlanAction item) {
        return ResponseEntity.ok(service.create(item));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuiviPlanAction> update(@PathVariable String id, @RequestBody SuiviPlanAction item) {
        return ResponseEntity.ok(service.update(id, item));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
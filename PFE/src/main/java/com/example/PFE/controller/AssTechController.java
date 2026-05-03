package com.example.PFE.controller;

import com.example.PFE.model.AssTech;
import com.example.PFE.service.AssTechService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ass-tech")

public class AssTechController {

    private final AssTechService service;

    public AssTechController(AssTechService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AssTech>> getAll(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(service.getAll(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssTech> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AssTech> create(@RequestBody AssTech item) {
        return ResponseEntity.ok(service.create(item));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AssTech> update(@PathVariable String id, @RequestBody AssTech item) {
        return ResponseEntity.ok(service.update(id, item));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
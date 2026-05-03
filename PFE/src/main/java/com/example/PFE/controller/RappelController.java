package com.example.PFE.controller;


import com.example.PFE.model.Rappel;
import com.example.PFE.service.RappelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rappels")
public class RappelController {

    private final RappelService service;

    public RappelController(RappelService service) {
        this.service = service;
    }

    @GetMapping
    public List<Rappel> list(@RequestParam(required = false) String q) {
        return service.list(q);
    }

    @PostMapping
    public Rappel create(@RequestBody Rappel r) {
        return service.create(r);
    }

    @PutMapping("/{id}")
    public Rappel update(@PathVariable String id, @RequestBody Rappel r) {
        return service.update(id, r);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @PatchMapping("/{id}/done")
    public Rappel done(@PathVariable String id, @RequestParam boolean value) {
        return service.markDone(id, value);
    }
}
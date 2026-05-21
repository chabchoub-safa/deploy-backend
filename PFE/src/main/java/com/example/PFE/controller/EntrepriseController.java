package com.example.PFE.controller;

import com.example.PFE.dto.EntrepriseStatsDTO;
import com.example.PFE.model.Entreprise;
import com.example.PFE.service.EntrepriseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entreprises")
@CrossOrigin(origins = "*")
public class EntrepriseController {

    private final EntrepriseService service;

    public EntrepriseController(EntrepriseService service) {
        this.service = service;
    }

//    @GetMapping
//    public List<EntrepriseStatsDTO> getAll() {
//        return service.getAll();
//    }
@GetMapping
public List<EntrepriseStatsDTO> getAll(@RequestParam(defaultValue = "false") boolean trash) {
    if (trash) {
        return service.getTrash();
    }
    return service.getActive();
}
    @PutMapping("/{id}/restore")
    public Entreprise restore(@PathVariable String id) {
        return service.restore(id);
    }
    @GetMapping("/{id}")
    public EntrepriseStatsDTO getById(@PathVariable String id) {
        return service.getDetailsWithStats(id);
    }

    @PostMapping
    public Entreprise create(@RequestBody Entreprise entreprise) {
        return service.create(entreprise);
    }

    @PutMapping("/{id}")
    public Entreprise update(@PathVariable String id, @RequestBody Entreprise entreprise) {
        return service.update(id, entreprise);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<EntrepriseStatsDTO> search(@RequestParam String q) {
        return service.search(q);
    }
}
package com.example.PFE.controller;

import com.example.PFE.dto.ConsumptionDTO;
import com.example.PFE.model.Machine;
import com.example.PFE.repository.MachineRepo;
import com.example.PFE.service.QrService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/machines")
public class MachineController {
    private final MachineRepo repo;
    private final QrService qr;

    public MachineController(MachineRepo repo, QrService qr){ this.repo=repo; this.qr=qr; }

    @GetMapping
    public List<Machine> list(@RequestParam(required=false) String q){
        if (q==null || q.isBlank()) return repo.findByDeletedFalseOrderByCreatedAtDesc();
        return repo.findByDeletedFalseAndCodeContainingIgnoreCase(q);
    }

    @PostMapping
    public Machine create(@RequestBody Machine m){
        m.setDeleted(false);
        m.setCreatedAt(new Date());
        return repo.save(m);
    }

    @PutMapping("/{id}")
    public Machine update(@PathVariable String id, @RequestBody Machine upd){
        Machine m = repo.findById(id).orElseThrow();
        m.setCode(upd.getCode());
        m.setNom(upd.getNom());
        return repo.save(m);
    }

    @DeleteMapping("/{id}")
    public void softDelete(@PathVariable String id){
        Machine m = repo.findById(id).orElseThrow();
        m.setDeleted(true);
        repo.save(m);
    }
    @GetMapping("/deleted")
    public List<Machine> listDeleted(){
        return repo.findByDeletedTrueOrderByCreatedAtDesc();
    }

    @PatchMapping("/{id}/restore")
    public Machine restore(@PathVariable String id){
        Machine m = repo.findById(id).orElseThrow();
        m.setDeleted(false);
        m.setUpdatedAt(new Date());
        return repo.save(m);
    }

    @GetMapping("/{id}/qr")
    public ResponseEntity<byte[]> qrMachine(@PathVariable String id) throws Exception {
        Machine m = repo.findById(id).orElseThrow();
        byte[] png = qr.generatePng("MACHINE:"+m.getId(), 300);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(png);
    }
    // ✅ DETAILS
    @GetMapping("/{id}")
    public Machine get(@PathVariable String id) {
        Machine m = repo.findById(id).orElseThrow(() -> new RuntimeException("Machine introuvable"));
        if (m.isDeleted()) throw new RuntimeException("Machine supprimée");
        return m;
    }
    // ✅ TOGGLE ON/OFF
    @PatchMapping("/{id}/toggle")
    public Machine toggle(@PathVariable String id) {
        Machine m = repo.findById(id).orElseThrow(() -> new RuntimeException("Machine introuvable"));
        if (m.isDeleted()) throw new RuntimeException("Machine supprimée");

        m.setActif(!m.isActif());
        m.setUpdatedAt(new Date());
        return repo.save(m);
    }

    // ✅ MANUAL CONSUMPTION (test)
    @PutMapping("/{id}/consumption")
    public Machine setConsumption(@PathVariable String id, @RequestBody ConsumptionDTO dto) {
        Machine m = repo.findById(id).orElseThrow(() -> new RuntimeException("Machine introuvable"));
        if (m.isDeleted()) throw new RuntimeException("Machine supprimée");

        m.setLastWaterLiters(dto.waterLiters);
        m.setLastCurrentWatts(dto.currentWatts);
        m.setUpdatedAt(new Date());
        return repo.save(m);
    }
}

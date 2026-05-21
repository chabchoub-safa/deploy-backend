//package com.example.PFE.controller;
//
//import com.example.PFE.model.ComplementOption;
//import com.example.PFE.service.ComplementOptionService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/complements")
//public class ComplementOptionController {
//
//    private final ComplementOptionService service;
//
//    public ComplementOptionController(ComplementOptionService service) {
//        this.service = service;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ComplementOption>> getAll() {
//        return ResponseEntity.ok(service.getAll());
//    }
//
//    @PostMapping
//    public ResponseEntity<ComplementOption> create(@RequestBody ComplementOption item) {
//        return ResponseEntity.ok(service.create(item));
//    }
//}
package com.example.PFE.controller;

import com.example.PFE.model.ComplementOption;
import com.example.PFE.service.ComplementOptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complements")
public class ComplementOptionController {

    private final ComplementOptionService service;

    public ComplementOptionController(ComplementOptionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ComplementOption>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/trash")
    public ResponseEntity<List<ComplementOption>> getTrash() {
        return ResponseEntity.ok(service.getTrash());
    }

    @PostMapping
    public ResponseEntity<ComplementOption> create(@RequestBody ComplementOption item) {
        return ResponseEntity.ok(service.create(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDelete(@PathVariable String id) {
        service.softDelete(id);
        return ResponseEntity.ok("Complément supprimé");
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<?> restore(@PathVariable String id) {
        service.restore(id);
        return ResponseEntity.ok("Complément restauré");
    }
}
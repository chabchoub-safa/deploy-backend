package com.example.PFE.controller;

import com.example.PFE.dto.HjSummaryRow;
import com.example.PFE.service.HjSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hj-summary")

public class HjSummaryController {

    private final HjSummaryService service;

    public HjSummaryController(HjSummaryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<HjSummaryRow>> getSummary() {
        return ResponseEntity.ok(service.buildSummary());
    }
}
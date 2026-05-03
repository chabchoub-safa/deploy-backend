package com.example.PFE.controller;

import com.example.PFE.dto.GeneralResumeRowDto;
import com.example.PFE.service.GeneralResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/general-resume")
public class GeneralResumeController {

    private final GeneralResumeService service;

    public GeneralResumeController(GeneralResumeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GeneralResumeRowDto>> getResume(
            @RequestParam(required = false) Double multiplicateur
    ) {
        return ResponseEntity.ok(service.getResume(multiplicateur));
    }
}
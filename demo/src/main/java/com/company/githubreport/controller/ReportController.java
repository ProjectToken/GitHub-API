package com.company.githubreport.controller;

import com.company.githubreport.model.AccessReport;
import com.company.githubreport.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/{org}")
    public ResponseEntity<?> getReport(@PathVariable String org) {
        try {
            AccessReport report = service.buildAccessReport(org);
            return ResponseEntity.ok(report);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(502).body(Map.of("error", "GitHub API error", "detail", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Unexpected error", "detail", e.getMessage()));
        }
    }
}

package com.backend.demo.controller;

import com.backend.demo.services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{org}")
    public ResponseEntity<Map<String, List<String>>> getReport(@PathVariable String org) {
        Map<String, List<String>> report = reportService.generateReport(org);
        return ResponseEntity.ok(report);
    }
}


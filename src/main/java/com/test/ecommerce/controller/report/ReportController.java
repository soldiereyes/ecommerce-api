package com.test.ecommerce.controller.report;

import com.test.ecommerce.dto.report.AvgTicketResponse;
import com.test.ecommerce.dto.report.TopUserResponse;
import com.test.ecommerce.dto.report.TotalMonthResponse;
import com.test.ecommerce.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/top-users")
    public ResponseEntity<List<TopUserResponse>> topUsers() {
        return ResponseEntity.ok(reportService.getTopUsers());
    }

    @GetMapping("/avg-ticket")
    public ResponseEntity<List<AvgTicketResponse>> avgTicket() {
        return ResponseEntity.ok(reportService.getAvgTicket());
    }

    @GetMapping("/total-month")
    public ResponseEntity<TotalMonthResponse> totalMonth() {
        return ResponseEntity.ok(reportService.getTotalMonth());
    }
}

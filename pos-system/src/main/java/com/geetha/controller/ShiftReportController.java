package com.geetha.controller;


import com.geetha.payload.dto.ShiftReportDto;
import com.geetha.service.ShiftReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/shift-reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ShiftReportController {

    private final ShiftReportService shiftReportService;

    // ✅ Start a new shift
    @PostMapping("/start")
    public ResponseEntity<ShiftReportDto> startShift() throws Exception {
        return ResponseEntity.ok(shiftReportService.startShift());
    }

    // ✅ End the current shift
    @PutMapping("/end")
    public ResponseEntity<ShiftReportDto> endShift() throws Exception {
        return ResponseEntity.ok(shiftReportService.endShift(null,null));
    }

    // ✅ Get a single shift report by ID
    @GetMapping("/{id}")
    public ResponseEntity<ShiftReportDto> getShiftById(@PathVariable Long id) throws Exception {

            ShiftReportDto dto = shiftReportService.getShiftReportById(id);
            return ResponseEntity.ok(dto);
    }

    // ✅ Get all shift reports
    @GetMapping
    public ResponseEntity<List<ShiftReportDto>> getAllShifts() {
        List<ShiftReportDto> list = shiftReportService.getAllShiftReports();
        return ResponseEntity.ok(list);
    }

    // ✅ Get shifts by branch ID
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<ShiftReportDto>> getShiftsByBranch(@PathVariable Long branchId) {
        List<ShiftReportDto> list = shiftReportService.getShiftReportByBranchId(branchId);
        return ResponseEntity.ok(list);
    }

    // ✅ Get shifts by cashier ID
    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<ShiftReportDto>> getShiftsByCashier(@PathVariable Long cashierId) {
        List<ShiftReportDto> list = shiftReportService.getShiftReportByCashierId(cashierId);
        return ResponseEntity.ok(list);
    }

    // ✅ Get current active shift progress (real-time)
    @GetMapping("/current")
    public ResponseEntity<ShiftReportDto> getCurrentShiftProgress() throws Exception {
        return ResponseEntity.ok(shiftReportService.getCurrentShiftProgress(null));
    }

    // ✅ Get shift by cashier and date
    @GetMapping("/cashier/{cashierId}/by-date")
    public ResponseEntity<ShiftReportDto> getShiftByCashierAndDate(@PathVariable Long cashierId, @RequestParam @DateTimeFormat (iso=DateTimeFormat.ISO.DATE) LocalDateTime date) throws Exception {
        return ResponseEntity.ok(shiftReportService.getShiftByCashierAndDate(cashierId,date));
    }
}

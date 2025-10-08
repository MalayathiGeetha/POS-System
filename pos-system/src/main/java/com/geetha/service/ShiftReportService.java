package com.geetha.service;

import com.geetha.modal.ShiftReport;
import com.geetha.payload.dto.ShiftReportDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShiftReportService {
    ShiftReportDto startShift() throws Exception;
    ShiftReportDto endShift(Long shiftReportId, LocalDateTime shiftEnd) throws Exception;
    ShiftReportDto getShiftReportById(Long id) throws Exception;
    List<ShiftReportDto> getAllShiftReports();
    List<ShiftReportDto> getShiftReportByBranchId(Long branchId);
    List<ShiftReportDto> getShiftReportByCashierId(Long cashierId);
    ShiftReportDto getCurrentShiftProgress(Long cashierId) throws Exception;
    ShiftReportDto getShiftByCashierAndDate(Long cashierId,LocalDateTime date) throws Exception;
}

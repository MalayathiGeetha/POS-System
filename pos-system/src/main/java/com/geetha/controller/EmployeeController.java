package com.geetha.controller;


import com.geetha.domain.UserRole;
import com.geetha.modal.User;
import com.geetha.payload.dto.UserDto;
import com.geetha.payload.response.ApiResponse;
import com.geetha.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDto> createStoreEmployee(@PathVariable Long storeId, @RequestBody UserDto userDto) throws Exception {
        UserDto employee=employeeService.createStoreEmployee(userDto,storeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDto> createBranchEmployee(@PathVariable Long branchId, @RequestBody UserDto userDto) throws Exception {
        UserDto employee=employeeService.createBranchEmployee(userDto,branchId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable Long id, @RequestBody UserDto userDto) throws Exception {
        User employee=employeeService.updateEmployee(id,userDto);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> updateEmployee(@PathVariable Long id) throws Exception {
        employeeService.deleteEmployee(id);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage("Employee deleted");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<List<UserDto>> storeEmployee(@PathVariable Long id, @RequestParam(required=false)UserRole userRole) throws Exception{
        List<UserDto> employee=employeeService.findStoreEmployees(id,userRole);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<List<UserDto>> branchEmployee(@PathVariable Long id, @RequestParam(required=false)UserRole userRole) throws Exception{
        List<UserDto> employee=employeeService.findBranchEmployees(id,userRole);
        return ResponseEntity.ok(employee);
    }


}

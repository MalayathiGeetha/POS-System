package com.geetha.controller;

import com.geetha.payload.dto.InventoryDto;
import com.geetha.payload.response.ApiResponse;
import com.geetha.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDto> createInventory(@RequestBody InventoryDto inventoryDto) throws Exception {
        InventoryDto created = inventoryService.createInventory(inventoryDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDto> updateInventory(
            @PathVariable Long id,
            @RequestBody InventoryDto inventoryDto) throws Exception {
        InventoryDto updated = inventoryService.updateInventory(id,inventoryDto);
        return ResponseEntity.ok(updated);
    }

    // ✅ Delete Inventory
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInventory(@PathVariable Long id) throws Exception {
        inventoryService.deleteInventory(id);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage("Inventory deleted");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getInventoryById(@PathVariable Long id) throws Exception {
        InventoryDto dto = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(dto);
    }

    // ✅ Get Inventory by Product + Branch
    @GetMapping("/branch/{branchId}/product/{productId}")
    public ResponseEntity<InventoryDto> getByProductAndBranch(
            @PathVariable Long productId,
            @PathVariable Long branchId) throws Exception {
        InventoryDto dto = inventoryService.getInventoryByProductIdAndBranchId(productId, branchId);
        return ResponseEntity.ok(dto);
    }

    // ✅ Get All Inventory by Branch
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDto>> getInventoryByBranch(@PathVariable Long branchId) {
        List<InventoryDto> list = inventoryService.getAllInventoryByBranchId(branchId);
        return ResponseEntity.ok(list);
    }
}

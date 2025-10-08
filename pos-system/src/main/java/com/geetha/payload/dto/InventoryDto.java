package com.geetha.payload.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryDto {
    private Long id;
    private BranchDto branch;
    private Long branchId;
    private Long productId;
    private ProductDTO product;
    private Integer quantity;
    private LocalDateTime lastUpdate;
}

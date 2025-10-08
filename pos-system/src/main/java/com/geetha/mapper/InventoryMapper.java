package com.geetha.mapper;

import com.geetha.modal.Inventory;
import com.geetha.modal.Branch;
import com.geetha.modal.Product;
import com.geetha.payload.dto.InventoryDto;

public class InventoryMapper {

    public static InventoryDto toDto(Inventory inventory) {
        if (inventory == null) {
            return null;
        }

        return InventoryDto.builder()
                .id(inventory.getId())
                .branchId(inventory.getBranch() != null ? inventory.getBranch().getId() : null)
                .productId(inventory.getProduct() != null ? inventory.getProduct().getId() : null)
                .product(ProductMapper.toDTO(inventory.getProduct()))
                .quantity(inventory.getQuantity())
                .build();
    }

    public static Inventory toEntity(InventoryDto dto, Branch branch, Product product) {
        if (dto == null) {
            return null;
        }

        return Inventory.builder()
                .branch(branch)       // Pass from service
                .product(product)     // Pass from service
                .quantity(dto.getQuantity())
                .build();
    }
}

package com.geetha.payload.dto;

import com.geetha.domain.StoreStatus;
import com.geetha.modal.StoreContact;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDto {
    private Long id;
    private String brand;
    private UserDto storeAdmin;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;

    private String storeType;
    private StoreStatus status;

    private StoreContact contact;
}

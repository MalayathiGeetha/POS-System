package com.geetha.mapper;


import com.geetha.modal.Branch;
import com.geetha.modal.Store;
import com.geetha.payload.dto.BranchDto;

import java.time.LocalDateTime;

public class BranchMapper {

    public static BranchDto toDto(Branch branch) {
        if (branch == null) return null;

        return BranchDto.builder()
                .id(branch.getId())
                .name(branch.getName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .email(branch.getEmail())
                .workingDays(branch.getWorkingDays())
                .openTime(branch.getOpenTime())
                .closeTime(branch.getCloseTime())
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .storeId(branch.getStore() != null ? branch.getStore().getId() : null)
                .build();
    }

    public static Branch toEntity(BranchDto dto, Store store) {
        if (dto == null) return null;

        return Branch.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .workingDays(dto.getWorkingDays())
                .openTime(dto.getOpenTime())
                .closeTime(dto.getCloseTime())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .store(store)
                .build();
    }
}

package com.geetha.mapper;

import com.geetha.modal.*;
import com.geetha.payload.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShiftReportMapper {

    public static ShiftReportDto toDto(ShiftReport entity) {
        if (entity == null) return null;

        return ShiftReportDto.builder()
                .id(entity.getId())
                .shiftStart(entity.getShiftStart())
                .shiftEnd(entity.getShiftEnd())
                .totalSales(entity.getTotalSales())
                .totalRefunds(entity.getTotalRefunds())
                .netSale(entity.getNetSale())
                .totalOrders(entity.getTotalOrders())
                .cashier(UserMapper.toDTO(entity.getCashier()))
                .cashierId(entity.getCashier().getId())
                .branch(BranchMapper.toDto(entity.getBranch()))
                .branchId(entity.getBranch().getId())
                .paymentSummaries(entity.getPaymentSummaries()) // since PaymentSummary is not a DTO
                .topSellingProducts(mapOrders(entity.getTopSellingProducts()))
                .recentOrders(mapProducts(entity.getRecentOrders()))
                .refunds(mapRefunds(entity.getRefunds()))
                .build();
    }

    private static List<RefundDto> mapRefunds(List<Refund> refunds) {
        if(refunds==null||refunds.isEmpty()) return null;
        return refunds.stream()
                .map(RefundMapper::toDto)
                .collect(Collectors.toList());
    }

    private static List<OrderDto> mapProducts(List<Order> recentOrders) {
        if(recentOrders==null||recentOrders.isEmpty()) return null;
        return recentOrders.stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    private static List<ProductDTO> mapOrders(List<Product> topSellingProducts) {
        if(topSellingProducts==null||topSellingProducts.isEmpty()) return null;
        return topSellingProducts.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    /*public static ShiftReport toEntity(ShiftReportDto dto) {
        if (dto == null) return null;

        ShiftReport entity = new ShiftReport();
        entity.setId(dto.getId());
        entity.setShiftStart(dto.getShiftStart());
        entity.setShiftEnd(dto.getShiftEnd());
        entity.setTotalSales(dto.getTotalSales());
        entity.setTotalRefunds(dto.getTotalRefunds());
        entity.setNetSale(dto.getNetSale());
        entity.setTotalOrders(dto.getTotalOrders());
        entity.setCashier(UserMapper.toEntity(dto.getCashier()));
        entity.setBranch(BranchMapper.toEntity(dto.getBranch()));
        entity.setPaymentSummaries(dto.getPaymentSummaries());

        if (dto.getTopSellingProducts() != null) {
            entity.setTopSellingProducts(dto.getTopSellingProducts().stream()
                    .map(ProductMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        if (dto.getRecentOrders() != null) {
            entity.setRecentOrders(dto.getRecentOrders().stream()
                    .map(OrderMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        if (dto.getRefunds() != null) {
            entity.setRefunds(dto.getRefunds().stream()
                    .map(RefundMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        return entity;
    }*/

}

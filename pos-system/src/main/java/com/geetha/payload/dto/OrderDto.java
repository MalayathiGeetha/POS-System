package com.geetha.payload.dto;

import com.geetha.domain.PaymentType;
import com.geetha.modal.Customer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class OrderDto {

    private Long id;
    private Double totalAmount;
    private LocalDateTime createdAt;

    private Long branchId;
    private Long customerId;
    private BranchDto branch;

    private UserDto cashier;
    private PaymentType paymentType;
    private Customer customer;
    private List<OrderItemDto>  items;


}

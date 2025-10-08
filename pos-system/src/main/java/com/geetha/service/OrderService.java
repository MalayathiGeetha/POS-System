package com.geetha.service;

import com.geetha.domain.OrderStatus;
import com.geetha.domain.PaymentType;
import com.geetha.payload.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto) throws  Exception;
    OrderDto getOrderById(Long id) throws Exception;
    List<OrderDto> getOrdersByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus status) throws Exception;
    List<OrderDto> getOrderByCashier(Long cashierId);
    void deleteOrder(Long id) throws  Exception;
    List<OrderDto> getTodayOrdersByBranch(Long branchId) throws Exception;
    List<OrderDto> getOrdersByCustomerId(Long customerId) throws Exception;
    List<OrderDto> getTop5RecentOrdersByBranch(Long branchId) throws Exception;
}

package com.geetha.service.impl;

import com.geetha.domain.OrderStatus;
import com.geetha.domain.PaymentType;
import com.geetha.mapper.OrderMapper;
import com.geetha.modal.*;
import com.geetha.payload.dto.OrderDto;
import com.geetha.repository.OrderItemRepository;
import com.geetha.repository.OrderRepository;
import com.geetha.repository.ProductRepository;
import com.geetha.service.OrderService;
import com.geetha.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) throws Exception {
        User cashier=userService.getCurrentUser();
        Branch branch=cashier.getBranch();
        if(branch==null){
            throw new Exception("cashier's branch not found");

        }
        Order order=Order.builder().branch(branch).cashier(cashier).customer(orderDto.getCustomer()).paymentType(orderDto.getPaymentType()).build();

        //orderRepository.save(order);
        List<OrderItem> orderItems=orderDto.getItems().stream().map(
                itemDto->{
                    Product product=productRepository.findById(itemDto.getProductId())
                            .orElseThrow(()->new EntityNotFoundException("product not found"));

                    return OrderItem.builder().product(product)
                            .quantity(itemDto.getQuantity()).price(product.getSellingPrice()+itemDto.getQuantity())
                            .order(order).build();
                   // return orderItemRepository.save(orderItem);
                }
        ).toList();
        double total=orderItems.stream().mapToDouble(
                OrderItem::getPrice
        ).sum();
        order.setTotalAmount(total);
        order.setItems(orderItems);
        Order savedOrder=orderRepository.save(order);
        return OrderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long id) throws Exception {
        return orderRepository.findById(id).map(OrderMapper::toDto).orElseThrow(()->new Exception("order not found with id"+id));
    }

    @Override
    public List<OrderDto> getOrdersByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus status) throws Exception {
        return orderRepository.findByBranchId(branchId).stream().filter(order->customerId==null||(order.getCustomer()!=null && order.getCustomer().getId().equals(customerId)))
                .filter(order -> cashierId==null || order.getCashier()!=null && order.getCashier().getId().equals(cashierId))
                .filter(order->paymentType==null||order.getPaymentType()==paymentType)
                .map(OrderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrderByCashier(Long cashierId) {
        return orderRepository.findByCashierId(cashierId).stream()
                .map(OrderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Order order=orderRepository.findById(id).orElseThrow(()->new Exception("order not found with id "+id));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getTodayOrdersByBranch(Long branchId) throws Exception {
        LocalDate today=LocalDate.now();
        LocalDateTime start=today.atStartOfDay();
        LocalDateTime end=today.plusDays(1).atStartOfDay();

        return orderRepository.findByBranchIdAndCreatedAtBetween(
                branchId,start,end
        ).stream().map(
                OrderMapper::toDto
        ).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByCustomerId(Long customerId) throws Exception {

        return orderRepository.findByCustomerId(
               customerId
        ).stream().map(
                OrderMapper::toDto
        ).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getTop5RecentOrdersByBranch(Long branchId) throws Exception {
        return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(
                branchId
        ).stream().map(
                OrderMapper::toDto
        ).collect(Collectors.toList());
    }
}

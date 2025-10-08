package com.geetha.mapper;

import com.geetha.modal.OrderItem;
import com.geetha.payload.dto.OrderItemDto;

public class OrderItemMapper {
    public static OrderItemDto toDto(OrderItem item){
        if(item==null) return null;
        return OrderItemDto.builder().id(item.getId()).productId(item.getProduct().getId())
                .quantity(item.getQuantity()).price(item.getPrice()).product(ProductMapper.toDTO(item.getProduct())).build();
    }
}

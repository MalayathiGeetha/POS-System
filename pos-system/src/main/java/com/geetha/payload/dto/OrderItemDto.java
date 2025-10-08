package com.geetha.payload.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {
    private Long id;
    private Integer quantity;
    private Double price;

    private ProductDTO product;
    private Long productId;
    private Long orderId;
}

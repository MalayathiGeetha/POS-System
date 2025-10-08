package com.geetha.payload.dto;

import lombok.*;

@Data
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
   // private Store store;
    private Long storeId;
}

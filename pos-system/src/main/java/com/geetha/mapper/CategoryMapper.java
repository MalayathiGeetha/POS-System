package com.geetha.mapper;

import com.geetha.modal.Category;
import com.geetha.payload.dto.CategoryDTO;

public class CategoryMapper {
    public static CategoryDTO toDto(Category category) {

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .storeId(category.getStore() != null ? category.getStore().getId() : null)
                .build();
    }
}

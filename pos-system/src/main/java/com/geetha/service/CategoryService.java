package com.geetha.service;

import com.geetha.exception.UserException;
import com.geetha.payload.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO dto) throws Exception;
    List<CategoryDTO> getCategoriesByStoreId(Long storeId);
    CategoryDTO updateCategory(Long id,CategoryDTO dto) throws Exception;
    void deleteCategory(Long id) throws Exception;


}

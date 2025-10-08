package com.geetha.service.impl;


import com.geetha.domain.UserRole;
import com.geetha.mapper.CategoryMapper;
import com.geetha.modal.Category;
import com.geetha.modal.Store;
import com.geetha.modal.User;
import com.geetha.payload.dto.CategoryDTO;
import com.geetha.repository.CategoryRepository;
import com.geetha.repository.StoreRepository;
import com.geetha.service.CategoryService;
import com.geetha.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public CategoryDTO createCategory(CategoryDTO dto) throws Exception {
        User user=userService.getCurrentUser();
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() ->new Exception("Store not found "));

        Category category = Category.builder()
                .store(store)
                .name(dto.getName())
                .build();
        checkAuthority(user,category.getStore());

        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDTO> getCategoriesByStoreId(Long storeId) {

        return categoryRepository.findByStoreId(storeId)
                .stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category not found " ));
        User user=userService.getCurrentUser();

        category.setName(dto.getName());
        checkAuthority(user,category.getStore());

        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category not found "));
        User user=userService.getCurrentUser();
        checkAuthority(user,category.getStore());
        categoryRepository.delete(category);
    }

    private void checkAuthority(User user,Store store) throws Exception {
        boolean isAdmin=user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager=user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore=user.equals(store.getStoreAdmin());

        if(!(isAdmin && isSameStore) && !isManager){
            throw new Exception("you don't have permission to manage this category");
        }
    }



}

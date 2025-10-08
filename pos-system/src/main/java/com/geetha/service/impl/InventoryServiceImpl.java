package com.geetha.service.impl;

import com.geetha.mapper.InventoryMapper;
import com.geetha.modal.Branch;
import com.geetha.modal.Inventory;
import com.geetha.modal.Product;
import com.geetha.payload.dto.InventoryDto;
import com.geetha.repository.BranchRepository;
import com.geetha.repository.InventoryRepository;
import com.geetha.repository.ProductRepository;
import com.geetha.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) throws Exception {
        Branch branch = branchRepository.findById(inventoryDto.getBranchId())
                .orElseThrow(() -> new Exception("Branch not found with id " + inventoryDto.getBranchId()));

        Product product = productRepository.findById(inventoryDto.getProductId())
                .orElseThrow(() -> new Exception("Product not found with id " + inventoryDto.getProductId()));

        Inventory inventory = InventoryMapper.toEntity(inventoryDto, branch, product);
        Inventory saved = inventoryRepository.save(inventory);

        return InventoryMapper.toDto(saved);
    }

    @Override
    public InventoryDto updateInventory(Long id,InventoryDto inventoryDto) throws Exception {
        Inventory existing = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Inventory not found with id " + inventoryDto.getId()));

        // Update fields
        existing.setQuantity(inventoryDto.getQuantity());

        Inventory updated = inventoryRepository.save(existing);
        return InventoryMapper.toDto(updated);
    }

    @Override
    public void deleteInventory(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->new Exception("Inventory not found with id " + id));
        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryDto getInventoryById(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Inventory not found with id " + id));
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryDto getInventoryByProductIdAndBranchId(Long productId, Long branchId) throws Exception {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);
        if (inventory == null) {
            throw new Exception("Inventory not found for productId " + productId + " and branchId " + branchId);
        }
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public List<InventoryDto> getAllInventoryByBranchId(Long branchId) {
        return inventoryRepository.findByBranchId(branchId).stream()
                .map(InventoryMapper::toDto)
                .collect(Collectors.toList());
    }
}

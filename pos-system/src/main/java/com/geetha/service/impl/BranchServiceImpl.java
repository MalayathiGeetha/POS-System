package com.geetha.service.impl;

import com.geetha.exception.UserException;
import com.geetha.mapper.BranchMapper;
import com.geetha.modal.Branch;
import com.geetha.modal.Store;
import com.geetha.modal.User;
import com.geetha.payload.dto.BranchDto;
import com.geetha.repository.BranchRepository;
import com.geetha.repository.StoreRepository;
import com.geetha.service.BranchService;
import com.geetha.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public BranchDto createBranch(BranchDto branchDto) throws UserException {
        // validate store
        User currentUser=userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(currentUser.getId());

        Branch branch = BranchMapper.toEntity(branchDto,store);
        Branch savedBranch=branchRepository.save(branch);
        return BranchMapper.toDto(savedBranch);
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new Exception("Branch not found "));

        branch.setName(branchDto.getName());
        branch.setAddress(branchDto.getAddress());
        branch.setPhone(branchDto.getPhone());
        branch.setEmail(branchDto.getEmail());
        branch.setWorkingDays(branchDto.getWorkingDays());
        branch.setOpenTime(branchDto.getOpenTime());
        branch.setCloseTime(branchDto.getCloseTime());
        branch.setUpdatedAt(LocalDateTime.now());

        Branch updated = branchRepository.save(branch);
        return BranchMapper.toDto(updated);
    }

    @Override
    public void deleteBranch(Long id) throws Exception {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new Exception("Branch not found "));

        branchRepository.delete(branch);
    }

    @Override
    public List<BranchDto> getAllBranchesByStoreId(Long storeId) {
        return branchRepository.findByStoreId(storeId).stream()
                .map(BranchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BranchDto getBranchById(Long id) throws Exception {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new Exception("Branch not found with id " + id));
        return BranchMapper.toDto(branch);
    }
}

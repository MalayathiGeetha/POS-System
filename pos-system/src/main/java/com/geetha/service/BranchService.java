package com.geetha.service;

import com.geetha.exception.UserException;
import com.geetha.payload.dto.BranchDto;

import java.util.List;

public interface BranchService {
    BranchDto createBranch(BranchDto branchDto) throws UserException;
    BranchDto updateBranch(Long id,BranchDto branchDto) throws Exception;
    void deleteBranch(Long id) throws Exception;
    List<BranchDto>  getAllBranchesByStoreId(Long storeId);
    BranchDto getBranchById(Long id) throws Exception;

}

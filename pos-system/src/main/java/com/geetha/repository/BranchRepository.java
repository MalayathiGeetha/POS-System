package com.geetha.repository;

import com.geetha.modal.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch,Long> {
    List<Branch> findByStoreId(Long storeId);


}

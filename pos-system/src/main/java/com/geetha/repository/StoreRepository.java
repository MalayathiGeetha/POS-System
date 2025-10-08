package com.geetha.repository;

import com.geetha.modal.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Store findByStoreAdminId(Long adminId);
}

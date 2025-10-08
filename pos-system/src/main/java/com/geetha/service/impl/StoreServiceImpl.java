package com.geetha.service.impl;

import com.geetha.domain.StoreStatus;
import com.geetha.exception.UserException;
import com.geetha.mapper.RefundMapper;
import com.geetha.mapper.StoreMapper;
import com.geetha.modal.*;
import com.geetha.payload.dto.RefundDto;
import com.geetha.payload.dto.StoreDto;
import com.geetha.repository.OrderRepository;
import com.geetha.repository.RefundRepository;
import com.geetha.repository.StoreRepository;
import com.geetha.service.RefundService;
import com.geetha.service.StoreService;
import com.geetha.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public StoreDto createStore(StoreDto storeDto, User user) {
        Store store= StoreMapper.toEntity(storeDto,user);
        return StoreMapper.toDto(storeRepository.save(store));
    }

    @Override
    public StoreDto getStoreById(Long id) throws Exception {
        Store store=storeRepository.findById(id).orElseThrow(
                ()->new Exception("store not found....")
        );
        return StoreMapper.toDto(store);
    }

    @Override
    public List<StoreDto> getAllStores() {
        List<Store> dtos=storeRepository.findAll();
        return dtos.stream().map(StoreMapper::toDto).collect(Collectors.toList());

    }

    @Override
    public Store getStoreByAdmin() throws UserException {
        User admin=userService.getCurrentUser();
        return storeRepository.findByStoreAdminId(admin.getId());
    }

    @Override
    public StoreDto updateStore(Long id, StoreDto storeDto) throws Exception {
        User currentUser=userService.getCurrentUser();
        Store existing=storeRepository.findByStoreAdminId(currentUser.getId());
        if(existing==null){
            throw new Exception("store not found");
        }
        existing.setBrand(storeDto.getBrand());
        existing.setDescription(storeDto.getDescription());
        if(storeDto.getStoreType()!=null){
            existing.setStoreType(storeDto.getStoreType());
        }
        if(storeDto.getContact()!=null){
            StoreContact contact=StoreContact.builder()
                    .address(storeDto.getContact().getAddress())
                    .phone(storeDto.getContact().getPhone())
                    .email(storeDto.getContact().getEmail())
                    .build();
            existing.setContact(contact);
        }
        Store updatedStore=storeRepository.save(existing);
        return StoreMapper.toDto(updatedStore);
    }

    @Override
    public void deleteStore(Long id) throws UserException {
        Store store=getStoreByAdmin();
        storeRepository.delete(store);
    }

    @Override
    public StoreDto getStoreByEmployee() throws UserException {
        User currentUser=userService.getCurrentUser();
        if(currentUser==null){
            throw new UserException("you don't have permission to access this store");
        }
        return StoreMapper.toDto(currentUser.getStore());
    }

    @Override
    public StoreDto moderateStore(Long id, StoreStatus status) throws Exception {
        Store store=storeRepository.findById(id).orElseThrow(
                ()->new Exception("store not found...")
        );
        store.setStatus(status);
        Store updatedStore=storeRepository.save(store);
        return StoreMapper.toDto(updatedStore);
    }

    @Service
    @RequiredArgsConstructor
    public static class RefundServiceImpl implements RefundService {

        private final UserService userService;
        private final RefundRepository refundRepository;
        private final OrderRepository orderRepository;
        @Override
        public RefundDto createRefund(RefundDto refundDto) throws Exception {
            User cashier=userService.getCurrentUser();
            Order order=orderRepository.findById(refundDto.getOrderId()).orElseThrow(
                    ()->new Exception("Order not found")
            );

            Branch branch=order.getBranch();
            Refund createdRefund=Refund.builder().order(order).cashier(cashier).branch(branch).reason(refundDto.getReason()).amount(refundDto.getAmount()).createdAt(refundDto.getCreatedAt()).build();
            Refund savedRefund=refundRepository.save(createdRefund);
            return RefundMapper.toDto(savedRefund);
        }

        @Override
        public List<RefundDto> getAllRefunds() throws Exception {
            return refundRepository.findAll().stream().map(RefundMapper::toDto).collect(Collectors.toList());
        }

        @Override
        public List<RefundDto> getRefundByCashier(Long cashierId) throws Exception {

            return refundRepository.findByCashierId(cashierId).stream().map(RefundMapper::toDto).collect(Collectors.toList());
        }

        @Override
        public List<RefundDto> getRefundByShiftReport(Long shiftReportId) throws Exception {
            return refundRepository.findByShiftReportId(shiftReportId).stream().map(RefundMapper::toDto).collect(Collectors.toList());

        }

        @Override
        public List<RefundDto> getRefundByCashierAndDateRange(Long cashierId, LocalDateTime startDate, LocalDateTime endDate) throws Exception {
            return refundRepository.findByCashierIdAndCreatedAtBetween(cashierId,startDate,endDate).stream().map(RefundMapper::toDto).collect(Collectors.toList());
        }

        @Override
        public List<RefundDto> getRefundByBranch(Long branchId) throws Exception {
            return refundRepository.findByBranchId(branchId).stream().map(RefundMapper::toDto).collect(Collectors.toList());
        }

        @Override
        public RefundDto getRefundById(Long refundId) throws Exception {
            return refundRepository.findById(refundId).map(RefundMapper::toDto).orElseThrow(()->new Exception("Refund not found"));
        }

        @Override
        public void deleteRefund(Long refundId) throws Exception {
            this.getRefundById(refundId);
            refundRepository.deleteById(refundId);
        }
    }
}

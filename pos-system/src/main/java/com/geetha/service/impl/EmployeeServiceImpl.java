package com.geetha.service.impl;

import com.geetha.domain.UserRole;
import com.geetha.mapper.UserMapper;
import com.geetha.modal.Branch;
import com.geetha.modal.Store;
import com.geetha.modal.User;
import com.geetha.payload.dto.UserDto;
import com.geetha.repository.BranchRepository;
import com.geetha.repository.StoreRepository;
import com.geetha.repository.UserRepository;
import com.geetha.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception {
        Store store=storeRepository.findById(storeId).orElseThrow(()->new Exception("store not found"));
        Branch branch=null;
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER){
            if(employee.getBranchId()==null){
                throw new Exception("branch id is required to create branch manager");
            }
            branch=branchRepository.findById(employee.getBranchId()).orElseThrow(()->new Exception("branch not found"));

        }
        User user= UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));

        User saved=userRepository.save(user);
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER && branch!=null){
            branch.setManager(saved);
            branchRepository.save(branch);

        }
        return UserMapper.toDTO(saved);
    }

    @Override
    public UserDto createBranchEmployee(UserDto employee, Long branchId) throws Exception {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new Exception("branch not found"));
        if(employee.getRole()==UserRole.ROLE_CASHIER||employee.getRole()==UserRole.ROLE_BRANCH_MANAGER){
            User user = UserMapper.toEntity(employee);

            user.setBranch(branch);
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            return UserMapper.toDTO(userRepository.save(user));
        }
        throw new Exception("branch role not supported");
    }

    @Override
    public User updateEmployee(Long employeeId, UserDto employeeDetails) throws Exception {
        User existing=userRepository.findById(employeeId).orElseThrow(()->new Exception("employee not exist with given id"));
        Branch branch=branchRepository.findById(employeeDetails.getBranchId()).orElseThrow(()->new Exception("branch not found"));

        existing.setFullName(employeeDetails.getFullName());
        existing.setEmail(employeeDetails.getEmail());
        existing.setPassword(passwordEncoder.encode(employeeDetails.getPassword()));
        existing.setRole(employeeDetails.getRole());
        existing.setBranch(branch);
        return userRepository.save(existing);

    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        User employee=userRepository.findById(employeeId).orElseThrow(()->new Exception("employee not found"));
        userRepository.delete(employee);
    }

    @Override
    public List<UserDto> findStoreEmployees(Long storeId, UserRole role) throws Exception {
        Store store=storeRepository.findById(storeId).orElseThrow(()->new Exception("store not found"));

        return userRepository.findByStore(store).stream()
                .filter(user -> role==null||user.getRole()==role)
                .map(UserMapper::toDTO)
                        .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findBranchEmployees(Long branchId, UserRole role) throws Exception {
        Branch branch=branchRepository.findById(branchId).orElseThrow(()-> new Exception("branch not found"));
        return userRepository.findByBranchId(branchId).stream().filter(
                user -> role==null||user.getRole()==role
        ).map(UserMapper::toDTO).collect(Collectors.toList());
    }
}

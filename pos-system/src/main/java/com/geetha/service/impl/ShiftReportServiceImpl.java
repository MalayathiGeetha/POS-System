package com.geetha.service.impl;

import com.geetha.domain.PaymentType;
import com.geetha.mapper.ProductMapper;
import com.geetha.modal.*;
import com.geetha.payload.dto.ShiftReportDto;
import com.geetha.repository.*;
import com.geetha.service.ShiftReportService;
import com.geetha.mapper.ShiftReportMapper;
import com.geetha.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftReportServiceImpl implements ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final RefundRepository refundRepository;

    @Override
    public ShiftReportDto startShift() throws Exception {
        User currentUser=userService.getCurrentUser();
        LocalDateTime shiftStart=LocalDateTime.now();
        LocalDateTime startOfDay=shiftStart.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay=shiftStart.withHour(23).withMinute(59).withSecond(59);

        Optional<ShiftReport> existingShift = shiftReportRepository
                .findByCashierAndShiftStartBetween(currentUser,startOfDay,endOfDay);
        if (existingShift.isPresent()) {
            throw new Exception("Shift already started today.Cashier already has an active shift!");
        }

        Branch branch=currentUser.getBranch();
        ShiftReport newShift = ShiftReport.builder()
                .cashier(currentUser)
                .branch(branch)
                .shiftStart(shiftStart)
                .build();

        ShiftReport savedShift = shiftReportRepository.save(newShift);
        return ShiftReportMapper.toDto(savedShift);
    }

    @Override
    public ShiftReportDto endShift(Long shiftReportId, LocalDateTime shiftEnd) throws Exception {
        User currentUser=userService.getCurrentUser();
        ShiftReport shiftReport=shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser).orElseThrow(()->new Exception("Shift not found"));
        shiftReport.setShiftEnd(shiftEnd);
        List<Refund> refunds=refundRepository.findByCashierIdAndCreatedAtBetween(currentUser.getId(),
                shiftReport.getShiftStart(),shiftReport.getShiftEnd());
        double totalRefunds=refunds.stream().mapToDouble(refund->refund.getAmount()!=null?refund.getAmount():0.0).sum();

        List<Order> orders=orderRepository.findByCashierAndCreatedAtBetween(
                currentUser,shiftReport.getShiftStart(),shiftReport.getShiftEnd()
        );
        double totalSales=orders.stream().mapToDouble(Order::getTotalAmount).sum();
        int totalOrders=orders.size();


        double netSale = totalSales-totalRefunds;
        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSale(netSale);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders,totalSales));
        shiftReport.setRefunds(refunds);
        ShiftReport saved=shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDto(saved);
    }

    private List<PaymentSummary> getPaymentSummaries(List<Order> orders, double totalSales) {
        Map<PaymentType,List<Order>> grouped=orders.stream().collect(Collectors.groupingBy(order->order.getPaymentType()!=null?
                order.getPaymentType():PaymentType.CASH));
        List<PaymentSummary> summaries=new ArrayList<>();
        for(Map.Entry<PaymentType,List<Order>> entry:grouped.entrySet()){
            double amount=entry.getValue().stream().mapToDouble(Order::getTotalAmount).sum();
            int transactions=entry.getValue().size();
            double percent=(amount/totalSales)*100;

            PaymentSummary ps=new PaymentSummary();
            ps.setType(entry.getKey());
            ps.setTotalAmount(amount);
            ps.setTransactionCount(transactions);
            ps.setPercentage(percent);
            summaries.add(ps);
        }
        return summaries;
    }

    private List<Product> getTopSellingProducts(List<Order> orders) {
        Map<Product,Integer> productSalesMap=new HashMap<>();
        for(Order order:orders){
            for(OrderItem item:order.getItems()){
                Product product=item.getProduct();
                productSalesMap.put(product,productSalesMap.getOrDefault(product,0)+item.getQuantity());
            }
        }

        return productSalesMap.entrySet().stream().sorted((a,b)->b.getValue().compareTo(a.getValue()))
                .limit(5).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    private List<Order> getRecentOrders(List<Order> orders) {
        return orders.stream().sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .limit(5).collect(Collectors.toList());
    }

    @Override
    public ShiftReportDto getShiftReportById(Long id) throws Exception {
        return shiftReportRepository.findById(id)
                .map(ShiftReportMapper::toDto)
                .orElseThrow(()->new Exception("Shift report not found with gievn id "+id));
    }

    @Override
    public List<ShiftReportDto> getAllShiftReports() {
        return shiftReportRepository.findAll()
                .stream()
                .map(ShiftReportMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDto> getShiftReportByBranchId(Long branchId) {
        return shiftReportRepository.findByBranchId(branchId)
                .stream()
                .map(ShiftReportMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDto> getShiftReportByCashierId(Long cashierId) {
        return shiftReportRepository.findByCashierId(cashierId)
                .stream()
                .map(ShiftReportMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShiftReportDto getCurrentShiftProgress(Long cashierId) throws Exception {
        User user=userService.getCurrentUser();

        ShiftReport shiftReport = shiftReportRepository
                .findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(user)
                .orElseThrow(() -> new Exception("No active shift found for cashier!"));

        LocalDateTime now=LocalDateTime.now();
        List<Order> orders=orderRepository.findByCashierAndCreatedAtBetween(user,shiftReport.getShiftStart(),now);
        List<Refund> refunds=refundRepository.findByCashierIdAndCreatedAtBetween(user.getId(),
                shiftReport.getShiftStart(),now);
        double totalRefunds=refunds.stream().mapToDouble(refund->refund.getAmount()!=null?refund.getAmount():0.0).sum();

        double totalSales=orders.stream().mapToDouble(Order::getTotalAmount).sum();
        int totalOrders=orders.size();
        double netSale = totalSales-totalRefunds;
        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSale(netSale);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders,totalSales));
        shiftReport.setRefunds(refunds);
        ShiftReport saved=shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDto(saved);
    }

    @Override
    public ShiftReportDto getShiftByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception {
        User cashier = userRepository.findById(cashierId)
                .orElseThrow(() -> new Exception("Cashier not found with id: " + cashierId));

        LocalDateTime startOfDay = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = date.withHour(23).withMinute(59).withSecond(59);

        ShiftReport shift = shiftReportRepository
                .findByCashierAndShiftStartBetween(cashier, startOfDay, endOfDay)
                .orElseThrow(() -> new Exception("No shift found for given date!"));

        return ShiftReportMapper.toDto(shift);
    }
}

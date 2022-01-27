package com.xxx.rent.api.service.impl;

import com.xxx.rent.api.exception.BizException;
import com.xxx.rent.api.mapper.CustomerMapper;
import com.xxx.rent.api.model.Customer;
import com.xxx.rent.api.model.Inventory;
import com.xxx.rent.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author yuyao wang
 * @time 2022/1/25 23:43
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer selectByCustomerId(Integer customerId) {
        Customer customer = customerMapper.selectByCustomerId(customerId);
        return customer;
    }

    @Override
    public Inventory selectByCarModelId(Integer carModelId) {
        return customerMapper.selectByCarModelId(carModelId);
    }

    @Override
    public void updateByCarModelId(Integer carModelId, Integer carInStock) {
        customerMapper.updateByCarModelId(carModelId, carInStock);
    }

    @Override
    public void insertIntoReserve(Integer customerId, String customerName, Integer carModelId, String carModel, Integer quantity, Integer duration, String reserveStatus) {
        customerMapper.insertIntoReserve(customerId, customerName, carModelId, carModel, quantity, duration, reserveStatus);
    }

    @Override
    @Transactional
    public synchronized String createNewReserve(Integer customerId, String customerName, Integer carModelId, Integer quantity, Integer duration) {

        // customer verification
        Customer customer = this.selectByCustomerId(customerId);

        if(customer == null || !customer.getCustomerName().equals(customerName)) throw new BizException("用户不存在！");

        // inventory verification
        Inventory inventory = this.selectByCarModelId(carModelId);

        if(inventory == null) throw new BizException("车型不存在！");

        // quantity check
        if (inventory.getCarInStock() < quantity) throw new BizException("车辆库存不足！");

        // update inventory
        this.updateByCarModelId(carModelId, inventory.getCarInStock() - quantity);

        // create reserve record
        String carModel = inventory.getCarModel();
        this.insertIntoReserve(customerId, customerName, carModelId, carModel, quantity, duration, "On");

        return "租车成功！";
    }

}

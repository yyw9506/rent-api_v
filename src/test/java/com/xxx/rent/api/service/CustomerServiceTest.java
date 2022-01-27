package com.xxx.rent.api.service;

import com.xxx.rent.api.model.Customer;
import com.xxx.rent.api.model.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerServiceTest {
    @Autowired
    public CustomerService cs;

    @Test
    public void selectByCustomerId() {

        // unit test
        Customer customer = cs.selectByCustomerId(2);

        if (customer == null){
            System.out.println("用户不存在");
        }else{
            System.out.println("用户名称：" + customer.getCustomerName());
        }
    }

    @Test
    public void selectByCarModelId(){
        Inventory inventory = cs.selectByCarModelId(2);

        if(inventory == null){
            System.out.println("该型号的车不存在");
        }else {
            System.out.println("库存：" + inventory.getCarInStock());
        }
    }

    @Test
    public void updateByCarModelId(){
        cs.updateByCarModelId(1, 2);
    }
}
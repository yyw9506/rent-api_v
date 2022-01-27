package com.xxx.rent.api.model;

import org.springframework.context.annotation.Scope;

/**
 * description
 * @author yuyao wang
 * @time 2022/1/25 23:39
 */

public class Customer {

    private Long customerId;
    private String customerName;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
}

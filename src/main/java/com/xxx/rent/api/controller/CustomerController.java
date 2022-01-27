package com.xxx.rent.api.controller;

import com.xxx.rent.api.util.Result;
import com.xxx.rent.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 * @author yuyao wang
 * @time 2022/1/25 23:50
 */

@RestController
@RequestMapping(value = "/car")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

//    /**
//     *
//     * @param carModelId
//     * @return Result
//     */
//    @GetMapping(value = "/findByCarModelId")
//    public Result<Inventory> findByCarModelId(Integer carModelId) {
//        Inventory inventory = customerService.selectByCarModelId(carModelId);
//        return Result.responseSuccess(inventory);
//    }
//
//    /**
//     *
//     * @param carModelId
//     * @param carInStock
//     *
//     */
//    @GetMapping(value = "/updateByCarModelId")
//    public void updateByCarModel(Integer carModelId, Integer carInStock){
//        customerService.updateByCarModelId(carModelId, carInStock);
//    }

    /**
     *
     * @param customerId
     * @param customerName
     * @param carModelId
     * @param quantity
     * @param duration
     * @return
     */
    @GetMapping(value = "/createNewReserve")
    public Result<String>createNewReserve(Integer customerId, String customerName, Integer carModelId, Integer quantity, Integer duration){
        String msg = customerService.createNewReserve(customerId, customerName, carModelId, quantity, duration);
        return Result.responseSuccess(msg);
    }
}
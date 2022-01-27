package com.xxx.rent.api.service;

import com.xxx.rent.api.model.Customer;
import com.xxx.rent.api.model.Inventory;
import org.apache.ibatis.annotations.Param;

/**
 * description
 *
 * @author yuyao wang
 * @time 2022/1/25 23:42
 */

public interface CustomerService {
    /** helper APIs **/

    /**
     *
     * @param customerId
     * @return
     */
    Customer selectByCustomerId(Integer customerId);

    /**
     *
     * @param carModelId
     * @return
     */
    Inventory selectByCarModelId(Integer carModelId);

    /**
     *
     * @param carModelId
     * @param carInStock
     */
    void updateByCarModelId(Integer carModelId, Integer carInStock);

    /**
     *
     * @param customerId
     * @param customerName
     * @param carModelId
     * @param carModel
     * @param quantity
     * @param duration
     * @param reserveStatus
     */
    void insertIntoReserve(Integer customerId, String customerName, Integer carModelId, String carModel, Integer quantity, Integer duration, String reserveStatus);

    /**
     *
     * @param customerId
     * @param customerName
     * @param carModelId
     * @param quantity
     * @param duration
     */
    String createNewReserve(Integer customerId, String customerName, Integer carModelId, Integer quantity, Integer duration);
}

package com.xxx.rent.api.mapper;

import com.xxx.rent.api.model.Customer;
import com.xxx.rent.api.model.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * description
 *
 * @author yuyao wang
 * @time 2022/1/25 23:37
 */
@Mapper
public interface CustomerMapper {

    /**
     *
     * @param customerId
     * @return
     */
    Customer selectByCustomerId(@Param("customerId") Integer customerId);

    /**
     *
     * @param carModelId
     * @return
     */
    Inventory selectByCarModelId(@Param("carModelId") Integer carModelId);


    /**
     *
     * @param carModelId
     * @param carInStock
     */
    void updateByCarModelId(@Param("carModelId") Integer carModelId, @Param("carInStock") Integer carInStock);


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
    void insertIntoReserve(@Param("customerId") Integer customerId,
                           @Param("customerName") String customerName,
                           @Param("carModelId") Integer carModelId,
                           @Param("carModel") String carModel,
                           @Param("quantity") Integer quantity,
                           @Param("duration") Integer duration,
                           @Param("reserveStatus") String reserveStatus);
}

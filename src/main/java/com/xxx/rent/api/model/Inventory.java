package com.xxx.rent.api.model;

/**
 * description
 *
 * @author yuyao wang
 * @time 2022/1/25 23:39
 */

public class Inventory {

    private Integer carModelId;
    private String carModel;
    private Integer carInStock;

    public Integer getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Integer carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Integer getCarInStock() {
        return carInStock;
    }

    public void setCarInStock(Integer carInStock) {
        this.carInStock = carInStock;
    }
}

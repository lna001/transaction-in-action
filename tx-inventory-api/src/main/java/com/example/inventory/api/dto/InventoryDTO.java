package com.example.inventory.api.dto;

import lombok.Data;

/**
 * @Author: lna
 * @Date: 2019/9/6 14:00
 */
@Data
public class InventoryDTO {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户账号ID
     */
    private Integer userId;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 数量
     */
    private Integer count;
}

package com.example.txorder.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lna
 * @Date: 2019/9/6 11:51
 */
@Data
public class OrderDto {
    private Integer id;

    /**
     * 用户账号ID
     */
    private Integer userId;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 购买数量
     */
    private Integer productCount;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单状态：0=已拍下，1=拍下失败，2=未支付，3=支付成功，4=支付失败
     */
    private Byte status;

    /**
     * 总价
     */
    private Long totalMoney;

    /**
     * 优惠券id
     */
    private Integer couponReceiveId;

    /**
     * 优惠金额
     */
    private BigDecimal districtMoney;

    /**
     * 支付金额
     */
    private BigDecimal paymentMoney;

    /**
     * 扣除积分
     */
    private Integer decreaseScore;

    /**
     * 赠送积分
     */
    private Integer largessScore;

    /**
     * 删除标志，默认0不删除，1删除
     */
    private Byte deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}

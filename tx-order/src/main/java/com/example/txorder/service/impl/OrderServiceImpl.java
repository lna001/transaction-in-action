package com.example.txorder.service.impl;

import com.example.inventory.api.api.InventoryApi;
import com.example.inventory.api.dto.InventoryDTO;
import com.example.txorder.dto.OrderDto;
import com.example.txorder.entity.Order;
import com.example.txorder.enums.OrderStatusEnum;
import com.example.txorder.mapper.OrderMapper;
import com.example.txorder.service.OrderService;
import org.dromara.hmily.annotation.Hmily;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: lna
 * @Date: 2019/9/6 11:47
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private InventoryApi inventoryApi;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Override
    @Hmily(confirmMethod = "confirmMethod",cancelMethod = "cancelMethod")
    @Transactional
    public void createOrder(OrderDto obj) {
        //创建一个订单
        Order order=new Order();
        BeanUtils.copyProperties(obj,order);
        order.setStatus(OrderStatusEnum.CONFIRM.getCode());
        this.orderMapper.insertSelective(order);
        //减库存
        InventoryDTO inventoryDTO=new InventoryDTO();
        inventoryDTO.setOrderNo(order.getOrderNo());
        inventoryDTO.setCount(order.getProductCount());
        inventoryDTO.setUserId(order.getUserId());
        inventoryDTO.setProductId(order.getProductId());
        inventoryApi.decrease(inventoryDTO);
    }
    @Transactional
    public void confirmMethod(OrderDto  obj){
        LOGGER.info("-------进入订单的confirm-------");
        Order order=new Order();
        order.setOrderNo(obj.getOrderNo());
        order=this.orderMapper.selectOne(order);
        order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        this.orderMapper.updateByPrimaryKeySelective(order);

    }
    public  void cancelMethod(OrderDto obj){
        LOGGER.info("-------进入订单的cancel-------");
        Order order=new Order();
        order.setOrderNo(obj.getOrderNo());
        order=this.orderMapper.selectOne(order);

        order.setStatus(OrderStatusEnum.CONFIRM_FAIL.getCode());
        this.orderMapper.updateByPrimaryKeySelective(order);
    }
}

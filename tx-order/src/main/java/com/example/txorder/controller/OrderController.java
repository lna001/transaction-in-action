package com.example.txorder.controller;

import com.example.txorder.dto.OrderDto;
import com.example.txorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @Author: lna
 * @Date: 2019/9/6 11:45
 */
@RestController
public class OrderController {
   @Autowired
    private OrderService orderService;
   @ResponseBody
   @RequestMapping(value="/t",method=RequestMethod.GET)
    public  String test(@RequestParam("c") Integer c){
        System.out.println("-------------->"+c);
       OrderDto obj=new OrderDto();
       obj.setUserId(1);
       obj.setProductId(1);
       obj.setPaymentMoney(new BigDecimal(100));
       obj.setProductCount(1);

       //扣除2个积分
       obj.setDecreaseScore(2);
       //扣除优惠券
       obj.setCouponReceiveId(c);
       obj.setOrderNo(UUID.randomUUID().toString());
       this.orderService.createOrder(obj);
       return "Ok";


   }
}

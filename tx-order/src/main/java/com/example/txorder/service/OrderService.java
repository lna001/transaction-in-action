package com.example.txorder.service;

import com.example.txorder.dto.OrderDto;
import org.springframework.stereotype.Service;

/**
 * @Author: lna
 * @Date: 2019/9/6 11:46
 */

public interface OrderService {
    void createOrder(OrderDto obj);
}

package com.example.txinventoryserver.service;

import com.example.inventory.api.dto.InventoryDTO;

/**
 * @Author: lna
 * @Date: 2019/9/6 14:23
 */
public interface InventoryService {
    void decrease(InventoryDTO inventoryDTO);
}

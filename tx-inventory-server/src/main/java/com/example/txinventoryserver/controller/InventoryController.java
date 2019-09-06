package com.example.txinventoryserver.controller;

import com.example.inventory.api.api.InventoryApi;
import com.example.inventory.api.dto.InventoryDTO;
import com.example.txinventoryserver.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lna
 * @Date: 2019/9/6 14:19
 */
@RestController
public class InventoryController implements InventoryApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);
    @Autowired
    private InventoryService inventoryService;
    @Override
    public String decrease(InventoryDTO inventoryDTO) {
        LOGGER.info("进入库存的controller=======");
        this.inventoryService.decrease(inventoryDTO);
        return "1";
    }
}

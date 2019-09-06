package com.example.inventory.api.api;

import com.example.inventory.api.dto.InventoryDTO;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: lna
 * @Date: 2019/9/6 14:08
 */
@FeignClient("tx-inventory")
@RequestMapping("/inventory")
public interface InventoryApi {
    /******
     * 减库存
     */
     @PostMapping(value="/decrease")
     @Hmily
     public String  decrease(@RequestBody InventoryDTO inventoryDTO);

}

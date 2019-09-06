package com.example.txinventoryserver.mapper;

import com.example.txinventoryserver.entity.Inventory;
import tk.mybatis.mapper.common.Mapper;

public interface InventoryMapper extends Mapper<Inventory> {

    void decrease(Inventory updateInventory);
}
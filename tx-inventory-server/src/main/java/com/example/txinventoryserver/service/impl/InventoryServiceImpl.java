package com.example.txinventoryserver.service.impl;

import com.example.inventory.api.dto.InventoryDTO;
import com.example.txcommons.enums.TccEnum;
import com.example.txinventoryserver.entity.Inventory;
import com.example.txinventoryserver.entity.InventoryDetail;
import com.example.txinventoryserver.mapper.InventoryDetailMapper;
import com.example.txinventoryserver.mapper.InventoryMapper;
import com.example.txinventoryserver.service.InventoryService;
import org.dromara.hmily.annotation.Hmily;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @Author: lna
 * @Date: 2019/9/6 14:24
 */
@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private InventoryDetailMapper inventoryDetailMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);
    @Override
    @Transactional
    @Hmily(confirmMethod = "confirmMethod",cancelMethod = "cancelMethod")
    public void decrease(InventoryDTO inventoryDTO) {
        LOGGER.info("=========进入库存try============");
        //1校验库存
        Inventory inventory=new Inventory();
        inventory.setProductId(inventoryDTO.getProductId());
        inventory=this.inventoryMapper.selectOne(inventory);
        if(inventory.getTotalInventory()<=0||inventory.getTotalInventory()<inventoryDTO.getCount()){
            throw new RuntimeException("库存不足");
        }
        //第二步：幂等性校验
        InventoryDetail inventoryDetail=new InventoryDetail();
        inventoryDetail.setOrderNo(inventoryDTO.getOrderNo());
        if(Objects.isNull(this.inventoryDetailMapper.selectOne(inventoryDetail))){
            throw new RuntimeException("存在订单号");
        }
        //第三步插入库存明细
        BeanUtils.copyProperties(inventoryDTO,inventoryDetail);
        inventoryDetail.setTxStatus(TccEnum.TRY.getCode());
        this.inventoryDetailMapper.insertSelective(inventoryDetail);
        //第四步减库存
        Inventory updateInventory=new Inventory();
        updateInventory.setProductId(inventoryDTO.getProductId());
        //库存处理
        updateInventory.setTotalInventory(-inventoryDTO.getCount());
        //冻结库存
        updateInventory.setLockInventory(+inventoryDTO.getCount());
        this.inventoryMapper.decrease(updateInventory);
    }

    @Transactional
    public void  confirmMethod(InventoryDTO obj){
        LOGGER.info("========进入库存的confirm======");
        //第一步：幂等性校验
        InventoryDetail  inventoryDetail=new InventoryDetail();
        inventoryDetail.setOrderNo(obj.getOrderNo());
        inventoryDetail=this.inventoryDetailMapper.selectOne(inventoryDetail);
        if(inventoryDetail.getTxStatus()==TccEnum.CONFIRM.getCode()){
            throw new RuntimeException("该订单号,已经comfirm!");
        }
        //第二步修改订单明细状态 作用：幂等性校验
        InventoryDetail updateInventoryDetail=new InventoryDetail();
        updateInventoryDetail.setId(inventoryDetail.getId());
        updateInventoryDetail.setTxStatus(TccEnum.CONFIRM.getCode());
        this.inventoryDetailMapper.updateByPrimaryKeySelective(updateInventoryDetail);

        //第三步库存处理
        Inventory inventory=new Inventory();
        inventory.setProductId(obj.getProductId());
        inventory.setTotalInventory(0);
        inventory.setLockInventory(-obj.getCount());
        this.inventoryMapper.decrease(inventory);
    }
    @Transactional
    public void  cancelMethod(InventoryDTO obj){
        LOGGER.info("========进入库存的cancel======");
        //第一步：幂等性校验
        InventoryDetail  inventoryDetail=new InventoryDetail();
        inventoryDetail.setOrderNo(obj.getOrderNo());
        inventoryDetail=this.inventoryDetailMapper.selectOne(inventoryDetail);
        if(inventoryDetail.getTxStatus()==TccEnum.CANCEL.getCode()){
            throw new RuntimeException("该订单号,已经CANCEL!");
        }
        //第二步修改订单明细状态 作用：幂等性校验
        InventoryDetail updateInventoryDetail=new InventoryDetail();
        updateInventoryDetail.setId(inventoryDetail.getId());
        updateInventoryDetail.setTxStatus(TccEnum.CANCEL.getCode());
        this.inventoryDetailMapper.updateByPrimaryKeySelective(updateInventoryDetail);
        //第三步库存处理
        Inventory inventory=new Inventory();
        inventory.setProductId(obj.getProductId());
        inventory.setTotalInventory(+obj.getCount());
        inventory.setLockInventory(-obj.getCount());
        this.inventoryMapper.decrease(inventory);

    }

}

package com.tcs.hackkerrank.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tcs.hackkerrank.dao.OperationRepositoryDao;
import com.tcs.hackkerrank.model.Inventory;

@Component
public class DBWriter implements ItemWriter<Inventory> {

    @Autowired
 private OperationRepositoryDao service;
 
    @Override
    public void write(List<? extends Inventory> items) throws Exception {

        System.out.println("Data Saved for items: " + items);
        for (Inventory inventory : items) {
        	Inventory invent = (Inventory) service.findEntityById(inventory.getSkuId(), Inventory.class);
        	if(null != invent){
        		if(invent.getMinQtyReq() > invent.getInventoryOnHand()){
        			// update the max
        			service.updateEntity(inventory, Inventory.class, invent.getSkuId());
        		}
        	}else{
        		service.insertEntity(inventory);
        	}
        	
		}
        
      System.out.println("Items Saved");
     
    }
}

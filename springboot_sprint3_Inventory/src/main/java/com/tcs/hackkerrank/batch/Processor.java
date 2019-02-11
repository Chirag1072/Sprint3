package com.tcs.hackkerrank.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.tcs.hackkerrank.model.Inventory;

@Component
public class Processor implements ItemProcessor<Inventory, Inventory> {

   @Override
    public Inventory process(Inventory item) throws Exception {
	   
	     
        return item;
    }
}

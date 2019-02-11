package com.tcs.hackkerrank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.hackkerrank.model.BaseEntity;
import com.tcs.hackkerrank.model.Inventory;
import com.tcs.hackkerrank.service.OperationInterface;

@RestController
@RequestMapping(value="/item")
public class InventoryController {
	@Autowired
	OperationInterface itemService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllItems(){
		List<BaseEntity> list = itemService.findAll("select i from Inventory i");
		if(null == list || list.isEmpty()) {
			return new ResponseEntity<String>("No Items Present", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<BaseEntity>>(list, HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public @ResponseBody ResponseEntity<?> getInventoryById(@PathVariable("id") Long id) {
		BaseEntity entity = itemService.findById(id,Inventory.class);
		if(null == entity) {
			return new ResponseEntity<String>("Item Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Inventory>((Inventory)entity , HttpStatus.OK);
	}
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<?> addItem(@RequestBody Inventory item) throws Exception {
		item = (Inventory) itemService.insertEntity(item);
	 return new ResponseEntity<Inventory>(item , HttpStatus.CREATED);
	}
	
	@RequestMapping( method = RequestMethod.PUT)
	public ResponseEntity<?> updateItem(@RequestBody Inventory item) throws Exception {
		item = (Inventory) itemService.updateEntity(item, Inventory.class, item.getSkuId());
		if(null == item) {
			return new ResponseEntity<String>("Item Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Inventory>(item , HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> deleteItemById(@PathVariable Long id){
		BaseEntity entity = itemService.delete(id,Inventory.class);
		if(null == entity) {
			return new ResponseEntity<String>("Item Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Item deleted" , HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteAll(){
		itemService.deleteAll("delete from Inventory");
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}

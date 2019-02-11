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
import com.tcs.hackkerrank.model.Vendor;
import com.tcs.hackkerrank.service.OperationInterface;

@RestController
@RequestMapping(value="/vendor")
public class VendorController {
	@Autowired
	OperationInterface vendorService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllVendors(){
		List<BaseEntity> list = vendorService.findAll("select v from Vendor v");
		if(null == list || list.isEmpty()) {
			return new ResponseEntity<String>("No Vendors Present", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<BaseEntity>>(list, HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public @ResponseBody ResponseEntity<?> getVendorById(@PathVariable("id") Long id) {
		BaseEntity entity = vendorService.findById(id,Vendor.class);
		if(null == entity) {
			return new ResponseEntity<String>("Vendor Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Vendor>((Vendor)entity , HttpStatus.OK);
	}
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<?> addVendor(@RequestBody Vendor vendor) throws Exception {
		vendor = (Vendor) vendorService.insertEntity(vendor);
	 return new ResponseEntity<Vendor>(vendor , HttpStatus.CREATED);
	}
	
	@RequestMapping( method = RequestMethod.PUT)
	public ResponseEntity<?> updateVendor(@RequestBody Vendor vendor) throws Exception {
		vendor = (Vendor) vendorService.updateEntity(vendor, Vendor.class, vendor.getVendorId());
		if(null == vendor) {
			return new ResponseEntity<String>("Vendor Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Vendor>(vendor , HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> deleteVendorById(@PathVariable Long id){
		BaseEntity entity = vendorService.delete(id,Vendor.class);
		if(null == entity) {
			return new ResponseEntity<String>("Vendor Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Vendor deleted" , HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteAll(){
		vendorService.deleteAll("delete from Vendor");
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}

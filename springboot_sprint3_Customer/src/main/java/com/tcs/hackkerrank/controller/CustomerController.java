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
import com.tcs.hackkerrank.model.Customer;
import com.tcs.hackkerrank.service.OperationInterface;

@RestController
@RequestMapping(value="/customer")
public class CustomerController {
	
	@Autowired
	OperationInterface customerService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllCustomers(){
		List<BaseEntity> list = customerService.findAll("select c from Customer c");
		if(null == list || list.isEmpty()) {
			return new ResponseEntity<String>("No Customers Present", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<BaseEntity>>(list, HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public @ResponseBody ResponseEntity<?> getCustomerById(@PathVariable("id") Long id) {
		BaseEntity entity = customerService.findById(id,Customer.class);
		if(null == entity) {
			return new ResponseEntity<String>("Customer Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Customer>((Customer)entity , HttpStatus.OK);
	}
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer) throws Exception {
		customer = (Customer) customerService.insertEntity(customer);
	 return new ResponseEntity<Customer>(customer , HttpStatus.CREATED);
	}
	
	@RequestMapping( method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) throws Exception {
		customer = (Customer) customerService.updateEntity(customer,Customer.class, customer.getCustomerId());
		if(null == customer) {
			return new ResponseEntity<String>("Customer Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Customer>(customer , HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable Long id){
		BaseEntity entity = customerService.delete(id,Customer.class);
		if(null == entity) {
			return new ResponseEntity<String>("Customer Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Customer deleted" , HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteAll(){
		customerService.deleteAll("delete from Customer");
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}

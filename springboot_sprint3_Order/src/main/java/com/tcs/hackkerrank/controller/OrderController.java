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

import com.tcs.hackkerrank.model.Order;
import com.tcs.hackkerrank.service.EmailService;
import com.tcs.hackkerrank.service.OperationInterface;
import com.tcs.hackkerrank.service.RabbitMessageService;

@RestController
@RequestMapping(value="/order")
public class OrderController {
	
	@Autowired
	OperationInterface orderService;
	@Autowired
	EmailService emailService;
	@Autowired
	RabbitMessageService messageService;
	// Get by order Id 
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public @ResponseBody ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
		Order entity = orderService.findById(id,Order.class);
		if(null == entity) {
			return new ResponseEntity<String>("Order Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Order>((Order)entity , HttpStatus.OK);
	}
	
	//get by customer Id
	@RequestMapping(method=RequestMethod.GET, value="/customer/{id}")
	public @ResponseBody ResponseEntity<?> getOrderByCustomerId(@PathVariable("id") Long id) {
		List<Order> entity = orderService.findByCustomerId(id);
		if(null == entity) {
			return new ResponseEntity<String>("Order Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Order>>(entity , HttpStatus.OK);
	}
	//POST
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<?> addOrder(@RequestBody Order order) throws Exception {
		order = (Order) orderService.insertEntity(order);
		if(null != order && order.getOrderId() != null) {
			// Send Email
			emailService.sendEmail();
			messageService.sendOrderMessage(order);
		}
	 return new ResponseEntity<Order>(order , HttpStatus.CREATED);
	}
	// Patch , Update order status 
	@RequestMapping( method = RequestMethod.PATCH)
	public ResponseEntity<?> updateOrder(@RequestBody Order order) throws Exception {
		order = (Order) orderService.updateEntity(order,Order.class, order.getOrderId());
		if(null == order) {
			return new ResponseEntity<String>("Order Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Order>(order , HttpStatus.OK);
	}
// delete by order Id
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> deleteOrderById(@PathVariable Long id){
		Order entity = orderService.delete(id,Order.class);
		if(null == entity) {
			return new ResponseEntity<String>("Order Not Found" , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Order deleted" , HttpStatus.OK);
	}
	
}

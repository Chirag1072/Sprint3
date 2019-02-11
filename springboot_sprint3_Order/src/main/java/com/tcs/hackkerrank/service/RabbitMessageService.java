package com.tcs.hackkerrank.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.tcs.hackkerrank.model.Order;
import com.tcs.hackkerrank.model.OrderLineItem;
import com.tcs.hackkerrank.rabbitmq.RabbitMessageProducer;

@Service
public class RabbitMessageService {

	public void sendOrderMessage(Order order) throws IOException, TimeoutException{
		RabbitMessageProducer producer = new RabbitMessageProducer("queue");
		List<OrderLineItem> orderItems = order.getOrderLineItemList(); 
		orderItems.forEach(items ->{
			HashMap<String, Object> message = new HashMap<String, Object>();
			message.put("SKUID", items.getSkuId());
			message.put("ITEM_QTY", items.getItemQty());
			try {
				producer.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		System.out.println("Message sent.... ");
	}
	
}

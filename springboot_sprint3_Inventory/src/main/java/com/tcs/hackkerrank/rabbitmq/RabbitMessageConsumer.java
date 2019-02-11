package com.tcs.hackkerrank.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.tcs.hackkerrank.model.Inventory;
import com.tcs.hackkerrank.service.OperationInterface;
@Component
@Scope("prototype")
public class RabbitMessageConsumer extends RabbitEndPoint implements Runnable, Consumer{
	
	@Autowired
	OperationInterface inventoryService;
	
	public RabbitMessageConsumer(String endPointName) throws IOException, TimeoutException{
		super(endPointName);		
	}
	
	public RabbitMessageConsumer() throws IOException, TimeoutException {
		super("queue");	
	}
	
	public void run() {
		try {
			//start consuming messages. Auto acknowledge messages.
			channel.basicConsume(endPointName, true,this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called when consumer is registered.
	 */
	public void handleConsumeOk(String consumerTag) {
		System.out.println("Consumer "+consumerTag +" registered");		
	}

	/**
	 * Called when new message is available.
	 */
	public void handleDelivery(String consumerTag, Envelope env,
			BasicProperties props, byte[] body) throws IOException {
		HashMap<String, Object> message = (HashMap<String, Object>)SerializationUtils.deserialize(body);
			Inventory invent = (Inventory) inventoryService.findById((Long)message.get("SKUID"), Inventory.class);
			invent.setInventoryOnHand(invent.getInventoryOnHand() - (Integer)message.get("ITEM_QTY"));
			inventoryService.updateEntity(invent, Inventory.class,invent.getSkuId());
			
	}

	public void handleCancel(String consumerTag) {}
	public void handleCancelOk(String consumerTag) {}
	public void handleRecoverOk(String consumerTag) {}
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}
}
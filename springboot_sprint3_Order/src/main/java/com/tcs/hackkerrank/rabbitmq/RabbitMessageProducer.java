package com.tcs.hackkerrank.rabbitmq;


import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import org.springframework.util.SerializationUtils;

public class RabbitMessageProducer extends RabbitEndPoint{
	
	public RabbitMessageProducer(String endPointName) throws IOException, TimeoutException{
		super(endPointName);
	}

	public void sendMessage(Serializable object) throws IOException {
	    channel.basicPublish("",endPointName, null, SerializationUtils.serialize(object));
	}	
}
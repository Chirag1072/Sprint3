package com.tcs.hackerrank;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.hackkerrank.SpringbootSprint3Application;
import com.tcs.hackkerrank.model.Order;
import com.tcs.hackkerrank.model.OrderLineItem;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes={SpringbootSprint3Application.class})
public class OrderServiceTest {

	@Autowired
	private WebApplicationContext context;
	private Order order;
	private OrderLineItem orderlineitem;
	private MockMvc mvc;
	//private String path="";

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	
	@Test
	public void getOrderByIdTest() throws Exception {
		// Bad Test
		mvc.perform(MockMvcRequestBuilders.get("/order/0").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
		.andExpect(content().string("Order Not Found"));
		// Good Test will be tested with addOrderTest
	}

	@Test
	public void addOrderTest() throws Exception {
	
		orderlineitem  = new OrderLineItem(1L , 5);
		List<OrderLineItem> lineitemlist = new ArrayList<OrderLineItem>();
		lineitemlist.add(orderlineitem);
		order = new Order(2L,"CARD",false,"PROCESSED",36.3,"MISSOURI",lineitemlist);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/order/")
				.content(toJson(order))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.orderId").isNumber()).andReturn();
		
		// This will test getOrderById
		  JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		  mvc.perform(MockMvcRequestBuilders.get("/order/"+json.get(
		  "orderId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		  .andExpect(jsonPath("$").isNotEmpty())
		  .andExpect(jsonPath("$.orderId").value(json.get("orderId")))
		  .andExpect(jsonPath("$.customerId").value(json.get("customerId")));
		 // This test get by customer By Id
		  mvc.perform(MockMvcRequestBuilders.get("/order/customer/"+json.get(
				  "customerId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				  .andExpect(jsonPath("$").isNotEmpty());
	}
	
	
	@Test
	public void deleteResourceTest() throws Exception {
		//add a resource
		orderlineitem  = new OrderLineItem(1L , 5);
		List<OrderLineItem> lineitemlist = new ArrayList<OrderLineItem>();
		lineitemlist.add(orderlineitem);
		order = new Order(3L,"CARD",false,"PROCESSED",36.3,"MISSOURI",lineitemlist);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/order/")
				.content(toJson(order))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.orderId").isNumber()).andReturn();
		
		//delete the resource
		JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		int orderId = (Integer)json.get("orderId");

		
		
		
		mvc.perform(MockMvcRequestBuilders.delete("/order/"+orderId)
		//.content(toJson(order))
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().string("Order deleted"));
		
		//confirm deletion
		mvc.perform(MockMvcRequestBuilders.get("/order/"+orderId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
		.andExpect(content().string("Order Not Found"));
	}
	
	@Test
	public void updateOrderTest() throws Exception {
		
		//Add a order
		orderlineitem  = new OrderLineItem(1L , 5);
		List<OrderLineItem> lineitemlist = new ArrayList<OrderLineItem>();
		lineitemlist.add(orderlineitem);
		order = new Order(4L,"CARD",false,"PROCESSED",36.3,"MISSOURI",lineitemlist);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/order/")
				.content(toJson(order))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.orderId").isNumber())
				.andExpect(jsonPath("$.paymentChannel").value("CARD")).andReturn();;
		
		// Get the order to check if the order is added
		  JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		  // Update the Order
		  Order	order1 = new Order(4L,"NetBanking",false,"PROCESSED",36.3,"MISSOURI",lineitemlist);
				order1.setOrderId(Long.valueOf(json.get("orderId").toString()));
				orderlineitem.setOrder(order1);
				JSONArray values = json.getJSONArray("orderLineItemList");
				orderlineitem.setOrderLineItem(Long.valueOf(values.getJSONObject(0).getString("orderLineItem")));
			mvc.perform(MockMvcRequestBuilders.patch("/order/")
					.content(toJson(order1))
		 			.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.paymentChannel").value("NetBanking")).andReturn();
				}

	 private byte[] toJson(Object r) throws Exception {
	        ObjectMapper map = new ObjectMapper();
	        return map.writeValueAsString(r).getBytes();
	    }

}

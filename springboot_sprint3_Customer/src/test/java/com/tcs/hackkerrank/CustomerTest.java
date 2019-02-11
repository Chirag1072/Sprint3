package com.tcs.hackkerrank;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.tcs.hackkerrank.model.Customer;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
//@WebAppConfiguration
public class CustomerTest {

	@Autowired
	private WebApplicationContext context;
	private Customer customer;
	private MockMvc mvc;
	//private String path="";

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getAllCustomersTest() throws Exception {
		// this is added to get atleast 1 request
		customer = new Customer("Nitin" , 6462860319L , "KANSAS" , "Male");
		mvc.perform(MockMvcRequestBuilders.post("/customer/")
				.content(toJson(customer))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.customerId").isNumber()).andReturn();
		
		mvc.perform(MockMvcRequestBuilders.get("/customer").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$").isNotEmpty())
			.andExpect(jsonPath("$[0].customerId").isNumber())
				.andExpect(status().isOk());
	}
	
	@Test
	public void getCustomerByIdTest() throws Exception {
		// Bad Test
		mvc.perform(MockMvcRequestBuilders.get("/customer/0").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
		.andExpect(content().string("Customer Not Found"));
		// Good Test will be tested with addCustomerTest
	}


	@Test
	public void addCustomerTest() throws Exception {
		
		customer = new Customer("Nitin" , 5738214186L , "MISSOURI" , "Male");
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/customer/")
				.content(toJson(customer))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.customerId").isNumber()).andReturn();
		
		// This will test getCustomerById
		  JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		  mvc.perform(MockMvcRequestBuilders.get("/customer/"+json.get(
		  "customerId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		  .andExpect(jsonPath("$").isNotEmpty())
		  .andExpect(jsonPath("$.customerId").value(json.get("customerId")))
		  .andExpect(jsonPath("$.customerName").value("Nitin"));
		 
	}
	
	
	@Test
	public void deleteResourceTest() throws Exception {
		//add a resource
		customer = new Customer("Nitin" , 6462860319L , "KANSAS" , "Male");
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/customer/")
				.content(toJson(customer))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.customerId").isNumber()).andReturn();
		
		//delete the resource
		JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		int customerId = (Integer)json.get("customerId");

		
		mvc.perform(MockMvcRequestBuilders.delete("/customer/0")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
				
		
		
		mvc.perform(MockMvcRequestBuilders.delete("/customer/"+customerId)
		//.content(toJson(customer))
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().string("Customer deleted"));
		
		//confirm deletion
		mvc.perform(MockMvcRequestBuilders.get("/customer/"+customerId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
		.andExpect(content().string("Customer Not Found"));
	}
	
	
	@Test
	public void deleteAllTest() throws Exception {
		//delete All resource
		mvc.perform(MockMvcRequestBuilders.delete("/customer/")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.get("/customer").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	
	@Test
	public void updateCustomerTest() throws Exception {
		
		//Add a customer
		customer = new Customer("Nitin" , 4747585869L , "Kansas" , "Male");
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/customer/")
				.content(toJson(customer))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.customerId").isNumber()).andReturn();
		
		// Get the customer to check if the customer is added
		  JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		  // Update the Customer
		  Customer  customer1 = new Customer("Jain" , 3693693693L , "MEXICO" , "Male");
			customer1.setCustomerId(Long.valueOf(json.get("customerId").toString()));
			mvc.perform(MockMvcRequestBuilders.put("/customer/")
					.content(toJson(customer1))
		 			.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.customerName").value("Jain")).andReturn();
			// Bad Test
			customer1 = new Customer("Jain" , 3693693693L , "MEXICO" , "Male");
			customer1.setCustomerId(0L);
			mvc.perform(MockMvcRequestBuilders.put("/customer/")
					.content(toJson(customer1))
		 			.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
					.andExpect(content().string("Customer Not Found")).andReturn();
			// Bad Test
	}

	/*@Test
	  public void contextLoads() {
	  }
	@Test
	public void applicationContextLoaded() {
	}

	@Test
	public void applicationContextTest() {
	    try {
			SpringApplication.main(new String[] {});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	 private byte[] toJson(Object r) throws Exception {
	        ObjectMapper map = new ObjectMapper();
	        return map.writeValueAsString(r).getBytes();
	    }

}

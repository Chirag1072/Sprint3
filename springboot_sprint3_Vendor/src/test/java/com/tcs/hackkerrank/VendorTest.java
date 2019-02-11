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
import com.tcs.hackkerrank.model.Vendor;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
//@WebAppConfiguration
public class VendorTest {

	@Autowired
	private WebApplicationContext context;
	private Vendor vendor;
	private MockMvc mvc;
	//private String path="";

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getAllVendorsTest() throws Exception {
		// this is added to get atleast 1 request
		vendor = new Vendor("Vendor-1" , 6462860319L , "asd@asd.com" , "vendor_user_12", "Vendor_Missouri");
		mvc.perform(MockMvcRequestBuilders.post("/vendor/")
				.content(toJson(vendor))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.vendorId").isNumber()).andReturn();
		
		mvc.perform(MockMvcRequestBuilders.get("/vendor").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$").isNotEmpty())
			.andExpect(jsonPath("$[0].vendorId").isNumber())
				.andExpect(status().isOk());
	}
	
	@Test
	public void getVendorByIdTest() throws Exception {
		// Bad Test
		mvc.perform(MockMvcRequestBuilders.get("/vendor/0").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
		.andExpect(content().string("Vendor Not Found"));
		// Good Test will be tested with addVendorTest
	}


	@Test
	public void addVendorTest() throws Exception {
		
		vendor = new Vendor("Vendor-1" , 6462860319L , "asd@asd.com" , "vendor_user_12", "Vendor_Missouri");
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/vendor/")
				.content(toJson(vendor))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.vendorId").isNumber()).andReturn();
		
		// This will test getVendorById
		  JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		  mvc.perform(MockMvcRequestBuilders.get("/vendor/"+json.get(
		  "vendorId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		  .andExpect(jsonPath("$").isNotEmpty())
		  .andExpect(jsonPath("$.vendorId").value(json.get("vendorId")))
		  .andExpect(jsonPath("$.vendorName").value("Vendor-1"));
		 
	}
	
	
	@Test
	public void deleteResourceTest() throws Exception {
		//add a resource
		vendor = new Vendor("Vendor-1" , 6462860319L , "asd@asd.com" , "vendor_user_12", "Vendor_Missouri");;
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/vendor/")
				.content(toJson(vendor))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.vendorId").isNumber()).andReturn();
		
		//delete the resource
		JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		int vendorId = (Integer)json.get("vendorId");

		
		mvc.perform(MockMvcRequestBuilders.delete("/vendor/0")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
				
		
		
		mvc.perform(MockMvcRequestBuilders.delete("/vendor/"+vendorId)
		//.content(toJson(vendor))
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().string("Vendor deleted"));
		
		//confirm deletion
		mvc.perform(MockMvcRequestBuilders.get("/vendor/"+vendorId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
		.andExpect(content().string("Vendor Not Found"));
	}
	
	
	@Test
	public void deleteAllTest() throws Exception {
		//delete All resource
		mvc.perform(MockMvcRequestBuilders.delete("/vendor/")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.get("/vendor").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	
	@Test
	public void updateVendorTest() throws Exception {
		
		//Add a vendor
		vendor = new Vendor("Vendor-1" , 6462860319L , "asd@asd.com" , "vendor_user_12", "Vendor_Missouri");;
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/vendor/")
				.content(toJson(vendor))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.vendorId").isNumber()).andReturn();
		
		// Get the vendor to check if the vendor is added
		  JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		  // Update the Vendor
		  Vendor  vendor1 = new Vendor("Vendor-Update" , 6462860319L , "asd@asd.com" , "vendor_user_12", "Vendor_Missouri");
			vendor1.setVendorId(Long.valueOf(json.get("vendorId").toString()));
			mvc.perform(MockMvcRequestBuilders.put("/vendor/")
					.content(toJson(vendor1))
		 			.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.vendorName").value("Vendor-Update")).andReturn();
			// Bad Test
			vendor1 =  new Vendor("Vendor-Update" , 6462860319L , "asd@asd.com" , "vendor_user_12", "Vendor_Missouri");
			vendor1.setVendorId(0L);
			mvc.perform(MockMvcRequestBuilders.put("/vendor/")
					.content(toJson(vendor1))
		 			.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
					.andExpect(content().string("Vendor Not Found")).andReturn();
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

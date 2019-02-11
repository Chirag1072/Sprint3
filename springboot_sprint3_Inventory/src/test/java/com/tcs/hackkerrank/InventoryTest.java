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
import com.tcs.hackkerrank.model.Inventory;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryTest {

	@Autowired
	private WebApplicationContext context;
	private Inventory item;
	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getAllItemsTest() throws Exception {
		// this is added to get atleast 1 request
		item = new Inventory("Item-1" , "Label1" , 22 , 2, 3.36);
		mvc.perform(MockMvcRequestBuilders.post("/item/")
				.content(toJson(item))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.skuId").isNumber()).andReturn();
		
		mvc.perform(MockMvcRequestBuilders.get("/item").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$").isNotEmpty())
			.andExpect(jsonPath("$[0].skuId").isNumber())
				.andExpect(status().isOk());
	}
	
	@Test
	public void getItemByIdTest() throws Exception {
		// Bad Test
		mvc.perform(MockMvcRequestBuilders.get("/item/0").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
		.andExpect(content().string("Item Not Found"));
		// Good Test will be tested with addItemTest
		//Springboot_sprint3_Inventory.main(new String[] {});
	}


	@Test
	public void addItemTest() throws Exception {
		
		item = new Inventory("Item-1" , "Label1" , 22 , 2, 3.36);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/item/")
				.content(toJson(item))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.skuId").isNumber()).andReturn();
		
		// This will test getItemById
		  JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		  mvc.perform(MockMvcRequestBuilders.get("/item/"+json.get(
		  "skuId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		  .andExpect(jsonPath("$").isNotEmpty())
		  .andExpect(jsonPath("$.skuId").value(json.get("skuId")))
		  .andExpect(jsonPath("$.productName").value("Item-1"));
		 
	}
	
	
	@Test
	public void deleteResourceTest() throws Exception {
		//add a resource
		item = new Inventory("Item-1" , "Label1" , 22 , 2, 3.36);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/item/")
				.content(toJson(item))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.skuId").isNumber()).andReturn();
		
		//delete the resource
		JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		int skuId = (Integer)json.get("skuId");

		
		mvc.perform(MockMvcRequestBuilders.delete("/item/0")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
				
		
		
		mvc.perform(MockMvcRequestBuilders.delete("/item/"+skuId)
		//.content(toJson(item))
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().string("Item deleted"));
		
		//confirm deletion
		mvc.perform(MockMvcRequestBuilders.get("/item/"+skuId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
		.andExpect(content().string("Item Not Found"));
	}
	
	
	@Test
	public void deleteAllTest() throws Exception {
		//delete All resource
		mvc.perform(MockMvcRequestBuilders.delete("/item/")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.get("/item").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	
	@Test
	public void updateItemTest() throws Exception {
		
		//Add a item
		item = new Inventory("Item-1" , "Label1" , 22 , 2, 3.36);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/item/")
				.content(toJson(item))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.skuId").isNumber()).andReturn();
		
		// Get the item to check if the item is added
		  JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		  // Update the Item
		  Inventory  item1 = new Inventory("Item-Update" , "Label1" , 22 , 2, 3.36);
			item1.setSkuId(Long.valueOf(json.get("skuId").toString()));
			mvc.perform(MockMvcRequestBuilders.put("/item/")
					.content(toJson(item1))
		 			.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.productName").value("Item-Update")).andReturn();
			// Bad Test
			item1 =  new Inventory("Item-1" , "Label1" , 22 , 2, 3.36);
			item1.setSkuId(0L);
			mvc.perform(MockMvcRequestBuilders.put("/item/")
					.content(toJson(item1))
		 			.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
					.andExpect(content().string("Item Not Found")).andReturn();
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

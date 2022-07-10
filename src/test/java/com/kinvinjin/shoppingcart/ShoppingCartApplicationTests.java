package com.kinvinjin.shoppingcart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingCartApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/index")).andExpect(status().isOk()).andReturn();
		assertEquals("Welcome!", result.getResponse().getContentAsString());
	}

	@Test
	public void shouldReturnCost() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/checkout?order=43N23P,234234")).andExpect(status().isOk()).andReturn();
		assertEquals("$5399.99", result.getResponse().getContentAsString());
	}

	@Test
	public void shouldReturnErrorMessage() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/checkout?order=234234,234234,234234")).andExpect(status().isOk()).andReturn();
		assertEquals("insufficient inventory quantity for Raspberry Pi B", result.getResponse().getContentAsString());
	}
}

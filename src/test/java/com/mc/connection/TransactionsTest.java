package com.mc.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.payments.model.Transaction;

public class TransactionsTest extends CityConnectionAbstractTest {
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void getSwaggerHome() throws Exception {
		String uri = "/";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
	}

	@Test
	public void getAllTransactions() throws Exception {
		String uri = "/all";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Transaction[] transactionList = super.mapFromJson(content, Transaction[].class);
		assertTrue(transactionList .length > 0);
	}

	@Test
	public void insertStation() throws Exception {
		String uri = "/transaction";

		Transaction tx = new Transaction();
		tx.setCreatedBy("user3@test.com");
		tx.setType("invoice");
		String request = super.mapToJson(tx);
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(request))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Transaction inserted successfully");
	}

	@Test
	public void insertStationFail() throws Exception {
		String uri = "/station";

		Transaction tx = new Transaction();
		tx.setCreatedBy("user3@test.com");
		tx.setType("invoice");
		String request = super.mapToJson(tx);
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(request))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
	}

}
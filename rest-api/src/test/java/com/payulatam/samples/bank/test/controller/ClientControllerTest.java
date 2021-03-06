package com.payulatam.samples.bank.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.server.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.controller.ClientController;
import com.payulatam.samples.bank.service.IClientService;

public class ClientControllerTest {

	MockMvc mockMvc;

	@InjectMocks
	ClientController clientController;

	@Mock
	IClientService clientService;

	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = standaloneSetup(clientController).setMessageConverters(
				new MappingJackson2HttpMessageConverter()).build();
	}

	@Test
	public void thatResponseIsTheNewClient() throws Exception {
		Client client = new Client();
		client.setName("restTest");
		client.setAddress("cra");
		client.setTelephone("123456");

		String json = String.format(
				"{ \"name\": \"%s\", \"address\": \"%s\", \"telephone\": \"%s\"}",
				client.getName(), client.getAddress(), client.getTelephone());

		when(clientService.create(any(String.class), any(String.class), any(String.class))).thenReturn(
				client);

//		this.mockMvc
//				.perform(
//						post("").
//						post("/clients/create").content(json).contentType(MediaType.APPLICATION_JSON)
//								.accept(MediaType.APPLICATION_JSON)).andDo(print())
//				.andExpect(status().isCreated());
//
//		verify(clientService).create(eq(client.getName()), eq(client.getAddress()),
//				eq(client.getTelephone()));

	}
}
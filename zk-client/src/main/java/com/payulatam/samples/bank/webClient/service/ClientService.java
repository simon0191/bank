package com.payulatam.samples.bank.webClient.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.payulatam.samples.bank.common.Client;

@Service
public class ClientService {

	public Client searchClientById(String id) throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI("http", "localhost:8080/rest-api", "/clients/"+id, null);
		System.out.println(uri.toString());
		Client client = restTemplate.getForObject(uri.toString(), Client.class);
		return client;
	}
	
	public Client createClient(String name,String address, String phoneNumber) throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		//String url = (new URI("http", "localhost:8080", "/rest-api/clients/create", null)).toString();
		String url = (new URI("http",null,"localhost",8080,"/rest-api/clients/create",null,null)).toString();
		Client client = new Client();
		client.setName(name);
		client.setAddress(address);
		client.setTelephone(phoneNumber);
		Client result = restTemplate.postForObject(url, client, Client.class);
		return result;
	}

}

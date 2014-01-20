package com.payulatam.samples.bank.webClient.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.utils.StringUtils;

@Service
public class ClientService {

	public Client searchClientById(String id) {
		RestTemplate restTemplate = new RestTemplate();
		Client client = restTemplate.getForObject(
				StringUtils.concatenate("http://localhost:8080/rest-api/clients/", id),
				Client.class);
		return client;
	}

	public Client createClient(String name, String address, String phoneNumber)
			throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String url = (new URI("http", null, "localhost", 8080, "/rest-api/clients/create", null,
				null)).toString();
		Client client = new Client();
		client.setName(name);
		client.setAddress(address);
		client.setTelephone(phoneNumber);
		Client result = restTemplate.postForObject(url, client, Client.class);
		return result;
	}

	public List<Client> searchClient(String id, String name, String address, String phoneNumber) {
		RestTemplate restTemplate = new RestTemplate();
		StringBuilder sb = new StringBuilder();
		if (id != null && !id.equals("")) {
			sb.append("id=");
			sb.append(id);
		}
		if (name != null && !name.equals("")) {
			sb.append("name=");
			sb.append(name);
		}
		if (address != null && !address.equals("")) {
			sb.append("address=");
			sb.append(address);
		}
		if (phoneNumber != null && !phoneNumber.equals("")) {
			sb.append("telephone=");
			sb.append(phoneNumber);
		}

		Client[] result = restTemplate.getForObject(
				"http://localhost:8080/rest-api/clients/search?" + sb.toString(), Client[].class);

		return Arrays.asList(result);
	}

	public List<Client> getAllClients() {
		return this.searchClient("", "", "", "");
	}

	public Client updateClient(String id, String name, String address, String phoneNumber) {
		RestTemplate restTemplate = new RestTemplate();
		Client client = new Client();
		client.setId(id);
		client.setName(name);
		client.setAddress(address);
		client.setTelephone(phoneNumber);
		
		String url = "http://localhost:8080/rest-api/clients/update";
		
		Client result = restTemplate.postForObject(url, client,Client.class);
		return result;

	}
}

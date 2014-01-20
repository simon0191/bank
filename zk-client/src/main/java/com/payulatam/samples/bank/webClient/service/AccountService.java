package com.payulatam.samples.bank.webClient.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.webClient.utils.StringUtils;

@Service
public class AccountService {

	public Account createAccount(String clientId) {

		RestTemplate restTemplate = new RestTemplate();

		Account result = restTemplate.getForObject(StringUtils.concatenate(
				"http://localhost:8080/rest-api/accounts/create/", clientId), Account.class);

		return result;
	}

}

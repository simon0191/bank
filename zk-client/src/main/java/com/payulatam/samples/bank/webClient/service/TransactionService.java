package com.payulatam.samples.bank.webClient.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.TransactionType;

@Service
public class TransactionService {

	public Transaction createTransaction(String accountId, TransactionType type, BigDecimal value) {
		
		RestTemplate restTemplate = new RestTemplate();
		Transaction t = new Transaction();
		t.setAccountId(accountId);
		t.setType(type);
		t.setValue(value);

		String url = "http://localhost:8080/rest-api/transactions/create";
		
		ResponseEntity<Transaction> re = restTemplate.postForEntity(url, t, Transaction.class);
		if(re.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			throw new IllegalStateException();
		}
//		Transaction result = restTemplate.postForObject(url, t, Transaction.class);
		return re.getBody();
		
	}

}

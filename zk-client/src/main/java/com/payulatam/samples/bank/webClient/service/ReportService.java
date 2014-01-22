package com.payulatam.samples.bank.webClient.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.payulatam.samples.bank.common.reports.TransactionReportItem;
import com.payulatam.samples.bank.common.reports.TransactionReportRequest;

@Service
public class ReportService {

	public List<TransactionReportItem> createReport(String clientId, Date startDate, Date endDate) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/rest-api/reports/transactions";
		TransactionReportRequest request = new TransactionReportRequest();
		request.setClientId(clientId);
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		
		//TODO: try, catch (org.springframework.web.client.HttpClientErrorException: 400 Petición incorrecta)
		ResponseEntity<TransactionReportItem[]> result = restTemplate.postForEntity(url, request, TransactionReportItem[].class);
		if(result.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			throw new IllegalStateException();
		}
		return Arrays.asList(result.getBody());
	}
	

}

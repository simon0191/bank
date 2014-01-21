package com.payulatam.samples.bank.webClient.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
		
		TransactionReportItem[] result = restTemplate.postForObject(url, request, TransactionReportItem[].class);
		
		return Arrays.asList(result);
	}
	

}

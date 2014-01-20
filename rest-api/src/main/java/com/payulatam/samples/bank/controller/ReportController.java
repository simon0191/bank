package com.payulatam.samples.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.payulatam.samples.bank.common.reports.TransactionReportItem;
import com.payulatam.samples.bank.common.reports.TransactionReportRequest;
import com.payulatam.samples.bank.service.ReportService;


@Controller
@RequestMapping(value = "/reports")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@ResponseBody
	@RequestMapping(value = "/transactions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	List<TransactionReportItem> generateTransactionsReport(@RequestBody TransactionReportRequest request) {
		List<TransactionReportItem> result = reportService.generateReport(request);
		return result;
	}
	
	

}

package com.payulatam.samples.bank.service;

import java.util.List;

import com.payulatam.samples.bank.common.reports.TransactionReportItem;
import com.payulatam.samples.bank.common.reports.TransactionReportRequest;

public interface IReportService {

	List<TransactionReportItem> generateReport(TransactionReportRequest request);

}
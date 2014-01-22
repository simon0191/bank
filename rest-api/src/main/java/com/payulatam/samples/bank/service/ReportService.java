package com.payulatam.samples.bank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.reports.TransactionReportItem;
import com.payulatam.samples.bank.common.reports.TransactionReportRequest;

@Service

public class ReportService {
	
	@Autowired
	private GigaSpace gigaSpace;
	
	@Autowired
	private ITransactionDao transactionDao;

	public List<TransactionReportItem> generateReport(TransactionReportRequest request) {
		Client client = gigaSpace.readById(Client.class,request.getClientId());
		if(client == null) {
			throw new NoSuchElementException("Client "+request.getClientId()+" not found");
		}
		List<Transaction> ts = transactionDao.searchByDateBetweenAndClient(request.getStartDate(), request.getEndDate(), request.getClientId());
		System.out.println("------------ "+ ts);
		Map<String,TransactionReportItem> map = new HashMap<String,TransactionReportItem>();
		
		for(Transaction t:ts) {
			if(!map.containsKey(t.getAccountId())) {
				map.put(t.getAccountId(), new TransactionReportItem(t.getAccountId()));
			}
			TransactionReportItem curr = map.get(t.getAccountId());
			switch(t.getType()) {
				case CREDIT:
					curr.setCredits(curr.getCredits().add(t.getValue()));
					break;
				case DEBIT:
					curr.setDebits(curr.getDebits().add(t.getValue()));
					break;
			}
			map.put(t.getAccountId(),curr);
		}
		
		List<TransactionReportItem> result = new ArrayList<TransactionReportItem>();
		for(String accountId:map.keySet()) {
			result.add(map.get(accountId));
		}
		System.out.println("------------ "+ result);
		return result;
	}

}

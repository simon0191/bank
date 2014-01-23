package com.payulatam.samples.bank.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.reports.TransactionReportItem;
import com.payulatam.samples.bank.common.reports.TransactionReportRequest;
import com.payulatam.samples.bank.service.IAccountService;
import com.payulatam.samples.bank.service.IReportService;
import com.payulatam.samples.bank.service.ITransactionService;

@Service
public class ReportService implements IReportService {
	
	@Autowired
	private GigaSpace gigaSpace;
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private ITransactionService transactionService;

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.impl.IReportService#generateReport(com.payulatam.samples.bank.common.reports.TransactionReportRequest)
	 */
	@Override
	public List<TransactionReportItem> generateReport(TransactionReportRequest request) {
		Client client = gigaSpace.readById(Client.class,request.getClientId());
		if(client == null) {
			throw new NoSuchElementException("Client "+request.getClientId()+" not found");
		}
		List<Transaction> ts = transactionService.searchByDateBetweenAndClient(request.getStartDate(), request.getEndDate(), request.getClientId());
		Map<String,TransactionReportItem> map = new HashMap<String,TransactionReportItem>();
		
		for(Transaction t:ts) {
			if(!map.containsKey(t.getAccountId())) {
				TransactionReportItem item = new TransactionReportItem(t.getAccountId());
				Account account = accountService.searchById(t.getAccountId());
				item.setBalance(account.getBalance());
				map.put(t.getAccountId(), item);
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
		return result;
	}

}

package com.payulatam.samples.bank.test.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.TransactionType;

public final class Fixtures {
	private Fixtures() {

	}

	public static Client standardClient() {
		Client result = Client.builder().name("Pepito Perez").telephone("5623487")
				.address("calle 123 cra 6778").build();
		return result;
	}

	public static Account standardAccount(String clientId) {
		return standardAccount(clientId,BigDecimal.TEN);
	}

	public static List<Transaction> validTransactions(String accountId) {
		Transaction dt = debitTransaction(accountId, BigDecimal.TEN);
		Transaction ct = creditTransaction(accountId, BigDecimal.TEN);
		return Arrays.asList(new Transaction[] { dt, dt, dt, ct, ct });
	}

	public static Transaction debitTransaction(String accountId, BigDecimal value) {
		return debitTransaction(accountId, value, new Date());
	}

	public static Transaction debitTransaction(String accountId, BigDecimal value, Date date) {
		return Transaction.builder().accountId(accountId).value(value).transactionDate(date)
				.type(TransactionType.DEBIT).build();
	}
	
	public static Transaction creditTransaction(String accountId, BigDecimal value) {
		return debitTransaction(accountId, value, new Date());
	}

	public static Transaction creditTransaction(String accountId, BigDecimal value, Date date) {
		return Transaction.builder().accountId(accountId).value(value).transactionDate(date)
				.type(TransactionType.CREDIT).build();
	}

	public static Account standardAccount(String clientId, BigDecimal value) {
		return Account.builder().clientId(clientId).balance(value).build();
	}

}

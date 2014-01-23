package com.payulatam.samples.bank.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.TransactionType;

public interface ITransactionService {

	Transaction create(String accountId, TransactionType type, BigDecimal value);

	Transaction searchByNumber(String id);

	List<Transaction> searchByAccountId(String accountId);

	List<Transaction> searchByClientId(String ownerId);

	List<Transaction> searchByDateBetweenAndAccount(Date startDate, Date endDate, String accountId);

	List<Transaction> searchByDateBetweenAndClient(Date startDate, Date endDate, String ownerId);

}
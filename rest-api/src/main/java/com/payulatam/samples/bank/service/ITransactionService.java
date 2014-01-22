package com.payulatam.samples.bank.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.TransactionType;

public interface ITransactionService {

	Transaction create(String accountId, TransactionType type, BigDecimal value)
			throws IllegalArgumentException, IllegalStateException, NoSuchElementException;

	Transaction searchByNumber(String id) throws NoSuchElementException;

	List<Transaction> searchByAccountId(String accountId)
			throws NoSuchElementException;

	List<Transaction> searchByClientId(String ownerId)
			throws NoSuchElementException;

	List<Transaction> searchByDateBetweenAndAccount(Date startDate, Date endDate,
			String accountId) throws NoSuchElementException, IllegalArgumentException;

	List<Transaction> searchByDateBetweenAndClient(Date startDate, Date endDate,
			String ownerId) throws NoSuchElementException;

}
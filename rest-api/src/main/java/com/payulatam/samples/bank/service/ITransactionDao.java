package com.payulatam.samples.bank.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.TransactionType;

public interface ITransactionDao {

	public abstract Transaction create(String accountId, TransactionType type, BigDecimal value)
			throws IllegalArgumentException, IllegalStateException, NoSuchElementException;

	public abstract Transaction searchByNumber(String id) throws NoSuchElementException;

	public abstract List<Transaction> searchByAccountId(String accountId)
			throws NoSuchElementException;

	public abstract List<Transaction> searchByClientId(String ownerId)
			throws NoSuchElementException;

	public abstract List<Transaction> searchByDateBetweenAndAccount(Date startDate, Date endDate,
			String accountId) throws NoSuchElementException, IllegalArgumentException;

	public abstract List<Transaction> searchByDateBetweenAndClient(Date startDate, Date endDate,
			String ownerId) throws NoSuchElementException;

}
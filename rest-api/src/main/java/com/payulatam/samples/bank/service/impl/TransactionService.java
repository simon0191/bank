package com.payulatam.samples.bank.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gigaspaces.client.WriteModifiers;
import com.gigaspaces.query.ISpaceQuery;
import com.j_spaces.core.client.SQLQuery;
import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.TransactionType;
import com.payulatam.samples.bank.service.IAccountService;
import com.payulatam.samples.bank.service.ITransactionService;

@Service
public class TransactionService implements ITransactionService {

	// @GigaSpaceContext
	@Autowired
	private GigaSpace gigaSpace;

	@Autowired
	private IAccountService accountService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.payulatam.samples.bank.service.ITransactionService#create(java.lang.String
	 * , com.payulatam.samples.bank.common.TransactionType,
	 * java.math.BigDecimal)
	 */
	// TODO: Agregar manejador de transacciones
	// http://docs.spring.io/spring/docs/2.0.8/reference/transaction.html
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public Transaction create(String accountId, TransactionType type, BigDecimal value)
			throws IllegalArgumentException, IllegalStateException, NoSuchElementException {
		List<String> invalidArguments = new ArrayList<String>();
		if (value.compareTo(BigDecimal.ZERO) <= 0) {
			invalidArguments.add("Transaction value should be >= 0");
		}
		if (type == null) {
			invalidArguments.add("Transaction type can not be null");
		}
		if (!invalidArguments.isEmpty()) {
			throw new IllegalArgumentException(invalidArguments.toString());
		}

		Account account = gigaSpace.readById(Account.class, accountId);
		if (account == null) {
			throw new NoSuchElementException();
		}
		if (type.equals(TransactionType.CREDIT) && account.getBalance().compareTo(value) < 0) {
			throw new IllegalStateException();
		}
		Transaction result = new Transaction();
		result.setAccountId(account.getId());
		result.setTransactionDate(new Date());
		result.setValue(value);
		result.setType(type);

		switch (type) {
		case CREDIT:
			account.setBalance(account.getBalance().subtract(value));
			break;
		case DEBIT:
			account.setBalance(account.getBalance().add(value));
			break;
		}
		gigaSpace.write(result, WriteModifiers.WRITE_ONLY);
		gigaSpace.write(account, WriteModifiers.UPDATE_ONLY);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.payulatam.samples.bank.service.ITransactionService#searchByNumber(java
	 * .lang.String)
	 */
	@Override
	public Transaction searchByNumber(String id) throws NoSuchElementException {
		Transaction result = gigaSpace.readById(Transaction.class, id);
		if (result == null) {
			throw new NoSuchElementException();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.payulatam.samples.bank.service.ITransactionService#searchByAccountId(
	 * java.lang.String)
	 */
	@Override
	public List<Transaction> searchByAccountId(String accountId) throws NoSuchElementException {
		Account account = gigaSpace.readById(Account.class, accountId);
		if (account == null) {
			throw new NoSuchElementException();
		}
		ISpaceQuery<Transaction> query = new SQLQuery<Transaction>(Transaction.class,
				"accountId = ?", accountId);
		Transaction[] result = gigaSpace.readMultiple(query);
		return Arrays.asList(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.payulatam.samples.bank.service.ITransactionService#searchByClientId(java
	 * .lang.String)
	 */
	@Override
	public List<Transaction> searchByClientId(String ownerId) throws NoSuchElementException {
		List<Account> accounts = accountService.searchAccountsByClientId(ownerId);
		List<Transaction> result = new ArrayList<Transaction>();
		for (Account a : accounts) {
			result.addAll(this.searchByAccountId(a.getId()));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.payulatam.samples.bank.service.ITransactionService#
	 * searchByDateBetweenAndAccount(java.util.Date, java.util.Date,
	 * java.lang.String)
	 */
	@Override
	public List<Transaction> searchByDateBetweenAndAccount(Date startDate, Date endDate,
			String accountId) throws NoSuchElementException, IllegalArgumentException {
		Account account = gigaSpace.readById(Account.class, accountId);
		if (account == null) {
			throw new NoSuchElementException("Non existent account");
		}
		if (endDate.before(startDate)) {
			throw new IllegalArgumentException("startDate should be before endDate");
		}
		ISpaceQuery<Transaction> query = new SQLQuery<Transaction>(Transaction.class,
				"transactionDate >= ? AND transactionDate <= ? AND accountId = ?", startDate,
				endDate, accountId);
		Transaction[] result = gigaSpace.readMultiple(query);
		return Arrays.asList(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.payulatam.samples.bank.service.ITransactionService#
	 * searchByDateBetweenAndClient(java.util.Date, java.util.Date,
	 * java.lang.String)
	 */
	@Override
	public List<Transaction> searchByDateBetweenAndClient(Date startDate, Date endDate,
			String ownerId) throws NoSuchElementException {
		if (endDate.before(startDate)) {
			throw new IllegalArgumentException("startDate should be before endDate");
		}
		List<Account> accounts = accountService.searchAccountsByClientId(ownerId);
		List<Transaction> result = new ArrayList<Transaction>();
		for (Account a : accounts) {
			result.addAll(this.searchByDateBetweenAndAccount(startDate, endDate, a.getId()));
		}
		return result;
	}

}

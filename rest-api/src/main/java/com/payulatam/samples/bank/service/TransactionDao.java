package com.payulatam.samples.bank.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gigaspaces.client.WriteModifiers;
import com.gigaspaces.query.ISpaceQuery;
import com.j_spaces.core.client.SQLQuery;
import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.TransactionType;

@Service
public class TransactionDao {

	// @GigaSpaceContext
	@Autowired
	private GigaSpace gigaSpace;

	@Autowired
	private AccountDao accountDao;

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
		result.setDate(new Date());
		result.setValue(value);

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

	public Transaction searchByNumber(String id) throws NoSuchElementException {
		Transaction result = gigaSpace.readById(Transaction.class, id);
		if (result == null) {
			throw new NoSuchElementException();
		}
		return result;
	}

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

	public List<Transaction> searchByClientId(String ownerId) throws NoSuchElementException {
		List<Account> accounts = accountDao.searchAccountsByClientId(ownerId);
		List<Transaction> result = new ArrayList<Transaction>();
		for (Account a : accounts) {
			result.addAll(this.searchByAccountId(a.getId()));
		}
		return result;
	}

	public List<Transaction> searchByDateBetweenAndAccount(Date startDate, Date endDate,
			String accountId) throws NoSuchElementException, IllegalArgumentException {
		Account account = gigaSpace.readById(Account.class, accountId);
		if (account == null) {
			throw new NoSuchElementException("Non existent account");
		}
		if(endDate.before(startDate) || endDate.after(new Date())) {
			throw new IllegalArgumentException("startDate should be before endDate and both should be before today");
		}
		ISpaceQuery<Transaction> query = new SQLQuery<Transaction>(Transaction.class,
				"date >= ? AND date <= ? AND accountId = ?", startDate, endDate, accountId);
		Transaction[] result = gigaSpace.readMultiple(query);
		return Arrays.asList(result);
	}

	public List<Transaction> searchByDateBetweenAndClient(Date startDate, Date endDate,
			String ownerId) throws NoSuchElementException {
		if(endDate.before(startDate) || endDate.after(new Date())) {
			throw new IllegalArgumentException("startDate should be before endDate and both should be before today");
		}
		List<Account> accounts = accountDao.searchAccountsByClientId(ownerId);
		List<Transaction> result = new ArrayList<Transaction>();
		for(Account a:accounts) {
			result.addAll(this.searchByDateBetweenAndAccount(startDate, endDate, a.getId()));
		}
		return result;
	}

}

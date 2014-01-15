package com.payulatam.samples.bank.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gigaspaces.client.WriteModifiers;
import com.j_spaces.core.client.SQLQuery;
import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.common.TransactionType;

@Service
public class TransactionDao {

	@GigaSpaceContext
	private GigaSpace gigaSpace;

	@Autowired
	private Utils utils;
	
	@Autowired
	private AccountDao accountDao;

	public void setGigaSpace(GigaSpace gigaSpace) {
		this.gigaSpace = gigaSpace;
	}

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
		Account account = gigaSpace.readById(Account.class,accountId);
		if(account == null) {
			throw new NoSuchElementException();
		}
		Transaction[] result = gigaSpace.readMultiple(new SQLQuery<Transaction>("accountId = ?",accountId));
		return Arrays.asList(result);
	}

	public List<Transaction> searchByClientId(String ownerId) throws NoSuchElementException {
		Client owner = gigaSpace.readById(Client.class,ownerId);
		if(owner == null) {
			throw new NoSuchElementException();
		}
		
		return null;
	}

}

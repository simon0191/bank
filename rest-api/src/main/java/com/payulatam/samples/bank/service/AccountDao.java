package com.payulatam.samples.bank.service;

import java.math.BigDecimal;
import java.util.Arrays;
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

@Service
public class AccountDao {

	@GigaSpaceContext
	private GigaSpace gigaSpace;

	@Autowired
	private Utils utils;

	public void setGigaSpace(GigaSpace gigaSpace) {
		this.gigaSpace = gigaSpace;

	}

	public Account create(String clientId) throws NoSuchElementException {
		Client owner = gigaSpace.readById(Client.class, clientId);
		if (owner == null) {
			throw new NoSuchElementException();
		}
		Account result = new Account();
		result.setBalance(BigDecimal.ZERO);
		result.setClientId(owner.getId());

		gigaSpace.write(result, WriteModifiers.WRITE_ONLY);

		return result;
	}

	public Account delete(String id) {
		Account result = gigaSpace.takeById(Account.class, id);
		if (result == null) {
			throw new NoSuchElementException();
		}
		return result;
	}

	public Account searchById(String id) throws NoSuchElementException {
		Account result = gigaSpace.readById(Account.class, id);
		if (result == null) {
			throw new NoSuchElementException();
		}
		return result;
	}

	public List<Account> searchAccountsByClientId(String clientId) {
		Client client = gigaSpace.readById(Client.class,clientId);
		if(client == null) {
			throw new NoSuchElementException();
		}
		Account[] result = gigaSpace.readMultiple(new SQLQuery<Account>(Account.class, "clientId = ?", clientId));
		return Arrays.asList(result);
	}

}

package com.payulatam.samples.bank.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
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
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.common.Transaction;
import com.payulatam.samples.bank.service.IAccountDao;
import com.payulatam.samples.bank.service.Utils;

@Service
public class AccountDao implements IAccountDao {

	//@GigaSpaceContext
	@Autowired
	private GigaSpace gigaSpace;

	@Autowired
	private Utils utils;

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.IAccountDao#create(java.lang.String)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.IAccountDao#delete(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Account delete(String id) {
		Account result = gigaSpace.readById(Account.class, id);
		if (result == null) {
			throw new NoSuchElementException("Account with Id <"+id+"> does not exist");
		}
		Transaction template = new Transaction();
		template.setAccountId(result.getId());
		gigaSpace.takeMultiple(template);
		result = gigaSpace.takeById(Account.class, id);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.IAccountDao#searchById(java.lang.String)
	 */
	@Override
	public Account searchById(String id) throws NoSuchElementException {
		Account result = gigaSpace.readById(Account.class, id);
		if (result == null) {
			throw new NoSuchElementException();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.IAccountDao#searchAccountsByClientId(java.lang.String)
	 */
	@Override
	public List<Account> searchAccountsByClientId(String clientId) {
		Client client = gigaSpace.readById(Client.class,clientId);
		if(client == null) {
			throw new NoSuchElementException();
		}
		ISpaceQuery<Account> query = new SQLQuery<Account>(Account.class, "clientId = ?", clientId);
		Account[] result = gigaSpace.readMultiple(query);
		return Arrays.asList(result);
	}

}

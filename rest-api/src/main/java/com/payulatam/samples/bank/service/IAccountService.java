package com.payulatam.samples.bank.service;

import java.util.List;

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Client;

/**
 * @author simon.soriano
 *
 */
public interface IAccountService {

	
	/**
	 * @param clientId
	 * @return The new Account associated with the {@link Client} with id = <code>clientId</code>
	 */
	Account create(String clientId);

	/**
	 * @param id
	 * @return
	 */
	Account delete(String id);
	/**
	 * 
	 * @param id
	 * @return
	 */
	Account searchById(String id);

	List<Account> searchAccountsByClientId(String clientId);
	

}
package com.payulatam.samples.bank.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gigaspaces.client.WriteModifiers;
import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.service.IAccountService;
import com.payulatam.samples.bank.service.IClientService;
import com.payulatam.samples.bank.utils.Utils;

@Service
public class ClientService implements IClientService {

	//@GigaSpaceContext
	@Autowired
	private GigaSpace gigaSpace;
	
	@Autowired
	private IAccountService accountService;

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.IClientService#create(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Client create(String name, String address, String telephone) {

		this.validate(name, address, telephone);

		Client result = new Client();
		result.setName(name);
		result.setAddress(address);
		result.setTelephone(telephone);
		
		gigaSpace.write(result,WriteModifiers.WRITE_ONLY);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.IClientService#update(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Client update(String id, String name, String address, String telephone) {
		Client result = gigaSpace.readById(Client.class, id);
		if (result == null) {
			throw new NoSuchElementException();
		}
		if (!name.equals("")) {
			result.setName(name);
		}
		if (!address.equals("")) {
			result.setAddress(address);
		}
		if (!telephone.equals("")) {
			result.setTelephone(telephone);
		}
		this.validate(result.getName(), result.getAddress(), result.getTelephone());
		gigaSpace.write(result, WriteModifiers.UPDATE_ONLY);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.IClientService#delete(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Client delete(String id) {
		Client result = gigaSpace.readById(Client.class, id);
		if (result == null) {
			throw new NoSuchElementException();
		}
		Account template = new Account();
		template.setClientId(result.getId());
		Account[] accounts = gigaSpace.readMultiple(template);
		for(Account a:accounts) {
			accountService.delete(a.getId());
		}
		result = gigaSpace.takeById(Client.class, id);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.IClientService#searchById(java.lang.String)
	 */
	@Override
	public Client searchById(String id) {
		Client result = gigaSpace.readById(Client.class, id);
		if (result == null) {
			throw new NoSuchElementException();
		}
		return result;
	}
	
	@Override
	public List<Client> search(String clientId, String name, String address, String telephone) {
		Client template = new Client();
		if (clientId != null && !clientId.equals("")) {
			template.setId(clientId);
		}
		if (address != null && !address.equals("")) {
			template.setAddress(address);
		}
		if (telephone != null && !telephone.equals("")) {
			template.setTelephone(telephone);
		}
		if (name != null && !name.equals("")) {
			template.setName(name);
		}
		
		this.validate(template.getName(), template.getAddress(), template.getTelephone());
		Client[] result = gigaSpace.readMultiple(template);
		return Arrays.asList(result);
	}
	
	
	private void validate(String name, String address, String telephone) {
		List<String> invalidFields = new ArrayList<String>();
		boolean hasInvalidFields = false;
		if (telephone!= null && !Utils.validateTelephone(telephone)) {
			hasInvalidFields = true;
			invalidFields.add("telephone");
		}
		if (address != null && !Utils.validateAddress(address)) {
			hasInvalidFields = true;
			invalidFields.add("address");
		}
		if (name != null && !Utils.validateName(name)) {
			hasInvalidFields = true;
			invalidFields.add("name");
		}
		if (hasInvalidFields) {
			throw new IllegalArgumentException("Invalid fields: " + invalidFields.toString());
		}
	}

}

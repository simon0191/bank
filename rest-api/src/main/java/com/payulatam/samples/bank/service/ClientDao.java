package com.payulatam.samples.bank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gigaspaces.client.WriteModifiers;
import com.payulatam.samples.bank.common.Client;

@Service
public class ClientDao implements IClientDao {

	//@GigaSpaceContext
	@Autowired
	private GigaSpace gigaSpace;

	@Autowired
	private Utils utils;

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.IClientDao#create(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Client create(String name, String address, String telephone)
			throws IllegalArgumentException {

		this.validate(name, address, telephone);

		Client result = new Client();
		result.setName(name);
		result.setAddress(address);
		result.setTelephone(telephone);
		
		gigaSpace.write(result,WriteModifiers.WRITE_ONLY);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.IClientDao#update(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Client update(String id, String name, String address, String telephone)
			throws IllegalArgumentException, NoSuchElementException {
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
	 * @see com.payulatam.samples.bank.service.IClientDao#delete(java.lang.String)
	 */
	@Override
	public Client delete(String id) {
		Client result = gigaSpace.takeById(Client.class, id);
		if (result == null) {
			throw new NoSuchElementException();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.payulatam.samples.bank.service.IClientDao#searchById(java.lang.String)
	 */
	@Override
	public Client searchById(String id) {
		Client result = gigaSpace.readById(Client.class, id);
		if (result == null) {
			throw new NoSuchElementException();
		}
		return result;
	}
	private void validate(String name, String address, String telephone)
			throws IllegalArgumentException {
		List<String> invalidFields = new ArrayList<String>();
		boolean hasInvalidFields = false;
		if (!utils.validateTelephone(telephone)) {
			hasInvalidFields = true;
			invalidFields.add("telephone");
		}
		if (!utils.validateAddress(address)) {
			hasInvalidFields = true;
			invalidFields.add("address");
		}
		if (!utils.validateName(name)) {
			hasInvalidFields = true;
			invalidFields.add("name");
		}
		if (hasInvalidFields) {
			throw new IllegalArgumentException("Invalid fields: " + invalidFields.toString());
		}
	}

}

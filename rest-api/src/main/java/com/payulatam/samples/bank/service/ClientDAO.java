package com.payulatam.samples.bank.service;

import java.util.ArrayList;
import java.util.List;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gigaspaces.client.WriteModifiers;
import com.payulatam.samples.bank.common.Client;

@Service
public class ClientDAO {

	@GigaSpaceContext
	private GigaSpace gigaSpace;
	
	@Autowired
	private Utils utils;

	public Client create(String name, String address, String telephone) throws IllegalArgumentException {
		
		List<String> invalidFields = new ArrayList<String>();
		boolean hasInvalidFields = false;
		if(!utils.validateTelephone(telephone)) {
			hasInvalidFields = true;
			invalidFields.add("telephone");
		}
		if(!utils.validateAddress(address)) {
			hasInvalidFields = true;
			invalidFields.add("address");
		}
		if(!utils.validateName(name)) {
			hasInvalidFields = true;
			invalidFields.add("name");
		}
		if(hasInvalidFields) {
			throw new IllegalArgumentException("Invalid fields: "+invalidFields.toString());
		}
		
		Client result = new Client();
		result.setName(name);
		result.setAddress(address);
		result.setTelephone(telephone);
		gigaSpace.write(result);
		return result;
	}

	public Client update(String id, String name, String address, String telephone) {
		Client result = gigaSpace.readById(Client.class, id);
		if(!name.equals("")) {
			result.setName(name);
		}
		if(!address.equals("")) {
			result.setAddress(address);
		}
		if(!telephone.equals("")) {
			result.setTelephone(telephone);
		}
		gigaSpace.write(result, WriteModifiers.UPDATE_ONLY);
		return result;
	}
	
	public Client delete(String id) {
		Client result = gigaSpace.takeById(Client.class, id);
		return result;
	}
	
	public Client searchById(String id) {
		Client result = gigaSpace.readById(Client.class,id);
		return result;
	}
	
	public void setGigaSpace(GigaSpace gigaSpace) {
		this.gigaSpace = gigaSpace;
	}

	public GigaSpace getGigaSpace() {
		return gigaSpace;
	}
}

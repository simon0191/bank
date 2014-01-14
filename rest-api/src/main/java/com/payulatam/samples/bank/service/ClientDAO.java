package com.payulatam.samples.bank.service;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.stereotype.Service;

import com.gigaspaces.client.WriteModifiers;
import com.j_spaces.core.LeaseContext;
import com.payulatam.samples.bank.common.Client;

@Service
public class ClientDAO {

	@GigaSpaceContext
	private GigaSpace gigaSpace;
	
	public Client create(String name, String address, String telephone) {
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

}

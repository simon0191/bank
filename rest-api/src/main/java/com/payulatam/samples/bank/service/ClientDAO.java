package com.payulatam.samples.bank.service;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.stereotype.Service;

import com.payulatam.samples.bank.common.Client;

@Service
public class ClientDAO {

	@GigaSpaceContext
	private GigaSpace gigaSpace;
	
	
	public String getName() {
		String result = "Santiago";
		try {
			Client client = new Client();
			client.setName("Homero");
			client.setAddress("Springfield");
			client.setTelephone("123456");
			gigaSpace.write(client);
			result+=gigaSpace.getName();
		} catch(Exception e) {
			System.out.println("-------------- :( :(");
			e.printStackTrace();
		}
		return result;
	}

}

package com.payulatam.samples.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.service.IClientDao;

@Controller
@RequestMapping(value = "/clients")
public class ClientController {

	/*
	 * @Autowired private GigaSpace gigaSpace;
	 */
	@Autowired
	IClientDao clientDAO;

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Client create(@RequestBody Client client) {
		Client result = clientDAO.create(client.getName(), client.getAddress(),
				client.getTelephone());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Client update(@RequestBody Client client) {
		Client result = clientDAO.update(client.getId(), client.getName(), client.getAddress(), client.getTelephone());
		return result;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Client delete(@PathVariable(value="id") String clientId) {
		Client result = clientDAO.delete(clientId);
		return result;
	}	
	
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Client searchById(@PathVariable(value="id")  String clientId) {
		Client result = clientDAO.searchById(clientId);
		return result;
	}	
	

}
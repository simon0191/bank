package com.payulatam.samples.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.service.ClientDao;

@Controller
@RequestMapping(value = "/clients")
public class ClientController {

	/*
	 * @Autowired private GigaSpace gigaSpace;
	 */
	@Autowired
	ClientDao clientDAO;

	@ResponseBody
	@RequestMapping(
			value = "/create",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  Client create(
			@RequestParam String name,
			@RequestParam String address,
			@RequestParam String telephone
			) {
		Client result = clientDAO.create(name,address,telephone);
		return result;
	}

	@ResponseBody
	@RequestMapping(
			value = "/update",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  Client update(
			@RequestParam String id,
			@RequestParam(required=false, defaultValue="") String name,
			@RequestParam(required=false, defaultValue="") String address,
			@RequestParam(required=false, defaultValue="") String telephone
			) {
		Client result = clientDAO.update(id,name,address,telephone);
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Dog sayHello() {
		String result = "Simon";
		return new Dog(result);
	}
	

}
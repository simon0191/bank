package com.payulatam.samples.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.payulatam.samples.bank.service.ClientDAO;

@Controller
@RequestMapping
public class ClientController {

	/*
	 * @Autowired private GigaSpace gigaSpace;
	 */
	@Autowired
	ClientDAO clientDAO;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Dog sayHello() {
		String result = "Simon";
		try {
			result+=clientDAO.getName();
		}
		catch(Exception e) {
			System.out.println("------------------ :(");
			e.printStackTrace();
		}
		return new Dog(result);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Dog changeGroup(@PathVariable String id) {
		return new Dog("" + id);
	}

}
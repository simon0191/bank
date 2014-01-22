package com.payulatam.samples.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.service.IAccountService;


@Controller
@RequestMapping(value = "/accounts")
public class AccountController {
	
	@Autowired
	IAccountService accountService;

	@ResponseBody
	@RequestMapping(value = "/create/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Account create(@PathVariable(value="id") String clientId) {
		Account result = accountService.create(clientId);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Account delete(@PathVariable(value="id") String accountId) {
		Account result = accountService.delete(accountId);
		return result;
	}	
	
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Account searchById(@PathVariable(value="id") String accountId) {
		Account result = accountService.searchById(accountId);
		return result;
	}	
	
	@ResponseBody
	@RequestMapping(value = "/searchByClient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Account> searchByClientId(@PathVariable(value="id") String clientId) {
		List<Account> result = accountService.searchAccountsByClientId(clientId);
		return result;
	}	
	
	
}

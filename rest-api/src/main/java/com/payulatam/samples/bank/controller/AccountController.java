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
import com.payulatam.samples.bank.service.IAccountDao;


@Controller
@RequestMapping(value = "/accounts")
public class AccountController {
	
	@Autowired
	IAccountDao accountDao;

	@ResponseBody
	@RequestMapping(value = "/create/{clientId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Account create(@PathVariable String clientId) {
		Account result = accountDao.create(clientId);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Account delete(@PathVariable String accountId) {
		Account result = accountDao.delete(accountId);
		return result;
	}	
	
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Account searchById(@PathVariable String accountId) {
		Account result = accountDao.searchById(accountId);
		return result;
	}	
	
	@ResponseBody
	@RequestMapping(value = "/searchByClient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Account> searchByClientId(@PathVariable String clientId) {
		List<Account> result = accountDao.searchAccountsByClientId(clientId);
		return result;
	}	
	
	
}

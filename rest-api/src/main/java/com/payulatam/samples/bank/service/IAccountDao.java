package com.payulatam.samples.bank.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.payulatam.samples.bank.common.Account;

public interface IAccountDao {

	Account create(String clientId);

	Account delete(String id);

	Account searchById(String id);

	List<Account> searchAccountsByClientId(String clientId);

}
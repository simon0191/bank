package com.payulatam.samples.bank.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.payulatam.samples.bank.common.Account;

public interface IAccountDao {

	public abstract Account create(String clientId) throws NoSuchElementException;

	public abstract Account delete(String id);

	public abstract Account searchById(String id) throws NoSuchElementException;

	public abstract List<Account> searchAccountsByClientId(String clientId);

}
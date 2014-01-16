package com.payulatam.samples.bank.service;

import java.util.NoSuchElementException;

import com.payulatam.samples.bank.common.Client;

public interface IClientDao {

	public Client create(String name, String address, String telephone)
			throws IllegalArgumentException;

	public Client update(String id, String name, String address, String telephone)
			throws IllegalArgumentException, NoSuchElementException;

	public Client delete(String id);

	public Client searchById(String id);

}
package com.payulatam.samples.bank.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.payulatam.samples.bank.common.Client;

public interface IClientService {

	Client create(String name, String address, String telephone);

	Client update(String id, String name, String address, String telephone);

	Client delete(String id);

	Client searchById(String id);

	List<Client> search(String clientId, String name, String address, String telephone);

}
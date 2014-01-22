package com.payulatam.samples.bank.webClient.view.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Client;

//TODO: 
public final class ViewUtils {

	private ViewUtils() {

	}

	public static void populateClientsGrid(Grid clientsGrid, List<Client> clients) {
		ListModelList<Client> clientsModel = new ListModelList<Client>(clients);
		clientsGrid.setModel(clientsModel);
	}

	public static void populateClientsGrid(Grid clientsGrid, Client client) {
		populateClientsGrid(clientsGrid, Arrays.asList(client));
	}

	public static void populateClientsCombo(Combobox clientsCombo, List<Client> clients,
			Client selectedClient) {
		ListModelList<Client> clientListModel = new ListModelList<Client>(clients);
		clientListModel.addToSelection(selectedClient);
		clientsCombo.setModel(clientListModel);
		if(!clientListModel.isEmpty()) {
			Events.postEvent(Events.ON_CHANGE,clientsCombo,null);	
		}
		
	}

	public static void populateClientsCombo(Combobox clientsCombo, List<Client> clients) {
		populateClientsCombo(clientsCombo, clients, clients.get(0));
	}

	public static void populateAccountsCombo(Combobox accountsCombo, List<Account> accounts) {
		ListModelList<Account> accountListModel = new ListModelList<Account>(accounts);
		if(!accountListModel.isEmpty()){
			accountListModel.addToSelection(accountListModel.get(0));
		}
		accountsCombo.setModel(accountListModel);

	}

	public static void populateAccountsGrid(Grid accountsGrid, List<Account> accounts) {
		ListModelList<Account> accountModel = new ListModelList<Account>(accounts);
		accountsGrid.setModel(accountModel);
	}

}

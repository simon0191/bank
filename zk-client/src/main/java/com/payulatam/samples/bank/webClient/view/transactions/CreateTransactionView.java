package com.payulatam.samples.bank.webClient.view.transactions;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.SimpleConstraint;

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.service.AccountService;
import com.payulatam.samples.bank.webClient.service.ClientService;
import com.payulatam.samples.bank.webClient.utils.StringUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CreateTransactionView extends SelectorComposer<Component> {
	private static final long serialVersionUID = 8909979100495278052L;

	@WireVariable
	private AccountService accountService;

	@WireVariable
	private ClientService clientService;

	@Wire
	private Combobox clientsCombo;

	@Wire
	private Combobox accountsCombo;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		configClientsCombo();
		configAccountsCombo();

		populateClientsCombo();
	}

	
	@Listen("onChange = #clientsCombo")
	public void onChangeClientsCombo() {
		String clientId = (String) clientsCombo.getSelectedItem().getAttribute("clientId");
		List<Account> accounts = accountService.searchAccountsByClient(clientId);
		populateAccountsCombo(accounts);
	}
	
	private void populateAccountsCombo(List<Account> accounts) {
		ListModel<Account> accountListModel = new ListModelList<Account>(accounts);
		accountsCombo.setModel(accountListModel );
	}


	private void configAccountsCombo() {
		accountsCombo.setItemRenderer(new ComboitemRenderer<Account>() {
			@Override
			public void render(Comboitem item, Account account, int index) throws Exception {
				NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(Locale.US);
				usdCostFormat.setMinimumFractionDigits(1);
				usdCostFormat.setMaximumFractionDigits(2);
				
				item.setLabel(account.getId());
				item.setDescription(usdCostFormat.format(account.getBalance().doubleValue()));
				item.setAttribute("accountId", account.getId());
			}
		});
		clientsCombo.setConstraint(new SimpleConstraint(SimpleConstraint.NO_EMPTY));

	}

	private void configClientsCombo() {
		clientsCombo.setItemRenderer(new ComboitemRenderer<Client>() {
			@Override
			public void render(Comboitem item, Client client, int index) throws Exception {
				String label = StringUtils.concatenate(client.getName(), " - ", client.getId());
				item.setLabel(label);
				item.setAttribute("clientId", client.getId());
			}
		});
		clientsCombo.setConstraint(new SimpleConstraint(SimpleConstraint.NO_EMPTY));
	}

	private void populateClientsCombo() {
		List<Client> clients = clientService.getAllClients();
		ListModel<Client> clientListModel = new ListModelList<Client>(clients);
		clientsCombo.setModel(clientListModel);
	}
	
	

}

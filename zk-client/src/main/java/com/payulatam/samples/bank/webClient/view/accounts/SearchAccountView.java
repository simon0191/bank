package com.payulatam.samples.bank.webClient.view.accounts;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.webClient.service.AccountService;
import com.payulatam.samples.bank.webClient.service.ClientService;
import com.payulatam.samples.bank.webClient.view.utils.ViewUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SearchAccountView extends SelectorComposer<Component> {
	private static final long serialVersionUID = 513770219922545167L;

	@WireVariable
	private AccountService accountService;

	@WireVariable
	private ClientService clientService;

	@Wire
	private Button searchAccountBtn;

	@Wire("include #clientsCombo")
	private Combobox clientsCombo;

	@Wire
	private Grid searchAccountForm;

	@Wire("include #accountsGrid")
	private Grid accountsGrid;

	// TODO:
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		configSearchAccountBtn();

		ViewUtils.populateClientsCombo(clientsCombo, clientService.getAllClients());

	}

	private void configSearchAccountBtn() {
		this.searchAccountBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event ev) throws Exception {
				String clientId = (String) clientsCombo.getSelectedItem().getAttribute("clientId");
				List<Account> accounts = accountService.searchAccountsByClient(clientId);
				ViewUtils.populateAccountsGrid(accountsGrid,accounts);
				
			}

		});

	}
}

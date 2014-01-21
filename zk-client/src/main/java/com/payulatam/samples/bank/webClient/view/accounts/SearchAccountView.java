package com.payulatam.samples.bank.webClient.view.accounts;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;

import com.payulatam.samples.bank.webClient.service.AccountService;
import com.payulatam.samples.bank.webClient.service.ClientService;

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
	private Combobox accountsCombo;

	@Wire
	private Grid searchAccountForm;

	@Wire("include #resultsGrid")
	private Grid resultsGrid;
	
	//TODO:
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
//		initClientsCombo();
//		initAccountsCombo();
//		initSearchAccountBtn();
		
	}

	private void initClientsCombo() {
		
		
	}

}

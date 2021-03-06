package com.payulatam.samples.bank.webClient.view.accounts;

import java.net.URISyntaxException;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleConstraint;

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.service.AccountService;
import com.payulatam.samples.bank.webClient.service.ClientService;
import com.payulatam.samples.bank.webClient.utils.FormValidation;
import com.payulatam.samples.bank.webClient.utils.StringUtils;
import com.payulatam.samples.bank.webClient.view.utils.ViewUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CreateAccountView extends SelectorComposer<Component>{

	private static final long serialVersionUID = 3559459247765626819L;
	@WireVariable
	private AccountService accountService;
	
	@WireVariable
	private ClientService clientService;
	
	@Wire("include #clientsCombo")
	private Combobox clientsCombo;
	
	@Wire
	private Button createClientButton;
	
	@Wire
	private Grid createAccountForm;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		ViewUtils.populateClientsCombo(clientsCombo, clientService.getAllClients());
	}
	
	@Listen("onClick = button#createAccount")
	public void submitCreateAccount() throws URISyntaxException {
		if(FormValidation.isValid(createAccountForm)) {
			String clientId = (String) clientsCombo.getSelectedItem().getAttribute("clientId");
			Account result = accountService.createAccount(clientId);
			Messagebox.show(result.toString());	
		}
	}

}

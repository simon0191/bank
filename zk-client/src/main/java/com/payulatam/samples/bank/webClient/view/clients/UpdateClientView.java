package com.payulatam.samples.bank.webClient.view.clients;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.Textbox;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.service.ClientService;
import com.payulatam.samples.bank.webClient.utils.StringUtils;
import com.payulatam.samples.bank.webClient.view.utils.ViewUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UpdateClientView extends SelectorComposer<Component> {
	private static final long serialVersionUID = -5140044603040431242L;
	@WireVariable
	private ClientService clientService;

	@Wire
	private Grid createClientForm;

	@Wire("include #clientsCombo")
	private Combobox clientsCombo;

	@Wire
	private Textbox nameTxt;

	@Wire
	private Textbox addressTxt;

	@Wire
	private Textbox phoneNumberTxt;
	

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		String clientId = Executions.getCurrent().getParameter("clientId");
		List<Client> clients = clientService.getAllClients();
		if (clientId != null) {
			Client clientToUpdate = clientService.searchClientById(clientId);
			ViewUtils.populateClientsCombo(clientsCombo, clients, clientToUpdate);
			populateUpdateForm(clientToUpdate);
		} else {
			ViewUtils.populateClientsCombo(clientsCombo, clients);
		}
	}

	@Listen("onChange = include #clientsCombo")
	public void onChangeClientsCombo() {
		String clientId = (String) clientsCombo.getSelectedItem().getAttribute("clientId");
		Client clientToUpdate = clientService.searchClientById(clientId);
		populateUpdateForm(clientToUpdate);
	}

	@Listen("onClick = #submitUpdateClient")
	public void onClickUpdateClient() {
		Client result = clientService.updateClient((String) clientsCombo.getSelectedItem()
				.getAttribute("clientId"), nameTxt.getText(), addressTxt.getText(), phoneNumberTxt
				.getText());
		ViewUtils.populateClientsCombo(clientsCombo, clientService.getAllClients(), result);
		populateUpdateForm(result);
		Messagebox.show("Client updated");
	}

	private void populateUpdateForm(Client clientToUpdate) {
		nameTxt.setText(clientToUpdate.getName());
		addressTxt.setText(clientToUpdate.getAddress());
		phoneNumberTxt.setText(clientToUpdate.getTelephone());
	}
}

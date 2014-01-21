package com.payulatam.samples.bank.webClient.view.clients;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleConstraint;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.utils.StringUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UpdateClientView extends ClientFormView {
	private static final long serialVersionUID = -5140044603040431242L;
	@Wire
	private Grid createClientForm;

	@Wire
	private Combobox clientsCombo;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		configClientsCombo();
		String clientId = Executions.getCurrent().getParameter("clientId");
		if (clientId != null) {
			Client clientToUpdate = (Client) super.clientService.searchClientById(clientId);
			populateClientsCombo(clientToUpdate);
			populateUpdateForm(clientToUpdate);
		} else {
			populateClientsCombo();
		}
	}

	@Listen("onChange = #clientsCombo")
	public void onChangeClientsCombo() {
		String clientId = (String) clientsCombo.getSelectedItem().getAttribute("clientId");
		Client clientToUpdate = super.clientService.searchClientById(clientId);
		populateUpdateForm(clientToUpdate);
	}
	
	@Listen("onClick = #submitUpdateClient")
	public void onClickUpdateClient() {
		Client result = clientService.updateClient((String)clientsCombo.getSelectedItem().getAttribute("clientId"),nameTxt.getText(), addressTxt.getText(),
				phoneNumberTxt.getText());
		populateClientsCombo(result);
		populateUpdateForm(result);
		Messagebox.show("Client updated");
	}

	private void populateUpdateForm(Client clientToUpdate) {
		super.nameTxt.setText(clientToUpdate.getName());
		super.addressTxt.setText(clientToUpdate.getAddress());
		super.phoneNumberTxt.setText(clientToUpdate.getTelephone());
	}

	private void populateClientsCombo() {
		List<Client> clients = clientService.getAllClients();
		ListModelList<Client> clientListModel = new ListModelList<Client>(clients);
		clientListModel.addToSelection(clientListModel.get(0));
		clientsCombo.setModel(clientListModel);
	}

	private void populateClientsCombo(Client client) {
		List<Client> clients = clientService.getAllClients();
		ListModelList<Client> clientListModel = new ListModelList<Client>(clients);
		clientListModel.addToSelection(client);
		clientsCombo.setModel(clientListModel);
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

}

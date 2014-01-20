package com.payulatam.samples.bank.webClient.view.clients;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
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
		
		Client clientToUpdate = (Client) Sessions.getCurrent().getAttribute("clientToUpdate");
		if (clientToUpdate != null) {
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
	
	//TODO: redraw the combobox !?
	@Listen("onClick = #submitUpdateClient")
	public void onClickUpdateClient() {
		Client result = clientService.updateClient((String)clientsCombo.getSelectedItem().getAttribute("clientId"),nameTxt.getText(), addressTxt.getText(),
				phoneNumberTxt.getText());
		populateClientsCombo();
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
		ListModel<Client> clientListModel = new ListModelList<Client>(clients);
		clientsCombo.setModel(clientListModel);
	}

	//TODO:  clientsCombo.getItems == 0 despues de llenarlo !?
	private void populateClientsCombo(Client client) {
		System.out.println("--------- antes: "+clientsCombo.getItems().size());
		this.populateClientsCombo();
		System.out.println("--------- despues: "+clientsCombo.getItems().size());
		for (Comboitem item : clientsCombo.getItems()) {
			System.out.println("---------- "+item.getAttribute("clientId"));
			if (item.getAttribute("clientId").equals(client.getId())) {
				clientsCombo.setSelectedItem(item);
				break;
			}
		}
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

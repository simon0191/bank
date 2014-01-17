package com.payulatam.samples.bank.webClient.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.utils.FormValidation;

public class ClientController extends SelectorComposer<Component> {
	private static final long serialVersionUID = -5140044603040431242L;
	@Wire
	private Grid searchClientForm;
	@Wire
	private Grid createClientForm;
	@Wire
	private Grid resultsGrid;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		resultsGrid.setVisible(false);
	}

	@Listen("onClick = button#submitCreateClient")
	public void submitCreateClient() {
		boolean isValid = FormValidation.isValid(createClientForm);
		if (!isValid) {
			Messagebox.show("Datos invalidos");
		} 
		//TODO: Enviar datos
	}

	@Listen("onClick = button#submitSearchClient")
	public void submitSearchClient() {
		boolean isValid = FormValidation.isValid(searchClientForm);
		if (!isValid) {
			Messagebox.show("Datos invalidos");
		} else {
			Messagebox.show("Datos enviados");
			populateResultsGrid();
		}
	}

	@Listen("onClick = button.editButton")
	public void editClient() {
		Messagebox.show("Click");
	}

	private void populateResultsGrid() {
		Client client = new Client();
		client.setId("AAVBVASDF324");
		client.setAddress("cra 45 #34 ");
		client.setName("Simon Santiago");
		client.setTelephone("345345");

		List<Client> clients = new ArrayList<Client>();

		ListModelList<Client> clientsModel = new ListModelList<Client>(clients);
		clientsModel.add(client);
		clientsModel.add(client);

		resultsGrid.setModel(clientsModel);
		resultsGrid.setVisible(true);

		// results.set

		resultsGrid.setRowRenderer(new RowRenderer<Client>() {
			public void render(final Row row, final Client client, final int index)
					throws Exception {
				new Label(String.valueOf(index)).setParent(row);
				new Label(client.getId()).setParent(row);
				new Label(client.getName()).setParent(row);
				new Label(client.getAddress()).setParent(row);
				new Label(client.getTelephone()).setParent(row);
				final Button editButton = new Button("Edit");
				editButton.setClass("editButton");
				editButton.setAttribute("clientId", client.getId());
				editButton.setParent(row);
				editButton.addEventListener(Events.ON_CLICK, new EventListener<MouseEvent>() {
					@Override
					public void onEvent(MouseEvent ev) throws Exception {
						Messagebox.show(editButton.getAttribute("clientId").toString());
					}
				});
			}

		});

	}

}

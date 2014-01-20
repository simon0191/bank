package com.payulatam.samples.bank.webClient.view.clients;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.service.ClientService;

public class ClientFormView extends SelectorComposer<Component> {
	private static final long serialVersionUID = -2234514242292139908L;

	@WireVariable
	protected ClientService clientService;
	
	@Wire
	protected Grid resultsGrid;

	@Wire
	protected Textbox nameTxt;

	@Wire
	protected Textbox addressTxt;

	@Wire
	protected Textbox phoneNumberTxt;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	@Listen("onCreate = grid#resultsGrid")
	public void onCreateResultsGrid() {
		resultsGrid.setVisible(false);
		resultsGrid.setRowRenderer(new RowRenderer<Client>() {
			public void render(final Row row, final Client client, final int index)
					throws Exception {
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
	
	protected void populateResultsGrid(Client client) {
		List<Client> clientsList = new ArrayList<Client>();
		clientsList.add(client);
		populateResultsGrid(clientsList);
	}

	protected void populateResultsGrid(List<Client> clients) {
		ListModelList<Client> clientsModel = new ListModelList<Client>(clients);

		resultsGrid.setModel(clientsModel);
		resultsGrid.setVisible(true);
	}

}

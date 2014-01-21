package com.payulatam.samples.bank.webClient.view.clients;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.service.ClientService;
import com.payulatam.samples.bank.webClient.utils.StringUtils;

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
		configResultsGrid();
	}
	
	public void configResultsGrid() {
		resultsGrid.setVisible(false);
		resultsGrid.setRowRenderer(new RowRenderer<Client>() {
			public void render(final Row row, final Client client, final int index)
					throws Exception {
				new Label(String.valueOf(index)).setParent(row);
				new Label(client.getId()).setParent(row);
				new Label(client.getName()).setParent(row);
				new Label(client.getAddress()).setParent(row);
				new Label(client.getTelephone()).setParent(row);
				
				final Hbox options = new Hbox();
				options.setParent(row);
				
				final Button editButton = new Button("Edit");
				editButton.setAttribute("clientId", client.getId());
				editButton.setParent(options);
				editButton.addEventListener(Events.ON_CLICK, new EventListener<MouseEvent>() {
					@Override
					public void onEvent(MouseEvent ev) throws Exception {
//						Sessions.getCurrent().setAttribute("clientToUpdate", client);
						Executions.sendRedirect("/clients/update.zul?"+"clientId="+client.getId());
					}
				});
				
				final Button deleteButton = new Button("Delete");
				deleteButton.setAttribute("clientId", client.getId());
				deleteButton.setParent(options);
				deleteButton.addEventListener(Events.ON_CLICK, new EventListener<MouseEvent>() {
					@Override
					public void onEvent(MouseEvent ev) throws Exception {
						Client deleted = clientService.deleteClient(client.getId());
						
//						Messagebox.show(StringUtils.concatenate(deleted.toString()," deleted"));
						Messagebox.show(" deleted");
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

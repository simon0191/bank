package com.payulatam.samples.bank.webClient.view.clients;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.service.ClientService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ClientsGridView extends SelectorComposer<Component> {
	private static final long serialVersionUID = -2234514242292139908L;

	@WireVariable
	private ClientService clientService;

	@Wire
	private Grid clientsGrid;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		configResultsGrid();
	}

	private void configResultsGrid() {
		clientsGrid.setRowRenderer(new RowRenderer<Client>() {
			public void render(final Row row, final Client client, final int index)
					throws Exception {
				new Label(String.valueOf(index)).setParent(row);
				new Label(client.getId()).setParent(row);
				new Label(client.getName()).setParent(row);
				new Label(client.getAddress()).setParent(row);
				new Label(client.getTelephone()).setParent(row);

				final Hbox options = new Hbox();
				options.setParent(row);

				final Button editButton = createEditButton(client);
				editButton.setParent(options);
				
				final Button deleteButton = createDeleteButton(client,row);
				deleteButton.setParent(options);

			}
		});
	}
	
	private Button createDeleteButton(final Client client,final Row row) {
		Button result = new Button("Delete");
		result.addEventListener(Events.ON_CLICK, new EventListener<MouseEvent>() {
			@Override
			public void onEvent(MouseEvent ev) throws Exception {
				Client deleted = clientService.deleteClient(client.getId());
				clientsGrid.removeChild(row);
				row.detach();
				Messagebox.show(deleted + " deleted");
			}
		});
		return result;
	}

	private Button createEditButton(final Client client) {
		Button result = new Button("Edit");		
		result.addEventListener(Events.ON_CLICK, new EventListener<MouseEvent>() {
			@Override
			public void onEvent(MouseEvent ev) throws Exception {
				Executions.sendRedirect("/clients/update.zul?" + "clientId="
						+ client.getId());
			}
		});
		return result;
	}
}

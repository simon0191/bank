package com.payulatam.samples.bank.webClient.view.clients;

import java.net.URISyntaxException;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.service.ClientService;
import com.payulatam.samples.bank.webClient.utils.FormValidation;
import com.payulatam.samples.bank.webClient.view.utils.ViewUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SearchClientView extends SelectorComposer<Component> {
	private static final long serialVersionUID = -5140044603040431242L;
	@WireVariable
	private ClientService clientService;

	@Wire
	private Grid searchClientForm;
	@Wire
	private Textbox clientId;

	@Wire
	private Textbox nameTxt;

	@Wire
	private Textbox addressTxt;

	@Wire
	private Textbox phoneNumberTxt;
	
	@Wire("include #clientsGrid")
	private Grid clientsGrid;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	@Listen("onClick = button#submitSearchClient")
	public void submitSearchClient() throws URISyntaxException {
		boolean isValid = FormValidation.isValid(searchClientForm);
		if (!isValid) {
			Messagebox.show("Datos invalidos");
		} else {
			List<Client> clients = clientService.searchClient(clientId.getText(),
					nameTxt.getText(), addressTxt.getText(),
					phoneNumberTxt.getText());
			ViewUtils.populateClientsGrid(clientsGrid,clients);
		}
	}

}

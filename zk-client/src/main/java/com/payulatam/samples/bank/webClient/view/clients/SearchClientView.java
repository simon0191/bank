package com.payulatam.samples.bank.webClient.view.clients;

import java.net.URISyntaxException;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.utils.FormValidation;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SearchClientView extends ClientFormView {
	private static final long serialVersionUID = -5140044603040431242L;
	
	@Wire
	private Grid searchClientForm;

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
			List<Client> clients = clientService.searchClient(super.nameTxt.getText(), super.addressTxt.getText(),
					super.phoneNumberTxt.getText());
			super.populateResultsGrid(clients);
		}
	}

}

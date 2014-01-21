package com.payulatam.samples.bank.webClient.view.clients;

import java.net.URISyntaxException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
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
public class CreateClientView extends SelectorComposer<Component> {
	private static final long serialVersionUID = -5140044603040431242L;

	@WireVariable
	private ClientService clientService;

	@Wire
	private Grid createClientForm;

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

	@Listen("onClick = button#submitCreateClient")
	public void submitCreateClient() throws WrongValueException, URISyntaxException {
		boolean isValid = FormValidation.isValid(createClientForm);
		if (!isValid) {
			Messagebox.show("Datos invalidos");
		} else {
			Client result = clientService.createClient(nameTxt.getText(), addressTxt.getText(),
					phoneNumberTxt.getText());
			ViewUtils.populateClientsGrid(clientsGrid, result);
		}
	}
}

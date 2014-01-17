package com.payulatam.samples.bank.webClient.view.clients;

import java.net.URISyntaxException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.service.ClientService;
import com.payulatam.samples.bank.webClient.utils.FormValidation;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CreateClientView extends ClientFormView {
	private static final long serialVersionUID = -5140044603040431242L;
	@Wire
	private Grid createClientForm;
	
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
			populateResultsGrid(result);
		}
	}
}

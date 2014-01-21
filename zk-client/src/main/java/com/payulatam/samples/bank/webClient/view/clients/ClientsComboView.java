package com.payulatam.samples.bank.webClient.view.clients;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.webClient.utils.StringUtils;

public class ClientsComboView extends SelectorComposer<Component> {

	private static final long serialVersionUID = -6345751113343465686L;
	@Wire
	private Combobox clientsCombo;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.configClientsCombo();
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
	}
}

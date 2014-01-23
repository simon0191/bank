package com.payulatam.samples.bank.webClient.view.accounts;

import java.text.NumberFormat;
import java.util.Locale;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.webClient.utils.StringUtils;

public class AccountsComboView extends SelectorComposer<Component> {

	private static final long serialVersionUID = -4896611785218278443L;
	
	@Wire
	private Combobox accountsCombo;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.configAccountsCombo();
	}
	private void configAccountsCombo() {
		accountsCombo.setReadonly(true);
		accountsCombo.setItemRenderer(new ComboitemRenderer<Account>() {
			@Override
			public void render(Comboitem item, Account account, int index) throws Exception {
				item.setLabel(account.getId());
				item.setDescription(StringUtils.formatMoney(account.getBalance()));
				item.setAttribute("accountId", account.getId());
			}
		});
	}
}

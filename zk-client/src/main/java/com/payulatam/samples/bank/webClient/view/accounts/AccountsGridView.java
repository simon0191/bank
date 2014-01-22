package com.payulatam.samples.bank.webClient.view.accounts;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import org.zkoss.zk.ui.Component;
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

import com.payulatam.samples.bank.common.Account;
import com.payulatam.samples.bank.webClient.service.AccountService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AccountsGridView extends SelectorComposer<Component> {

	private static final long serialVersionUID = -2678351478145419840L;

	@WireVariable
	private AccountService accountService;
	
	@Wire
	private Grid accountsGrid;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		configResultsGrid();
	}

	private void configResultsGrid() {
		accountsGrid.setRowRenderer(new RowRenderer<Account>() {
			public void render(final Row row, final Account account, final int index)
					throws Exception {

				new Label(String.valueOf(index)).setParent(row);
				new Label(account.getId()).setParent(row);
				new Label(account.getClientId()).setParent(row);

				NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(Locale.US);
				usdCostFormat.setCurrency(Currency.getInstance(Locale.US));
				usdCostFormat.setMinimumFractionDigits(1);
				usdCostFormat.setMaximumFractionDigits(2);
				new Label(usdCostFormat.format(account.getBalance().doubleValue())).setParent(row);

				final Hbox options = new Hbox();
				options.setParent(row);

				final Button deleteButton = createDeleteButton(account, row);
				deleteButton.setParent(options);

			}
		});
	}

	private Button createDeleteButton(final Account account, final Row row) {
		Button result = new Button("Delete");
		result.addEventListener(Events.ON_CLICK, new EventListener<MouseEvent>() {
			@Override
			public void onEvent(MouseEvent ev) throws Exception {
				Account deleted = accountService.deleteAccount(account.getId());
				accountsGrid.removeChild(row);
				row.detach();
				Messagebox.show(deleted + " deleted");
			}
		});
		return result;
	}

}

package com.payulatam.samples.bank.webClient.view.reports;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.HttpClientErrorException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.payulatam.samples.bank.common.reports.TransactionReportItem;
import com.payulatam.samples.bank.webClient.service.ClientService;
import com.payulatam.samples.bank.webClient.service.ReportService;
import com.payulatam.samples.bank.webClient.utils.FormValidation;
import com.payulatam.samples.bank.webClient.utils.StringUtils;
import com.payulatam.samples.bank.webClient.view.utils.ViewUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TransactionsReportView extends SelectorComposer<Component> {

	private static final long serialVersionUID = 178639770897441721L;
	@WireVariable
	private ClientService clientService;

	@Wire("include #clientsCombo")
	private Combobox clientsCombo;

	@Wire
	private Grid resultsGrid;
	@Wire
	private Grid reportForm;

	@Wire
	private Datebox startDate;

	@Wire
	private Datebox endDate;

	@WireVariable
	private ReportService reportService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		configResultsGrid();
		ViewUtils.populateClientsCombo(clientsCombo, clientService.getAllClients());
	}

	@Listen("onClick = button#createReport")
	public void submitCreateReport() {
		boolean isValid = FormValidation.isValid(reportForm);
		if (!isValid) {
			Messagebox.show("Datos invalidos");
		} else {
			String clientId = (String) clientsCombo.getSelectedItem().getAttribute("clientId");
			try {
				List<TransactionReportItem> result = reportService.createReport(clientId,
						startDate.getValue(), endDate.getValue());
				populateResultsGrid(result);
			} catch (HttpClientErrorException e) {
				Messagebox.show("Please verify the information", "Bad Request", Messagebox.OK,
						Messagebox.ERROR);
			}

		}
	}

	private void populateResultsGrid(List<TransactionReportItem> items) {
		List<TransactionReportItem> itemsList = new ArrayList<TransactionReportItem>(items);
		ListModelList<TransactionReportItem> reportModel = new ListModelList<TransactionReportItem>(
				itemsList);

		resultsGrid.setModel(reportModel);
		resultsGrid.setVisible(true);
	}

	private void configResultsGrid() {
		resultsGrid.setVisible(false);
		resultsGrid.setRowRenderer(new RowRenderer<TransactionReportItem>() {
			public void render(final Row row, final TransactionReportItem item, final int index)
					throws Exception {
				new Label(item.getAccountId()).setParent(row);
				new Label(StringUtils.formatMoney(item.getBalance())).setParent(row);
				new Label(StringUtils.formatMoney(item.getDebits())).setParent(row);
				new Label(StringUtils.formatMoney(item.getCredits())).setParent(row);
			}

		});
	}

}

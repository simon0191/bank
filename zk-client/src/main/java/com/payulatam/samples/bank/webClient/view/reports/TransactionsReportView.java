package com.payulatam.samples.bank.webClient.view.reports;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.SimpleConstraint;

import com.payulatam.samples.bank.common.Client;
import com.payulatam.samples.bank.common.reports.TransactionReportItem;
import com.payulatam.samples.bank.webClient.service.ClientService;
import com.payulatam.samples.bank.webClient.service.ReportService;
import com.payulatam.samples.bank.webClient.utils.FormValidation;
import com.payulatam.samples.bank.webClient.utils.StringUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TransactionsReportView extends SelectorComposer<Component> {

	private static final long serialVersionUID = 178639770897441721L;
	@WireVariable
	private ClientService clientService;

	@Wire
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
		configClientsCombo();
		configResultsGrid();

		populateClientsCombo();
	}

	@Listen("onClick = button#createReport")
	public void submitCreateReport() {
		boolean isValid = FormValidation.isValid(reportForm);
		if (!isValid) {
			Messagebox.show("Datos invalidos");
		} else {
			String clientId = (String) clientsCombo.getSelectedItem().getAttribute("clientId");
			List<TransactionReportItem> result = reportService.createReport(clientId,startDate.getValue(),endDate.getValue());
			System.out.println("----------* "+result);
			populateResultsGrid(result);
		}
	}
	private void populateResultsGrid(List<TransactionReportItem> items) {
		List<TransactionReportItem> itemsList = new ArrayList<TransactionReportItem>(items);
		ListModelList<TransactionReportItem> reportModel = new ListModelList<TransactionReportItem>(itemsList);

		resultsGrid.setModel(reportModel);
		resultsGrid.setVisible(true);
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
		clientsCombo.setConstraint(new SimpleConstraint(SimpleConstraint.NO_EMPTY));
	}

	private void populateClientsCombo() {
		List<Client> clients = clientService.getAllClients();
		ListModel<Client> clientListModel = new ListModelList<Client>(clients);
		clientsCombo.setModel(clientListModel);
	}

	private void configResultsGrid() {
		resultsGrid.setVisible(false);
		resultsGrid.setRowRenderer(new RowRenderer<TransactionReportItem>() {
			public void render(final Row row, final TransactionReportItem item, final int index)
					throws Exception {
				new Label(item.getAccountId()).setParent(row);
				new Label(item.getBalance().toString()).setParent(row);
				new Label(item.getDebits().toString()).setParent(row);
				new Label(item.getCredits().toString()).setParent(row);
			}

		});
	}

}

package com.payulatam.samples.bank.webClient.view;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

public class MenuView extends SelectorComposer<Component>{
	
	private static final long serialVersionUID = 2955106721594052755L;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    }
    
    @Listen("onClick = button#searchAccount")
    public void loadSearchAccount() {
    	Executions.sendRedirect("/accounts/search.zul");
    }
    @Listen("onClick = button#createAccount")
    public void loadCreateAccount() {
    	Executions.sendRedirect("/accounts/create.zul");
    }
    
    @Listen("onClick = button#searchClient")
    public void loadSearchClient() {
    	Executions.sendRedirect("/clients/search.zul");
    }
    @Listen("onClick = button#createClient")
    public void loadCreateClient() {
    	Executions.sendRedirect("/clients/create.zul");
    }
    
    @Listen("onClick = button#searchTransaction")
    public void loadSearchTransaction() {
    	Executions.sendRedirect("/transactions/search.zul");
    }
    @Listen("onClick = button#createTransaction")
    public void loadCreateTransaction() {
    	Executions.sendRedirect("/transactions/create.zul");
    }
    
    @Listen("onClick = button#createTransactionsReport")
    public void loadTransactionsReport() {
    	Executions.sendRedirect("/reports/transactionsByClientAndDate.zul");
    }
    
}

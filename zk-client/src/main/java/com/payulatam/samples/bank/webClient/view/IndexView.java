package com.payulatam.samples.bank.webClient.view;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

public class IndexView extends SelectorComposer<Component>{
	
	private static final long serialVersionUID = 2955106721594052755L;
	
	@Wire
    private Window indexWindow;
	
	@Wire
	private Include content;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	indexWindow.setTitle("Bank - Index");
    }
    
    @Listen("onClick = button#searchAccount")
    public void loadSearchAccount() {
    	content.setSrc("accounts/search.zul");
    }
    @Listen("onClick = button#createAccount")
    public void loadCreateAccount() {
    	content.setSrc("accounts/create.zul");
    }
    
    @Listen("onClick = button#searchClient")
    public void loadSearchClient() {
    	content.setSrc("clients/search.zul");
    }
    @Listen("onClick = button#createClient")
    public void loadCreateClient() {
    	content.setSrc("clients/create.zul");
    }
    
    @Listen("onClick = button#searchTransaction")
    public void loadSearchTransaction() {
    	content.setSrc("transactions/search.zul");
    }
    @Listen("onClick = button#createTransaction")
    public void loadCreateTransaction() {
    	content.setSrc("transactions/create.zul");
    }
    
    
    
}

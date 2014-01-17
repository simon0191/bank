package com.payulatam.samples.bank.webClient.view.accounts;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;


public class AccountController extends SelectorComposer<Component> {
	@Wire
    Window accountsWindow;
	
    @Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    	accountsWindow.setTitle("Accounts :D");
    }

}

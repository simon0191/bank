package com.payulatam.samples.bank.webClient.utils;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.impl.InputElement;

public final class FormValidation {
	private FormValidation() {
	}

	public final static boolean isValid(Component component) {
		boolean result = true;
		if (component instanceof InputElement) {
			if (!((InputElement) component).isValid()) {
				// Force show errorMessage
				((InputElement) component).getText();
				result = false;
			}
		}
		List<Component> children = component.getChildren();
		for (Component each : children) {
			result = result && isValid(each);
		}
		return result;
	}

}

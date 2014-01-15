package com.payulatam.samples.bank.service;

import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;

@Service
public class Utils {
	
	private final static String STRING_PATTERN = "[\\w\\s]+";
	private final static String NUMBER_PATTERN = "[\\d]+";

	public boolean validateTelephone(String telephone) {
		boolean result = telephone.matches(NUMBER_PATTERN);
		return result;
	}

	public boolean validateAddress(String address) {
		// TODO
		return true;
	}

	public boolean validateName(String name) {
		boolean result = name.matches(STRING_PATTERN);
		return result;
	}

}

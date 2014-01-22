package com.payulatam.samples.bank.utils;


public final class Utils {
	
	private final static String STRING_PATTERN = "[\\w\\s]+";
	private final static String NUMBER_PATTERN = "[\\d]+";

	private Utils() {
		
	}
	public static boolean validateTelephone(String telephone) {
		boolean result = telephone.matches(NUMBER_PATTERN);
		return result;
	}

	public static boolean validateAddress(String address) {
		// TODO valdiate address
		return true;
	}

	public static boolean validateName(String name) {
		boolean result = name.matches(STRING_PATTERN);
		return result;
	}

}

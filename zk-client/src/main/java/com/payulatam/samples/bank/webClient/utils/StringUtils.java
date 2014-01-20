package com.payulatam.samples.bank.webClient.utils;

public final class StringUtils {
	private StringUtils() {

	}
	public static String concatenate(String... args) {
		StringBuilder sb = new StringBuilder();
		for(String s: args) {
			sb.append(s);
		}
		return sb.toString();
		
	}
}

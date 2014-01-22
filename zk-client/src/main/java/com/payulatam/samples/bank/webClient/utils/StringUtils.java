package com.payulatam.samples.bank.webClient.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

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
	public static String formatMoney(BigDecimal value) {
		NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(Locale.US);
		usdCostFormat.setCurrency(Currency.getInstance(Locale.US));
		usdCostFormat.setMinimumFractionDigits(1);
		usdCostFormat.setMaximumFractionDigits(2);
		String result = usdCostFormat.format(value);
		return result;
	}
}

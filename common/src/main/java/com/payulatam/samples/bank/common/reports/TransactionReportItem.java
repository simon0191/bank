package com.payulatam.samples.bank.common.reports;

import java.math.BigDecimal;

public class TransactionReportItem {

	private String accountId;
	private BigDecimal balance;
	private BigDecimal debits;
	private BigDecimal credits;

	public TransactionReportItem() {
		
	}

	public TransactionReportItem(String accountId) {
		this.accountId = accountId;
		balance = BigDecimal.ZERO;
		debits = BigDecimal.ZERO;
		credits = BigDecimal.ZERO;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getDebits() {
		return debits;
	}

	public void setDebits(BigDecimal debits) {
		this.debits = debits;
	}

	public BigDecimal getCredits() {
		return credits;
	}

	public void setCredits(BigDecimal credits) {
		this.credits = credits;
	}

	@Override
	public String toString() {
		return "TransactionReportItem [accountId=" + accountId + ", balance=" + balance
				+ ", debits=" + debits + ", credits=" + credits + "]";
	}
	

}

package com.payulatam.samples.bank.common.reports;

import java.util.Date;

public class TransactionReportRequest {
	private String clientId;
	private Date startDate;
	private Date endDate;

	public TransactionReportRequest() {

	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}

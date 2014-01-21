package com.payulatam.samples.bank.common;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.gigaspaces.metadata.index.SpaceIndexType;

@Entity
@Table(name = "BANK_TRANSACTION")
@SpaceClass
public class Transaction {

	private String accountId;
	@Id
	private String id;
	private TransactionType type;
	private BigDecimal value;
	private Date transactionDate;

	public Transaction() {
	}

	@SpaceRouting
	@SpaceId(autoGenerate=true)
	public String getId() {
		return this.id;
	}
	
	@Enumerated
	public TransactionType getType() {
		return type;
	}

	public BigDecimal getValue() {
		return value;
	}

	@SpaceIndex(type=SpaceIndexType.EXTENDED)
	public Date getTransactionDate() {
		return transactionDate;
	}

	@Override
	public String toString() {
		return "Transaction [type=" + type + ", value=" + value + ", date="
				+ transactionDate + ", accountId="+accountId+"]";
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setId(String id) {
		this.id = id;
	}

}

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

	public static Builder builder() {
		return new Builder();
	}
	
	public boolean equals(Object other) {
		if(other == this){
			return true;
		}
		if(other instanceof Transaction) {
			Transaction that = (Transaction)other;
			return ((this.accountId==that.accountId || this.accountId.equals(that.accountId)) &&
					(this.id == that.accountId || this.accountId.equals(that.accountId)) &&
					(this.transactionDate == that.transactionDate || this.transactionDate.equals(that.transactionDate)) &&
					(this.type == that.type || this.type.equals(that.type)) &&
					(this.value == that.value || this.value.equals(that.value))
					);
		}
		else {
			return false;
		}
	}
	
	private Transaction(Builder builder) {
		this.accountId = builder.accountId;
		this.id	 = builder.id;
		this.transactionDate = builder.transactionDate;
		this.type = builder.type;
		this.value = builder.value;
	}
	
	public static class Builder {
		private String accountId;
		private String id;
		private TransactionType type;
		private BigDecimal value;
		private Date transactionDate;
		
		private Builder() {
			
		}
		
		public Builder accountId(String accountId) {
			this.accountId = accountId;
			return this;
		}
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		public Builder type(TransactionType type) {
			this.type = type;
			return this;
		}
		public Builder value(BigDecimal value) {
			this.value = value;
			return this;
		}
		public Builder transactionDate(Date transactionDate) {
			this.transactionDate = transactionDate;
			return this; 
		}
		public Transaction build() {
			return new Transaction(this);
		}
		
	}

}

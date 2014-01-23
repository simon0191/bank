package com.payulatam.samples.bank.common;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

@Entity
@Table(name = "ACCOUNT")
@SpaceClass
public class Account {

	private String clientId;
	@Id
	private String id;
	private BigDecimal balance;

	public Account() {
	}

	@SpaceRouting
	@SpaceId(autoGenerate = true)
	public String getId() {
		return id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + "]";
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Account) {
			Account that = (Account) other;
			return ((this.id == that.id || this.id.equals(that.id))
					&& (this.clientId == that.clientId || this.clientId.equals(that.clientId)) && (this.balance == that.balance || this.balance
					.equals(that.id)));
		} else {
			return false;
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	private Account(Builder builder) {
		this.id = builder.id;
		this.balance = builder.balance;
		this.clientId = builder.clientId;
	}

	public static class Builder {
		private String id;
		private String clientId;
		private BigDecimal balance;
		private Builder() {
			
		}
		public Builder clientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder balance(BigDecimal balance) {
			this.balance = balance;
			return this;
		}

		public Account build() {
			return new Account(this);
		}

	}
}

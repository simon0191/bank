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
}

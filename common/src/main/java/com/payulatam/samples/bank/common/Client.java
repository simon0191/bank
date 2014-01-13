package com.payulatam.samples.bank.common;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

@Entity
@Table(name = "CLIENT")
@SpaceClass
public class Client {

	@Id
	private String id;
	private String name;
	private String address;
	private String telephone;

	public Client() {
	}

	@SpaceRouting
	@SpaceId(autoGenerate = true)
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getTelephone() {
		return telephone;
	}
	
	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + "]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}

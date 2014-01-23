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

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", address=" + address + ", telephone="
				+ telephone + "]";
	}
	
	@Override
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}
		if(other instanceof Client) {
			Client that = (Client) other;
			return ((this.name == that.name || this.name.equals(that.name) )&& 
					(this.telephone == that.telephone || this.telephone.equals(that.telephone) )&& 
					(this.address == that.address || this.address.equals(that.address)));
		}
		else {
			return false;
		}
	}
	private Client(Client.Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.address = builder.address;
		this.telephone = builder.telephone;
	}
	public static Builder builder() {
		return new Builder();
	}
	public static class Builder {
		private String id;
		private String name;
		private String address;
		private String telephone;
		private Builder() {
			
		}
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder address(String address) {
			this.address = address;
			return this;
		}
		public Builder telephone(String telephone) {
			this.telephone = telephone;
			return this;
		}
		public Client build() {
			return new Client(this);
		}
	}

}

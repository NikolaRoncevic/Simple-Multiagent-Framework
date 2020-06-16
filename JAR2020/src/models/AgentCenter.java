package models;

import java.io.Serializable;

public class AgentCenter implements Serializable {
	private static final long serialVersionUID = 1L;
	private String alias;
	private String address;

	public String getAlias() {
		return alias;
	}

	public AgentCenter() {

	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AgentCenter(String alias, String address) {
		super();
		this.alias = alias;
		this.address = address;
	}
	

}

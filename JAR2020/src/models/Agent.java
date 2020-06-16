package models;

import java.io.Serializable;

import javax.ejb.Stateful;

@Stateful
public class Agent implements Serializable {

	private static final long serialVersionUID = 1L;
	private AID id;
	
	public void handleMessage(ACLMessage message) {
		System.out.println("test poruka");
	}
	public AID getId() {
		return id;
	}
	public void setId(AID id) {
		this.id = id;
	}
	public Agent(AID id) {
		super();
		this.id = id;
	}
	public Agent() {
		
	}
	
	
	

}

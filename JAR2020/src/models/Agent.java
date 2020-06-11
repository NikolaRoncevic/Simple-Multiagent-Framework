package models;

import javax.ejb.Stateful;

@Stateful
public abstract class Agent {
	
	private AID id;
	public abstract void handleMessage(ACLMessage message);
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

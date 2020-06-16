package agents;

import java.net.InetAddress;
import java.net.UnknownHostException;

import data.Data;
import enums.Performative;
import jms.JMSQueue;
import models.ACLMessage;
import models.AID;
import models.Agent;
import models.AgentCenter;
import models.AgentType;

public class PongAgent extends Agent{

	private static final long serialVersionUID = 1L;

	@Override
	public void handleMessage(ACLMessage message) {
		if(message.getPerformative() == Performative.REQUEST) {
			Agent recieverAgent = null;
			for(Agent a : Data.getAgents()) {
				if(a.getId().getName().equals("ping")) {
					recieverAgent = a;
				}
			}
			
			AID reciever = recieverAgent.getId();
			System.out.println("Pripremam se da posaljem poruku pingu!");
			ACLMessage msg = new ACLMessage();
			msg.setPerformative(Performative.INFORM);
			AID[] receivers = {reciever};
			msg.setRecivers(receivers);
			msg.setSender(this.getId());
			msg.setContent(message.getContent());
			new JMSQueue(msg);
			
			
		}
	}

	private AgentCenter findHost() {
		String currentIp = null;
		try {
			currentIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
		for(AgentCenter center : Data.getAgentCenters()) {
			if(center.getAddress().equals(currentIp)) {
				return center;
			}
		}
		return null;
	}

}

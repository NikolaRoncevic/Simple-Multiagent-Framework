package jms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.websocket.Session;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import agents.PingAgent;
import data.Data;
import models.ACLMessage;
import models.AID;
import models.Agent;
import models.AgentCenter;
import util.getLocalHost;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/mojQueue") })
public class MDBSConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
		ObjectMessage msg = (ObjectMessage) message;
		System.out.println("poruka je primljena");
		try {
			ACLMessage acl = (ACLMessage) msg.getObject();
			AgentCenter host = findHost();
			List<Agent> agents = new ArrayList<>();
			for (AID aid : acl.getRecivers()) {
				Agent agent = Data.getAgents().get(aid.getName());
				if (agent != null) {
					agents.add(agent);
				}
			}

			for (int i = 0; i < agents.size(); i++) {
				ACLMessage newMsg = new ACLMessage(acl);
				newMsg.setRecivers(new AID[] { agents.get(i).getId() });
				System.out.println("Trenutni agent kome ce se pozvati handleMessage: " + agents.get(i).getId().getName());
				agents.get(i).handleMessage(newMsg);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private AgentCenter findHost() {
		String currentIp = null;
		currentIp = getLocalHost.getIpAddress();
		for (AgentCenter center : Data.getAgentCenters()) {
			if (center.getAddress().equals(currentIp)) {
				return center;
			}
		}
		return null;
	}

}

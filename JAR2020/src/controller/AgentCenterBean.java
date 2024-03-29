package controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import agents.CollectorAgent;
import agents.MasterAgent;
import agents.PingAgent;
import agents.PongAgent;
import agents.PredictorAgent;
import data.Data;
import enums.Performative;
import jms.JMSQueue;
import models.ACLMessage;
import models.AID;
import models.Agent;
import models.AgentCenter;
import models.AgentType;
import util.getLocalHost;
import ws.WSEndPoint;

@Path("/agents")
@LocalBean
@Stateless
public class AgentCenterBean {
	
	@GET
	@Path("/getWinningTeam/{timA}/{timB}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@PathParam("timA") String timA,@PathParam("timB") String timB) {
		ACLMessage msg = new ACLMessage();
		Agent a = null;
		for(Agent aa : Data.getAgents()) {
			if(aa.getId().getType().getName().equals("collector")) {
				a = aa;
				break;
			}
		}
		AID[] niz = new AID[1];
		niz[0] = a.getId();
		System.out.println(timA + " " + timB);
		msg.setContent(timA + " " + timB);
		msg.setRecivers(niz);
		msg.setPerformative(Performative.REQUEST);
		new JMSQueue(msg);
		return Response.status(200).entity(msg).build();
		
		
	}

	@GET
	@Path("/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTypes() {
		ArrayList<AgentType> response = Data.getAgentTypes();
		return Response.status(200).entity(response).build();
	}

	@GET
	@Path("/running")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRunningAgenst() {
		ArrayList<Agent> response = Data.getAgents();
		return Response.status(200).entity(response).build();
	}

	@PUT
	@Path("/running/{type}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response startAgentBasedOnTypeAndName(@PathParam("type") String type, @PathParam("name") String name) {
		AgentType at = null;
		for(AgentType att : Data.getAgentTypes()) {
			if(att.getName().contentEquals(type)) {
				at = att; 
			}
		}
		Agent agent = null;
		if(type.equals("ping")) {
			agent = new PingAgent();
		}else if(type.equals("pong")) {
			agent = new PongAgent();
		}else if(type.equals("collector")) {
			agent = new CollectorAgent();
		}else if(type.equals("predictor")) {
			agent = new PredictorAgent();
		}else if(type.equals("master")) {
			agent = new MasterAgent();
		}
		AID agentId = new AID();
		
		String currentIp = null;
		currentIp = getLocalHost.getIpAddress();
		for (AgentCenter h : Data.getAgentCenters()) {
			if (h.getAddress().equals(currentIp)) {
				agentId.setHost(h);
			}
		}
		agentId.setName(name);
		agentId.setType(at);
		agent.setId(agentId);
		Data.getAgents().add(agent);
		ResteasyClient client = null;
		ResteasyWebTarget target = null;
		Response response = null;
		client = new ResteasyClientBuilder().build();
		for(AgentCenter center : Data.getAgentCenters()) {
			if(!center.getAddress().equals(currentIp)) {
				System.out.println("http://" + center.getAddress() + ":8080/WAR2020/rest/agents/running");
				target = client.target("http://" + center.getAddress() + ":8080/WAR2020/rest/agentsrunning");
				response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(agent, MediaType.APPLICATION_JSON));
			}
			
		}
		try {
			Context context = new InitialContext();
			WSEndPoint ws = (WSEndPoint) context.lookup(WSEndPoint.LOOKUP);
			ws.echoTextMessage(agent.getId().getName());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(agent).build();
	}

	@DELETE
	@Path("/running/{aid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAgent(@PathParam("aid") String aid) {
		for (Agent a : Data.getAgents()) {

			if (a.getId().getName().equals(aid)) {
				Data.getAgents().remove(a);
				return Response.status(200).entity(a).build();
			}
		}
		return Response.status(400).entity("Nema agenta sa tim nazivom").build();
	}

}

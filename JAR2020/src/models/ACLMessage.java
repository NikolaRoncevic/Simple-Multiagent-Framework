package models;

import java.io.Serializable;
import java.util.HashMap;

import enums.Performative;

public class ACLMessage implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Performative performative;
	private AID sender;
	private AID[] recivers;
	private AID replyTo;
	private String content;
	private Object contentObj;
	private HashMap<String,Object> userArgs;
	private String language;
	private String encoding;
	private String ontology;
	private String protocol;
	private String conversationId;
	private String replyWith;
	private String inReplyTo;
	private String replyBy;
	
	public ACLMessage() {
		
	}
	
	public ACLMessage(ACLMessage acl) {
		this.performative  = acl.getPerformative();
		this.sender = acl.getSender();
		this.recivers = acl.getRecivers();
		this.replyTo = acl.getReplyTo();
		this.content = acl.getContent();
		this.contentObj = acl.getContentObj();
		this.userArgs = acl.getUserArgs();
		this.language = acl.getLanguage();
		this.encoding = acl.getEncoding();
		this.ontology = acl.getOntology();
		this.protocol = acl.getProtocol();
		this.conversationId = acl.getConversationId();
		this.replyWith = acl.getReplyWith();
		this.inReplyTo = acl.getInReplyTo();
		this.replyBy = acl.getReplyBy();
		
	}
	
	public ACLMessage(Performative performative, AID sender, AID[] recivers, AID replyTo, String content,
			Object contentObj, HashMap<String, Object> userArgs, String language, String encoding, String ontology,
			String protocol, String conversationId, String replyWith, String inReplyTo, String replyBy) {
		super();
		this.performative = performative;
		this.sender = sender;
		this.recivers = recivers;
		this.replyTo = replyTo;
		this.content = content;
		this.contentObj = contentObj;
		this.userArgs = userArgs;
		this.language = language;
		this.encoding = encoding;
		this.ontology = ontology;
		this.protocol = protocol;
		this.conversationId = conversationId;
		this.replyWith = replyWith;
		this.inReplyTo = inReplyTo;
		this.replyBy = replyBy;
	}
	public Performative getPerformative() {
		return performative;
	}
	public void setPerformative(Performative performative) {
		this.performative = performative;
	}
	public AID getSender() {
		return sender;
	}
	public void setSender(AID sender) {
		this.sender = sender;
	}
	public AID[] getRecivers() {
		return recivers;
	}
	public void setRecivers(AID[] recivers) {
		this.recivers = recivers;
	}
	public AID getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(AID replyTo) {
		this.replyTo = replyTo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Object getContentObj() {
		return contentObj;
	}
	public void setContentObj(Object contentObj) {
		this.contentObj = contentObj;
	}
	public HashMap<String, Object> getUserArgs() {
		return userArgs;
	}
	public void setUserArgs(HashMap<String, Object> userArgs) {
		this.userArgs = userArgs;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getOntology() {
		return ontology;
	}
	public void setOntology(String ontology) {
		this.ontology = ontology;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getConversationId() {
		return conversationId;
	}
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
	public String getReplyWith() {
		return replyWith;
	}
	public void setReplyWith(String replyWith) {
		this.replyWith = replyWith;
	}
	public String getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public String getReplyBy() {
		return replyBy;
	}
	public void setReplyBy(String replyBy) {
		this.replyBy = replyBy;
	}
	
	

}

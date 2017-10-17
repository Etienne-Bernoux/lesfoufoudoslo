package application;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import model.Cash;
import model.FormatCommand;
import model.UnknowAction;
import spread.AdvancedMessageListener;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;
import tools.IOFileParsing;

public class Replica implements AdvancedMessageListener  {
	
	private String groupName = null;
	private String serverName = null;
	private String connName = null;
	private Integer port = null;
	private static final int RANGE_NAME = 100;
	private Cash cash = null;
	
	private SpreadConnection connection = null;
	private Set<String> listReplica = null;
	
	
	
	
	/**
	 * 
	 * @param groupName name of the group connection 
	 * @param serverName name of the server ex: rubin.ifi.uio.no:8443 or rubin.ifi.uio.no 
	 * 			default port is 4803.
	 * @throws SpreadException 
	 * @throws UnknownHostException 
	 */
	public Replica(String groupName, String serverName) throws UnknownHostException, SpreadException {
		super();
		
		this.groupName = groupName;
		String[] nameSplit = serverName.split(":");
		this.serverName = nameSplit[0];
		this.port = nameSplit.length == 2 ? new Integer(nameSplit[1]) : new Integer(4803);
		this.generateConnectionName();
		this.cash = new Cash();
		this.listReplica = new HashSet<String>();
		this.init();
	}
	
	private void generateConnectionName()
	{
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(RANGE_NAME);
		this.connName = "CliNum-" + randomInt;
	}

	/**
	 * Initialize the connexion on the Spread server and joint the repective group.
	 * @throws SpreadException 
	 * @throws UnknownHostException 
	 */
	public void init() throws UnknownHostException, SpreadException{
		
		this.connection = new SpreadConnection();
		this.connection.connect(InetAddress.getByName(this.serverName), this.port.intValue(), this.getConnName(), false, true);
		SpreadGroup group = new SpreadGroup();
		group.join(this.connection, this.groupName);
		// This itself as listener in order to received message while sending messages
		this.connection.add(this);
				
	}
	
	/**
	 * 
	 * @param text action to perform
	 */
	public void sendMessage(String text){
		SpreadMessage message = new SpreadMessage();
		message.setData(text.getBytes());
		message.addGroup(this.groupName);
		message.setReliable();
		
		try {
			connection.multicast(message);
		} catch (SpreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void regularMessageReceived(SpreadMessage message) {
//		System.out.println(this.getConnName() + " => New message from " + 
//		message.getSender() + ": " + new String(message.getData()));
		
		FormatCommand fc = IOFileParsing.getFormatCommandFromLine(new String(message.getData()));
		try {
			fc.performActionOnFrom(this,message.getSender());
		} catch (UnknowAction e) {
			System.out.println("Action unknown!");
		}
		
		
	}

	@Override
	public void membershipMessageReceived(SpreadMessage message) {
//		System.out.println(this.getConnName() + " => New membership message from " + message.getSender() 
//		+ ". Number of member " + message.getMembershipInfo().getMembers().length);
		
		for(SpreadGroup s : message.getGroups())
			this.listReplica.add(s.toString());
	}

	
	/**
	 * Exit the connection
	 */
	public void exit() {
		try {
			this.connection.disconnect();
		} catch (SpreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/***
	 * Print the current balance
	 */
	public void balance() {
		System.out.println(this.getConnName() + ": You have " + this.cash);
		
	}

	/**
	 * Add toAdd to the current amount
	 * @param toAdd the amount to add
	 */
	public void deposit(Float toAdd) {
		this.cash.deposit(toAdd);
		
	}
	
	/**
	 * Increase the amount by the percentage like so: * (1 + percentage/100)
	 * @param percentage
	 */
	public void addinterest(Float percentage) {
		this.cash.addinterest(percentage);
		
	}
	
	public void exchangeNOK()
	{
		this.cash.toNOK();
	}
	
	public void exchangeEUR()
	{
		this.cash.toEUR();
	}

	public void exchangeUSD()
	{
		this.cash.toUSD();
	}


	public String getConnName() {
		return connName;
	}

	public void memberinfo() {
		System.out.println(this.connName +":");
		// TODO: print list member
		for(String member: this.listReplica)
		{
			System.out.println("\t- " + member);
		}
		
	}

	public void sleep(Integer seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}

	public void exit(SpreadGroup sender) {
		this.listReplica.remove(sender.toString());
	}




}

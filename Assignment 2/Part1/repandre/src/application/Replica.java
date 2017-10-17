package application;

import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.plaf.synth.SynthSeparatorUI;

import spread.AdvancedMessageListener;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

public class Replica implements AdvancedMessageListener  {
	
	private String groupName = null;
	private String serverName = null;
	private String connName = null;
	private Integer port = null;
	private static final int RANGE_NAME = 100;
	
	private SpreadConnection connection = null;
	
	
	
	
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
		this.port = nameSplit.length == 2 ? new Integer(nameSplit[1]) : 4803;
		this.generateConnectionName();
		System.out.println("server : " +this.serverName + " port = " + this.port + " name = " + this.connName);
		this.init();
		System.out.println("################");
	}
	
	private void generateConnectionName()
	{
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(RANGE_NAME);
		this.connName =  "CliNum-" + randomInt;
	}





	/**
	 * Initialize the connexion on the Spread server and joint de repective group.
	 * @throws SpreadException 
	 * @throws UnknownHostException 
	 */
	public void init() throws UnknownHostException, SpreadException{
		
		this.connection = new SpreadConnection();
		this.connection.connect(InetAddress.getByName(this.serverName), this.port.intValue(), this.connName, false, true);
		SpreadGroup group = new SpreadGroup();
		group.join(this.connection, this.groupName);
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
		System.out.println(this.connName + " => New message from " + 
		message.getSender() + ": " + new String(message.getData()));
		
	}

	@Override
	public void membershipMessageReceived(SpreadMessage message) {
		System.out.println(this.connName + " => New membership message from " + message.getSender()  
		+ "Number of member " + message.getMembershipInfo().getMembers().length);
		
	}

}

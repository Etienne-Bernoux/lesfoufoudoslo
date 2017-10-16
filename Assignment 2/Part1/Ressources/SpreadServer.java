package apps;

import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

public class SpreadServer {
	
	public SpreadConnection connection;
	
	public void init(){
		connection = new SpreadConnection();
		try {
			connection.connect(InetAddress.getByName("rubin.ifi.uio.no"), 4333, "ServerConn", false, true);
			
			SpreadGroup group = new SpreadGroup();
			group.join(connection, "testGroup");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SpreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void receiveMessage(){
		while(true){
			try {
				SpreadMessage message = connection.receive();
				
				if(message.isRegular())
					System.out.println("New message from " + 
					message.getSender() + ": " + new String(message.getData()));
				else
					System.out.println("New membership message from " + 
					message.getMembershipInfo().getMembers().length);
				
			} catch (InterruptedIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SpreadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	public static void main(String[] args) {
		System.out.println("Starting Server...");
		SpreadServer server = new SpreadServer();
		server.init();
		
		System.out.println("Receiving messages from the group");
		server.receiveMessage();
		
	}

}

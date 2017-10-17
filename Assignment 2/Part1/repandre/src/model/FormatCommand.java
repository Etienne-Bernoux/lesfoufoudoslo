package model;

import application.Replica;
import spread.SpreadGroup;

public class FormatCommand {
	
	private String action = null;
	private String param1 = null;
	private String param2 = null;
	
	public FormatCommand(String[] request)
	{
		this.action = request[0];
		
		if (request.length == 2)
		{
			this.param1 = request[1];
		}
		else if (request.length == 3)
		{
			this.param1 = request[1];
			this.param2 = request[2];
		}
	}
	
	public String getAction() {
		return this.action;
	}

	public String getParam1() {
		return this.param1;
	}

	public String getParam2() {
		return this.param2;
	}

	public void sendWith(Replica replica) throws UnknowAction {
		switch (this.getAction())
		{
			case "exit":
				replica.sendMessage(this.getAction());
				break;
				
			case "balance":
				replica.sendMessage(this.getAction());
				break;
				
			case "deposit":
				replica.sendMessage(this.getAction() + " " + this.getParam1());
				break;
				
			case "addinterest":
				replica.sendMessage(this.getAction() + " " + this.getParam1());
				break;
				
			case "exchange":
				replica.sendMessage(this.getAction() + " " + this.getParam1() + " " + this.getParam2());
				break;
				
			case "memberinfo":
				replica.sendMessage(this.getAction());
				break;
				
			case "sleep":
				replica.sendMessage(this.getAction() + " " + this.getParam1());
				break;
			default:
				throw new UnknowAction(this.action);
		}
		
	}

	private void performItsOwnMesageOn(Replica replica) throws UnknowAction {
		switch (this.getAction())
		{
			case "exit":
				replica.exit();
				break;
				
			case "balance":
				replica.balance();
				break;
				
			case "deposit":
				replica.deposit(new Float(this.getParam1()));
				break;
				
			case "addinterest":
				replica.addinterest(new Float(this.getParam1()));
				break;
				
			case "exchange":
				if (this.param2.equals("NOK"))
					replica.exchangeNOK();
				else if (this.param2.equals("EUR"))
					replica.exchangeEUR();
				else if (this.param2.equals("USD"))
					replica.exchangeUSD();
				else
					throw new UnknowAction(this.action + " " + this.param1 + " " + this.param2);
				break;
				
			case "memberinfo":
				replica.memberinfo();
				break;
				
			case "sleep":
				replica.sleep(new Integer(this.param1));
				break;
			default:
				throw new UnknowAction(this.action);
		}
	}
	
	
	private void performMesageOnFromOtherSender(Replica replica, SpreadGroup sender) throws UnknowAction {
		switch (this.getAction())
		{
			case "exit":
				replica.exit(sender);
				break;
				
			case "balance":
				
				break;
				
			case "deposit":
				replica.deposit(new Float(this.getParam1()));
				break;
				
			case "addinterest":
				replica.addinterest(new Float(this.getParam1()));
				break;
				
			case "exchange":
				if (this.param2.equals("NOK"))
					replica.exchangeNOK();
				else if (this.param2.equals("EUR"))
					replica.exchangeEUR();
				else if (this.param2.equals("USD"))
					replica.exchangeUSD();
				else
					throw new UnknowAction(this.action + " " + this.param1 + " " + this.param2);
				
				break;
				
			case "memberinfo":

				break;
				
			case "sleep":

				break;
			default:
				throw new UnknowAction(this.action);
		}
	}

	public void performActionOnFrom(Replica replica, SpreadGroup sender) throws UnknowAction {
		if(sender.toString().split("#")[1].equals(replica.getConnName()))
		{
			this.performItsOwnMesageOn(replica);
		}
		else
		{
			this.performMesageOnFromOtherSender(replica, sender);
		}
		
	}

}

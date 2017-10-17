package model;

import application.Replica;

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

	public void executeOn(Replica replica) throws UnknowAction {
		switch (this.getAction())
		{
			case "exit":
				break;
				
			case "balance":
				replica.sendMessage(this.action);
				break;
				
			case "deposit":
				break;
				
			case "addinterest":
				break;
				
			case "exchange":
				break;
				
			case "memberinfo":
				break;
				
			case "sleep":
				break;
			default:
				throw new UnknowAction(this.action);
		}
		
	}

}

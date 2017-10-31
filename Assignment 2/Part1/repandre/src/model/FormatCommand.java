package model;

import application.Replica;
import spread.SpreadGroup;

public class FormatCommand {
	
	private String action = null;
	private String param1 = null;
	private String param2 = null;
	private SpreadGroup sender = null;
	
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

	public void sendWith(Replica replica) throws UnknownAction {
		switch (this.getAction())
		{
			case "exit":
				replica.sendMessage(this.getAction());
				break;
				
			case "balance":
				replica.sendMessage(this.getAction());
				break;
				
			case "deposit":
			    if(this.getParam1() == null){
                    throw new UnknownAction(this.action);
                }
				replica.sendMessage(this.getAction() + " " + this.getParam1());
				break;
				
			case "addinterest":
                if(this.getParam1() == null){
                    throw new UnknownAction(this.action);
                }
				replica.sendMessage(this.getAction() + " " + this.getParam1());
				break;
				
			case "exchange":
                if(this.getParam1() == null || this.getParam2() == null){
                    throw new UnknownAction(this.action);
                }
				replica.sendMessage(this.getAction() + " " + this.getParam1() + " " + this.getParam2());
				break;
				
			case "memberinfo":

				replica.sendMessage(this.getAction());
				break;
				
			case "sleep":
                if(this.getParam1() == null){
                    throw new UnknownAction(this.action);
                }
				replica.sendMessage(this.getAction() + " " + this.getParam1());
				break;
			default:
				throw new UnknownAction(this.action);
		}
		
	}

	private void performItsOwnMessageOn(Replica replica) throws UnknownAction, InvalidFromCurrency {
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
                replica.exchange(this.getParam1(),this.getParam2());
                break;
				
			case "memberinfo":
				replica.memberinfo();
				break;
				
			case "sleep":
				replica.sleep(new Integer(this.param1));
				break;
            case "welcome":
                break;
			default:
				throw new UnknownAction(this.action);
		}
	}
	
	
	private void performMessageOnFromOtherSender(Replica replica) throws UnknownAction, InvalidFromCurrency {
		switch (this.getAction())
		{
			case "exit":
				replica.exit(sender);
				break;
				
			case "balance":
				
				break;
				
			case "deposit":
                if(this.getParam1() == null){
                    throw new UnknownAction(this.action);
                }
				replica.deposit(new Float(this.getParam1()));
				break;
				
			case "addinterest":
                if(this.getParam1() == null){
                    throw new UnknownAction(this.action);
                }
				replica.addinterest(new Float(this.getParam1()));
				break;
				
			case "exchange":
                if(this.getParam1() == null || this.getParam2() == null){
                    throw new UnknownAction(this.action);
                }
                replica.exchange(this.getParam1(),this.getParam2());
				break;
				
			case "memberinfo":
				break;
				
			case "sleep":
				break;
            case "welcome":
                break;
			default:
				throw new UnknownAction(this.action);
		}
	}

    /**
     * Action to do when receive a message during init phase.
     * @param replica
     * @throws UnknownAction
     */
    private void performMessageInit(Replica replica) throws UnknownAction {
        switch (this.getAction())
        {
            // If it's a welcome message, we put a value in the hash map to count the occurrence of the same Cash object
            case "welcome":
                Cash myCash = new Cash(new Float(this.getParam1()),this.getParam2());
                Integer nbHit = replica.getMapCash().get(myCash);
                replica.getMapCash().put(myCash, nbHit == null? 1 : nbHit+1);
                replica.getListReplica().add(this.sender.toString());
                break;
            default:
                replica.getQueue().add(this);
        }
    }
    public void performMessageInQueue(Replica replica) throws UnknownAction, InvalidFromCurrency {
        if (this.sender.toString().split("#")[1].equals(replica.getConnName())) {
            this.performItsOwnMessageOn(replica);
        } else {
            this.performMessageOnFromOtherSender(replica);
        }
    }
	public void performActionOnFrom(Replica replica) throws UnknownAction, InvalidFromCurrency {
		if (replica.isReady()) {
            if (this.sender.toString().split("#")[1].equals(replica.getConnName())) {
                this.performItsOwnMessageOn(replica);
            } else {
                this.performMessageOnFromOtherSender(replica);
            }
		} else {
                this.performMessageInit(replica);
        }


	}

    public void setSender(SpreadGroup sender) {
        this.sender = sender;
    }
}

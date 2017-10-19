package application;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import model.*;
import spread.AdvancedMessageListener;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;
import tools.IOFileParsing;

public class Replica implements Runnable, AdvancedMessageListener  {
	
	private String groupName = null;
	private String serverName = null;
	private String connName = null;
	private Integer port = null;
	private static final int RANGE_NAME = 100;
	private Cash cash = null;
	private State state = null;

	private SpreadConnection connection = null;
	private Set<String> listReplica = null;

	private Queue<FormatCommand> queue = null;
	private Map<Cash,Integer> mapCash = null;
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
		this.state = State.INIT;
		this.queue = new ConcurrentLinkedQueue<FormatCommand>();
		this.mapCash = new HashMap<Cash,Integer>();
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
		//Event scheduler
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        // In 500 mS we will launch an event who we will process all the welcome message received
        executor.schedule(this,500, TimeUnit.MILLISECONDS);
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
		message.setFifo();
		
		try {
			connection.multicast(message);
		} catch (SpreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendWelcomeMessage(){
		SpreadMessage message = new SpreadMessage();
		message.setData(("welcome " + this.cash).getBytes());
		message.addGroup(this.groupName);
		message.setReliable();
		message.setFifo();

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
		fc.setSender(message.getSender());
		try {
			fc.performActionOnFrom(this);
		} catch (UnknownAction e) {
			System.out.println("Action unknown!");
		}
		
		
	}

	@Override
	public void membershipMessageReceived(SpreadMessage message) {
//		System.out.println(this.getConnName() + " => New membership message from " + message.getSender() 
//		+ ". Number of member " + message.getMembershipInfo().getMembers().length);
		
		for(SpreadGroup s : message.getGroups())
			this.listReplica.add(s.toString());
        if(this.isReady())
            this.sendWelcomeMessage();
    }

	
	/**
	 * Exit the connection
	 */
	public void exit() {
		try {
			if(!this.connection.isConnected())
			{
				this.connection.disconnect();
			}
			else
			{
				System.out.println("CLIENT WAS DISCONNECTED");
				this.connection.disconnect();
			}
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
	public void exchange(String from, String to) throws UnknownCurrency {
        if (to.equals("NOK"))
            this.exchangeNOK();
        else if (to.equals("EUR"))
            this.exchangeEUR();
        else if (to.equals("USD"))
            this.exchangeUSD();
        else
            throw new UnknownCurrency(to);
    }
	private void exchangeNOK()
	{
		this.cash.toNOK();
	}

    private void exchangeEUR()
	{
		this.cash.toEUR();
	}

    private void exchangeUSD()
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

	public boolean isDisconnected() {
		return !this.connection.isConnected();
	}

	public boolean isReady() {
		return this.state == State.READY;
	}
    public boolean isInQueue() {
        return this.state == State.IN_QUEUE;
    }
    public boolean isInit() {
        return this.state == State.INIT;
    }

	public Queue<FormatCommand> getQueue() {
		return queue;
	}

	public Map<Cash,Integer> getMapCash() {
		return mapCash;
	}

    public Set<String> getListReplica() {
        return listReplica;
    }

    /**
     * This method is call when the timeout for welcome message reception occur.
     * We use a timeout because we don't know the number of replica
     */
    @Override
    public void run() {
        int max = 0;
        int somme = 0;
        Cash bestCash = new Cash();
        for (Map.Entry<Cash,Integer> entry: mapCash.entrySet()){
            if(entry.getValue()>max){
                max = entry.getValue();
                bestCash = entry.getKey();
            }
            somme += entry.getValue();
        }
        // We use the Byzantine assumption to determine i
        if (somme == 0 || (max/somme>2/3)){
            this.cash = bestCash;
            this.state = State.IN_QUEUE;

            while (!this.getQueue().isEmpty()){
                try {
                    this.getQueue().poll().performMessageInQueue(this);
                } catch (UnknownAction unknownAction) {
                    unknownAction.printStackTrace();
                }
            }
            this.state = State.READY;
        }else{
            throw new ByzantineError();
        }


    }


}

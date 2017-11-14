package example.gossip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import peersim.cdsim.CDProtocol;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Linkable;
import peersim.core.Node;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;


/**
 * @author Lucas Provensi
 * 
 * Basic Shuffling protocol template
 * 
 * The basic shuffling algorithm, introduced by Stavrou et al in the paper: 
 * "A Lightweight, Robust P2P System to Handle Flash Crowds", is a simple 
 * peer-to-peer communication model. It forms an overlay and keeps it 
 * connected by means of an epidemic algorithm. The protocol is extremely 
 * simple: each peer knows a small, continuously changing set of other peers, 
 * called its neighbors, and occasionally contacts a random one to exchange 
 * some of their neighbors.
 * 
 * This class is a template with instructions of how to implement the shuffling
 * algorithm in PeerSim.
 * Should make use of the classes Entry and GossipMessage:
 *    Entry - Is an entry in the cache, contains a reference to a neighbor node
 *  		  and a reference to the last node this entry was sent to.
 *    GossipMessage - The message used by the protocol. It can be a shuffle
 *    		  request, reply or reject message. It contains the originating
 *    		  node and the shuffle list.
 *
 */
public class BasicShuffle  implements Linkable, EDProtocol, CDProtocol{
	
	private static final String PAR_CACHE = "cacheSize";
	private static final String PAR_L = "shuffleLength";
	private static final String PAR_TRANSPORT = "transport";

	private final int tid;

	// The list of neighbors known by this node, or the cache.
	private List<Entry> cache;
	
	// The maximum size of the cache;
	private final int size;
	
	// The maximum length of the shuffle exchange;
	private final int l;
	
	// Node waiting for a response from a shuffling operation
	private boolean isWaiting;
	
	// Node waiting for a response from a shuffling operation
	private List<Entry> subset;
	
	// buffer remove Q
	private Entry bufferRemove;
	
	/**
	 * Constructor that initializes the relevant simulation parameters and
	 * other class variables.
	 * 
	 * @param n simulation parameters
	 */
	public BasicShuffle(String n)
	{
		this.size = Configuration.getInt(n + "." + PAR_CACHE);
		this.l = Configuration.getInt(n + "." + PAR_L);
		this.tid = Configuration.getPid(n + "." + PAR_TRANSPORT);

		this.cache = new ArrayList<Entry>(size);
		this.isWaiting = false;
		this.bufferRemove = null;
	}

	/* START YOUR IMPLEMENTATION FROM HERE
	 * 
	 * The simulator engine calls the method nextCycle once every cycle 
	 * (specified in time units in the simulation script) for all the nodes.
	 * 
	 * You can assume that a node initiates a shuffling operation every cycle.
	 * 
	 * @see peersim.cdsim.CDProtocol#nextCycle(peersim.core.Node, int)
	 */
	@Override
	public void nextCycle(Node node, int protocolID) {

		// Implement the shuffling protocol using the following steps (or
		// you can design a similar algorithm):
		// Let's name this node as P
		
		// 1. If P is waiting for a response from a shuffling operation initiated in a previous cycle, return;
		if(this.isWaiting)
		{
			return;
		}
		// 2. If P's cache is empty, return;
		if(this.cache.isEmpty())
		{
			return;
		}
		// 3. Select a random neighbor (named Q) from P's cache to initiate the shuffling;
		//	  - You should use the simulator's common random source to produce a random number: CommonState.r.nextInt(cache.size())
		int indexQ = CommonState.r.nextInt(this.cache.size());
		Entry Q = this.cache.get(indexQ);
		// 4. If P's cache is full, remove Q from the cache;
		if (this.cache.size() == this.size)
		{
			this.cache.remove(indexQ);
			this.bufferRemove = Q;
		}

		// 5. Select a subset of other l - 1 random neighbors from P's cache;
		//	  - l is the length of the shuffle exchange
		//    - Do not add Q to this subset	
		this.subset = this.getShuffleEntryWithout(this.l - 1, Q);
			
		// 6. Add P to the subset;
		this.subset.add(new Entry(node));
		for(Entry e: this.subset)
		{
//			e.setSentTo(Q.getNode());
			e.setSentTo(null);
		}

			
		// 7. Send a shuffle request to Q containing the subset;
		//	  - Keep track of the nodes sent to Q
		//	  - Example code for sending a message:
		//
		// GossipMessage message = new GossipMessage(node, subset);
		// message.setType(MessageType.SHUFFLE_REQUEST);
		// Transport tr = (Transport) node.getProtocol(tid);
		// tr.send(node, Q.getNode(), message, protocolID);
		GossipMessage message = new GossipMessage(node, this.subset);
		message.setType(MessageType.SHUFFLE_REQUEST);
		Transport tr = (Transport) node.getProtocol(tid);
		tr.send(node, Q.getNode(), message, protocolID);
		
		
		// 8. From this point on P is waiting for Q's response and will not initiate a new shuffle operation;
		this.isWaiting = true;
		// The response from Q will be handled by the method processEvent.
		for(Entry e: this.cache)
		{
			if(this.subset.contains(e))
			{
				e.setSentTo(Q.getNode());
			}
		}
	
	}

	/* The simulator engine calls the method processEvent at the specific time unit that an event occurs in the simulation.
	 * It is not called periodically as the nextCycle method.
	 * 
	 * You should implement the handling of the messages received by this node in this method.
	 * 
	 * @see peersim.edsim.EDProtocol#processEvent(peersim.core.Node, int, java.lang.Object)
	 */
	@Override
	public void processEvent(Node node, int pid, Object event) {
		
		
		// Let's name this node as Q;
		// Q receives a message from P;
		//	  - Cast the event object to a message:
		GossipMessage message = (GossipMessage) event;
		
		Node nodeQ = node;
		Node nodeP = message.getNode();
		
		switch (message.getType()) {
		// If the message is a shuffle request:
		case SHUFFLE_REQUEST:
		//	  1. If Q is waiting for a response from a shuffling initiated in a previous cycle, 
		//			send back to P a message rejecting the shuffle request; 
			if(this.isWaiting)
			{
				GossipMessage answer = new GossipMessage(nodeQ, null);
				answer.setType(MessageType.SHUFFLE_REJECTED);
				Transport tr = (Transport) nodeQ.getProtocol(tid);
				tr.send(nodeQ, nodeP, answer, pid);
			}
			else
			{
				// this.isWaiting = true;
			
			//	  2. Q selects a random subset of size l of its own neighbors; 
				this.subset = this.getShuffleEntryWithout(this.l, new Entry(nodeP));
				for(Entry e: this.subset)
				{
//					e.setSentTo(nodeP);
					e.setSentTo(null);
				}
				List<Entry> cache = new ArrayList<>();
				for(Entry e: this.cache)
				{
					if(this.subset.contains(e))
					{
						e.setSentTo(nodeP);
						cache.add(e);
					}
				}

			//	  3. Q reply P's shuffle request by sending back its own subset;
				GossipMessage answer = new GossipMessage(nodeQ, this.subset);
				answer.setType(MessageType.SHUFFLE_REPLY);
				Transport tr = (Transport) nodeQ.getProtocol(tid);
				tr.send(nodeQ, nodeP, answer, pid);			
			//	  4. Q updates its cache to include the neighbors sent by P:
			//		 - No neighbor appears twice in the cache
			//		 - Use empty cache slots to add the new entries
			//		 - If the cache is full, you can replace entries among the ones sent to P with the
			//				new ones
				this.mergeEntriesFromWithout(message.getShuffleList(),nodeP, new Entry(nodeQ),cache);
			}
			break;
		
		// If the message is a shuffle reply:
		case SHUFFLE_REPLY:
		//	  1. In this case Q initiated a shuffle with P and is receiving a response containing a subset of P's neighbors
				
		//	  2. Q updates its cache to include the neighbors sent by P:
		//		 - No neighbor appears twice in the cache
		//		 - Use empty cache slots to add new entries
		//		 - If the cache is full, you can replace entries among the ones originally sent to P with the new ones
			this.mergeEntriesFromWithout(message.getShuffleList(), nodeP, new Entry(nodeQ), this.subset);
		//	  3. Q is no longer waiting for a shuffle reply;	 
			this.isWaiting = false;
			break;
		
		// If the message is a shuffle rejection:
		case SHUFFLE_REJECTED:
		//	  1. If P was originally removed from Q's cache, add it again to the cache.
			if (this.bufferRemove != null)
			{
				Entry e = new Entry(this.bufferRemove.getNode());
				e.setSentTo(this.bufferRemove.getSentTo());
				this.cache.add(e);
				this.bufferRemove = null;
			}
		//	  2. Q is no longer waiting for a shuffle reply;
			this.isWaiting = false;
			break;
			
		default:
			break;
		}

	}
	
	
	private List<Entry> getShuffleEntryWithout(int nbEntry, Entry forbidenEntry)
	{
		List<Entry> subset = new ArrayList<Entry>();
		if(this.cache.isEmpty())
		{
			return subset;
		}
		List<Integer> indexes = new ArrayList<Integer>();
		for(int i = 0; i < this.cache.size(); i ++)
		{
			indexes.add(i, i);
		}
		Collections.shuffle(indexes);
//		while(indexes.size() != this.cache.size())
//		{
//		        Integer rand = new Integer(CommonState.r.nextInt(this.cache.size()));
//		        if(!indexes.contains(rand))
//		        {
//		                indexes.add(rand);
//		        }
//		}
		
		
		Queue<Integer> queueRemaindingCacheIndex = new LinkedList<Integer>(indexes);
		while(subset.size() < nbEntry && !queueRemaindingCacheIndex.isEmpty())
		{
			Entry e = this.cache.get(queueRemaindingCacheIndex.poll());
			if(!(e.equals(forbidenEntry) || subset.contains(e)))
				subset.add(e);
		}
		
		return subset;
	}
	
	private void mergeEntriesFromWithout(List<Entry> subsetRcv, Node nodeTo, Entry forbiden,List<Entry> cache)
	{
		// init new cache
		List<Entry> newCache = new ArrayList<Entry>();
		// shuffle the cache before use it
		List<Entry> cacheShuffle = this.getShuffleEntryWithout(this.cache.size(), forbiden);
		

		
		// First add the remaining cache without those sent by nodeFrom
		Queue<Entry> cacheShuffleQueue = new LinkedList<Entry>(cacheShuffle);
		Queue<Entry> queueRemindingCache = new LinkedList<Entry>();

		// add all node s
		if(cache != null){
			while(!cacheShuffleQueue.isEmpty())
			{
				Entry e = cacheShuffleQueue.poll();
				if(cache.contains(e))
				{
					queueRemindingCache.add(e);
				}
				else
				{
					this.addNodeTo(newCache, e);
				}
			}			
		}
		else
		{
			while(!cacheShuffleQueue.isEmpty())
			{
				Entry e = cacheShuffleQueue.poll();
				if(nodeTo.equals(e.getSentTo()))
				{
					queueRemindingCache.add(e);
				}
				else
				{
					this.addNodeTo(newCache, e);
				}
			}
		}

		// System.out.println(">>>" + queueRemindingCache.size());
		
		// Second add the subset that we have received
		for(int i = 0; i < subsetRcv.size() && newCache.size() < this.size; i ++)
		{
			Entry e = subsetRcv.get(i);
			this.addNodeTo(newCache, e);
		}

		// if we have some remaining space, we add those sent by nodeFrom
		while(newCache.size() < this.size && !queueRemindingCache.isEmpty())
		{

			Entry e = queueRemindingCache.poll();
			this.addNodeTo(newCache, e);
		}

		this.cache = new ArrayList<Entry>(newCache);
	}
	
	
	public boolean addNodeTo(List<Entry> collect, Entry neighbour) {
		if (collect.contains(neighbour))
			return false;

		if (collect.size() >= this.size)
			return false;

		collect.add(neighbour);
		return true;
	}
/* The following methods are used only by the simulator and don't need to be changed */
	
	@Override
	public int degree() {
		return cache.size();
	}

	@Override
	public Node getNeighbor(int i) {
		return cache.get(i).getNode();
	}

	@Override
	public boolean addNeighbor(Node neighbour) {
		if (this.contains(neighbour))
			return false;

		if (cache.size() >= size)
			return false;

		Entry entry = new Entry(neighbour);
		cache.add(entry);
		return true;
	}

	@Override
	public boolean contains(Node neighbor) {
		return cache.contains(new Entry(neighbor));
	}

	public Object clone()
	{
		BasicShuffle gossip = null;
		try { 
			gossip = (BasicShuffle) super.clone(); 
		} catch( CloneNotSupportedException e ) {
			
		} 
		gossip.cache = new ArrayList<Entry>();

		return gossip;
	}

	@Override
	public void onKill() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void pack() {
		// TODO Auto-generated method stub	
	}
}

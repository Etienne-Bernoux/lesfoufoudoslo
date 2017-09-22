package server;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import model.Song;
import model.User;
import model.UserCounter;
import profileapp.ProfilerPOA;
import tools.IOFileParsing;

public class ProfileServant extends ProfilerPOA {

//	private static final String databaseFile = "./resources/train_triplets_extract.txt";
	private static final String databaseFile = "./resources/train_triplets.txt";
	private static final Integer maxUser = new Integer(1000);
	private Map<String, Integer> bufferSongHit = null;
	private Map<String, User> bufferUserProfile = null;
	
	public ProfileServant(){
		super();
		this.initBuffers();

		System.out.println("Buffer Ready!");
	}
	
	private void initBuffers()
	{
		
		this.bufferSongHit = new HashMap<String, Integer>();
		this.bufferUserProfile = new HashMap<String, User>();

		// MEMO : Sort on key
		SortedSet<UserCounter> setUserCounter = new TreeSet<UserCounter>();
		Set<String> setTopUSer = new HashSet<String>();
		BufferedReader br = null;
		FileReader fr = null;
		// open input file
		try
		{
			fr = new FileReader(databaseFile);
			br = new BufferedReader(fr);
		    // read the line line per line
		    String line = null;
		    while ((line = br.readLine()) != null)
		    {
		    	// extract the value of the line
		    	String[] parseLine = IOFileParsing.parseLineTab(line);
		    	FormatData fd = new FormatData(parseLine);
		    	
		    	// take the value of the of associate song (O if absent)
		    	Integer previousValSong = this.bufferSongHit.get(fd.getSongId()) == null ? new Integer(0) : this.bufferSongHit.get(fd.getSongId());

		    	// add the previous value with the current nbPlay
		    	bufferSongHit.put(fd.getSongId(), fd.getNbPlay() + previousValSong);
		    	
		    	
		    }
		    
		    System.out.println("Buffer1 OK");
		    // BUFFER 2 1/2
		    br.close();
		    fr.close();
			fr = new FileReader(databaseFile);
			br = new BufferedReader(fr);
			String lastUserId = null;
			Integer scoreTotalUser = new Integer(0);
			
		    while ((line = br.readLine()) != null)
		    {
		    	// extract the value of the line
		    	String[] parseLine = IOFileParsing.parseLineTab(line);
		    	FormatData curFd = new FormatData(parseLine);
		    	
		    	String curUserId = curFd.getUserId();
		    	Integer curScore = curFd.getNbPlay();
		    	
		    	// We read a new line of this same user
		    	if( curUserId.equals(lastUserId) || lastUserId == null)
		    	{
		    		scoreTotalUser = scoreTotalUser + curScore;
		    		lastUserId = curUserId;
		    	}
		    	// It is a line from a new user
		    	else
		    	{
		    		setUserCounter.add(new UserCounter(lastUserId, scoreTotalUser));
		    		scoreTotalUser = new Integer(curScore.intValue());
		    		lastUserId = curUserId;
		    	}
		    }		    
			

		    System.out.println("Buffer2 1/2 OK");
		    
		    // BUFFER 2 2/2
		    br.close();
		    fr.close();
			fr = new FileReader(databaseFile);
			br = new BufferedReader(fr);
			
			
			//Select Top 1000
			Iterator<UserCounter> it = setUserCounter.iterator();
			for(int i = 0; i < maxUser && it.hasNext(); i ++)
			{
				setTopUSer.add(it.next().getId());
			}
			
			
		    while ((line = br.readLine()) != null)
		    {
		    	// extract the value of the line
		    	String[] parseLine = IOFileParsing.parseLineTab(line);
		    	FormatData curFd = new FormatData(parseLine);
		    	
		    	String curUserId = curFd.getUserId();
		    	
		    	if(setTopUSer.contains(curUserId))
		    	{
			    	User u = this.bufferUserProfile.get(curUserId) == null ? new User(curUserId) : this.bufferUserProfile.get(curUserId);
			    	Song s = new Song(curFd.getSongId(), curFd.getNbPlay());
			    	u.updateSong(s);
			    	
			    	this.bufferUserProfile.put(curUserId, u);
		    	}
		    }	
		    System.out.println("Buffer2 2/2 OK");

		    
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			if(fr != null)
				try {fr.close();}
				catch (IOException e) {e.printStackTrace();}
			if(br != null)
				try {br.close();}
				catch (IOException e) {e.printStackTrace();}
		}
		
	}

	private static void simulateNetworkLatency(){
		try {
			Thread.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getTimesPlayed(String song_id) {
		ProfileServant.simulateNetworkLatency();
//		System.out.println("Message from client : " + song_id);
		
		// if the value of is our cache, we do not go over the file
		if(this.bufferSongHit != null && this.bufferSongHit.get(song_id) != null)
		{
			return this.bufferSongHit.get(song_id);
		}
		
		Integer res = new Integer(0);
		BufferedReader br = null;
		FileReader fr = null;
		// open input file
		try
		{
			fr = new FileReader(databaseFile);
			br = new BufferedReader(fr);

		    // read the line line per line
		    String line = null;
		    while ((line = br.readLine()) != null)
		    {
		    	// extract the value of the line
		    	String[] parseLine = IOFileParsing.parseLineTab(line);
		    	FormatData fd = new FormatData(parseLine);
		    	res += fd.getNbPlaySong(song_id);
		    }
			ProfileServant.simulateNetworkLatency();
			}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			if(fr != null)
				try {fr.close();}
				catch (IOException e) {e.printStackTrace();}
			if(br != null)
				try {br.close();}
				catch (IOException e) {e.printStackTrace();}
		}

		return res.intValue();
	}

	@Override
	public int getTimesPlayedByUser(String user_id, String song_id) {
		ProfileServant.simulateNetworkLatency();
		
		// if the value of is our cache, we do not go over the file
		if(this.bufferUserProfile != null && this.bufferUserProfile.get(user_id) != null)
		{
			return this.bufferUserProfile.get(user_id).getNbPlaySong(song_id);
		}
		
		Integer res = new Integer(0);
		BufferedReader br = null;
		FileReader fr = null;
		// open input file
		try
		{
			fr = new FileReader(databaseFile);
			br = new BufferedReader(fr);

		    // read the line line per line
		    String line = null;
		    while ((line = br.readLine()) != null)
		    {
		    	// extract the value of the line
		    	String[] parseLine = IOFileParsing.parseLineTab(line);
		    	FormatData fd = new FormatData(parseLine);
		    	res += fd.getNbPlaySongAndUser(song_id, user_id);
		    }
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			if(fr != null)
				try {fr.close();}
				catch (IOException e) {e.printStackTrace();}
			if(br != null)
				try {br.close();}
				catch (IOException e) {e.printStackTrace();}
		}

		return res.intValue();
	}

	
	
}

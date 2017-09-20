package server;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import model.User;
import model.UserCounter;
import profileapp.ProfilerPOA;
import tools.IOFileParsing;

public class ProfileServant extends ProfilerPOA {

//	private static final String databaseFile = "./resources/train_triplets_extract.txt";
	private static final String databaseFile = "./resources/train_triplets.txt";
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
			return this.bufferSongHit.get(song_id);

		
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
//		System.out.println("Message from client : " + song_id);
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

		ProfileServant.simulateNetworkLatency();
		return res.intValue();
	}
	
	public static void main(String[] arg)
	{
		
	}
	
	
}

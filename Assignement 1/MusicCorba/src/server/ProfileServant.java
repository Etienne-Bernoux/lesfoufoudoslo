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
import java.util.concurrent.Semaphore;

import model.SongImpl;
import model.TopTenImpl;
import model.UserImpl;
import model.UserCounter;
import profileapp.ProfilerPOA;
import profileapp.TopTen;
import profileapp.User;
import tools.IOFileParsing;

public class ProfileServant extends ProfilerPOA {

//	private static final String databaseFile = "./resources/train_triplets_extract.txt";
	private static final String databaseFile = "./resources/train_triplets.txt";
	private static final Integer maxUser = 1000;
	private Worker worker = null;
	
	ProfileServant(){
		this(new Boolean(true));
	}
	
	ProfileServant(Boolean enableBuffer){
		super();
		if(enableBuffer)
		{
			this.initBuffers();
		}
	}

    private class Worker implements Runnable {
        private Map<String, Integer> bufferSongHit;
        private Map<String, UserImpl> bufferUserProfile;
        private TopTen tt;
        private Semaphore flagTopTen = new Semaphore(1);

        Worker() {
    		this.bufferSongHit = new HashMap<String, Integer>();
    		this.bufferUserProfile = new HashMap<String, UserImpl>();
    		this.tt = new TopTenImpl();
    		try {
				this.flagTopTen.acquire();
			} catch (InterruptedException e) {
				this.flagTopTen.release();
				e.printStackTrace();
			}
        }
        
        private void fillBuffer1() throws IOException
        {
        	BufferedReader br = null;
            FileReader fr = null;

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
            br.close();
            fr.close();
        }
        private void fillBuffer2andTopTen() throws IOException
        {
        	BufferedReader br = null;
            FileReader fr = null;

            // MEMO : Sort on key
            SortedSet<UserCounter> setUserCounter = new TreeSet<UserCounter>();
            Set<String> setTopUSer = new HashSet<String>();
            Set<String> setTopUSer10 = new HashSet<String>();

            fr = new FileReader(databaseFile);
            br = new BufferedReader(fr);
           
            String lastUserId = null;
            Integer scoreTotalUser = 0;
            // read the line line per line
            String line = null;
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
                    scoreTotalUser = curScore;
                    lastUserId = curUserId;
                }
            }
            
            // Remplir topten
            Iterator<UserCounter> it10 = setUserCounter.iterator();
            for(int i = 0; i < 10 && it10.hasNext(); i ++)
            {
                setTopUSer10.add(it10.next().getId());
            }

            // close and open the buffer to go back to the begining of the file
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
     
            // Collect the profile of the 1000 most important users
            while ((line = br.readLine()) != null)
            {
                // extract the value of the line
                String[] parseLine = IOFileParsing.parseLineTab(line);
                FormatData curFd = new FormatData(parseLine);

                String curUserId = curFd.getUserId();

                if(setTopUSer.contains(curUserId))
                {
                    UserImpl u = this.bufferUserProfile.get(curUserId) == null ? new UserImpl(curUserId) : this.bufferUserProfile.get(curUserId);
                    SongImpl s = new SongImpl(curFd.getSongId(), curFd.getNbPlay());
                    u.updateSong(s);

                    this.bufferUserProfile.put(curUserId, u);
                }
                
            }
            
            // Now have have the profile, we just fill the top ten users
            Iterator<String> it102 = setTopUSer10.iterator();
            for(int i = 0; i < 10 && it10.hasNext(); i ++)
            {
                this.tt.topTenUsers[i] = this.bufferUserProfile.get(it102.next());
            }
            
            br.close();
            fr.close();
        }
        
        public void run() {
            
        	try {
				this.fillBuffer1();
				System.out.println("Buffer 1 : OK");
	        	this.fillBuffer2andTopTen();
	        	this.flagTopTen.release();
	        	System.out.println("Buffer 2 : OK");
	        	System.out.println("Top Ten  : OK");
			} catch (IOException e) {
				e.printStackTrace();
			}


   
        }
    }
	
	private void initBuffers()
	{
		this.worker = new Worker();

		// open input file

		Thread myThread = new Thread(this.worker);
		myThread.start();

		
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
		if(this.worker != null && this.worker.bufferSongHit != null && this.worker.bufferSongHit.get(song_id) != null)
		{
			return this.worker.bufferSongHit.get(song_id);
		}
		
		Integer res = 0;
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
		if(this.worker != null && this.worker.bufferUserProfile != null && this.worker.bufferUserProfile.get(user_id) != null)
		{
			return this.worker.bufferUserProfile.get(user_id).getNbPlaySong(song_id);
		}
		
		Integer res = 0;
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

	@Override
	public User getUserProfile(String user_id,String song_id){
		if(this.worker != null && this.worker.bufferUserProfile != null && this.worker.bufferUserProfile.get(user_id) != null)
		{
			return this.worker.bufferUserProfile.get(user_id);
		}
		else{
			UserImpl res = new UserImpl(user_id);
			BufferedReader br = null;
			FileReader fr = null;
			// open input file
			try
			{
				fr = new FileReader(databaseFile);
				br = new BufferedReader(fr);
				// 0 : user not find yet
				// 1 : user find (in the file) but not finish to collect all song
				// 2 : finish to collect all song who belong to this user
				int state = 0;
				// read the line line per line
				String line = null;
				while ((line = br.readLine()) != null && state != 2)
				{
					// extract the value of the line
					String[] parseLine = IOFileParsing.parseLineTab(line);
					FormatData fd = new FormatData(parseLine);
					if(fd.getUserId().equals(user_id)){
						state = 1;
						SongImpl s = new SongImpl(fd.getSongId(),fd.getNbPlay());
						res.updateSong(s);
					}
					else
						if(state == 1)
							state = 2;
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

			return res;
		}
	}

	@Override
	public TopTen getTopTenUsers() {
		// tt is feed by our worker
		if(this.worker == null)
			return null;
		TopTen tt = null;
		try {
			this.worker.flagTopTen.acquire();
			tt = this.worker.tt;
			this.worker.flagTopTen.release();
		} catch (InterruptedException e) {
			this.worker.flagTopTen.release();
			e.printStackTrace();
		}

		
		return tt;
	}
	
}

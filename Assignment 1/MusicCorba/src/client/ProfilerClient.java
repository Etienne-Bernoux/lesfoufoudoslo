package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import model.UserImpl;
import profileapp.Profiler;
import profileapp.ProfilerHelper;
import tools.IOFileParsing;

public class ProfilerClient {

	private static Profiler profilerImpl = null;
	private Map<String, UserImpl> bufferUserProfile = null;
	
	public ProfilerClient(Boolean cache){
		if(cache)
			this.bufferUserProfile = new HashMap<String, UserImpl>();
	}
	
	
	public static void main(String[] args) {
		
		if(args.length != 3)
		{
			System.out.println("Usage : Client True|False <input file> <output file>");
			return;
		}
		
		Boolean cache = new Boolean(args[0]);
		String pathInputFile = args[1];
		String pathOutputFile = args[2];

        BufferedReader br = null;
		File file = new File(pathOutputFile);
		// clean output file
		if (file.exists())
			if (!file.delete())
				System.err.println("Error : Imposible to clean the output file.");
		
		try
		{
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);
	
			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is 
			// part of the Interoperable naming Service.  
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	
			// resolve the Object Reference in Naming
			String name = "Profiler";
			profilerImpl = ProfilerHelper.narrow(ncRef.resolve_str(name));
			// create client object
			ProfilerClient pc = new ProfilerClient(cache);
			
			// open input file
			br = new BufferedReader(new FileReader(pathInputFile));

		    // read the line line per line
		    String line = null;
		    while ((line = br.readLine()) != null)
		    {
		    	String[] tokens = IOFileParsing.parseLineTab(line);
	    		FormatRequest fr = new FormatRequest(tokens);
	    		int times = 0;
	    		long timesExec = 0, t1 = 0, t2 = 0;
	    		
	    		
	    		// call the right function
	    		if(fr.getNameFonction().equals("getTimesPlayed"))
	    		{
	    			t1 = System.currentTimeMillis();
		    		times = profilerImpl.getTimesPlayed(fr.getSongId());
	    			t2 = System.currentTimeMillis();
	    		}
	    		else if(fr.getNameFonction().equals("getTimesPlayedByUser")){
	    			t1 = System.currentTimeMillis();
	    			// if we active cache
	    			if(cache)
	    			{
	    				// first, look into our cache
		    			UserImpl u = pc.bufferUserProfile.get(fr.getUserId());
		    			// if there is nothing for the user in our cache, we call the profile user function
		    			if(u == null){
		    				u = (UserImpl) profilerImpl.getUserProfile(fr.getUserId(), fr.getSongId());
		    				pc.bufferUserProfile.put(fr.getUserId(), u);
		    			}
		    			times = u.getNbPlaySong(fr.getSongId());
	    			}
	    			// no caching
	    			else
	    			{
	    				times = profilerImpl.getTimesPlayedByUser(fr.getUserId(), fr.getSongId());
	    			}
		    		t2 = System.currentTimeMillis();
	    		}
	    		else
	    		{
	    			//It is an unknow function.
	    			System.err.println("Unknown function: " + fr.getNameFonction());
	    		}
	    		
	    		
	    		timesExec = t2 - t1;
	    		// write the result in the output file
	    		IOFileParsing.writeInOutputLine(times, fr, timesExec, pathOutputFile);
		    		
		    }
	
		} catch (Exception e) {
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		}
		finally
		{
			// close the buffer
			if (br!= null)
			{
				try {br.close();}
				catch (IOException e) {e.printStackTrace();}
			}
		}



	}

}

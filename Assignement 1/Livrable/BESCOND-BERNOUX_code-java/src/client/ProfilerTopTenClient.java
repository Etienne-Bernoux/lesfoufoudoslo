package client;

import java.io.File;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import profileapp.Profiler;
import profileapp.ProfilerHelper;
import profileapp.TopTen;
import tools.IOFileParsing;

public class ProfilerTopTenClient {

	private static Profiler profilerImpl = null;
	
	public ProfilerTopTenClient(){
	}
	
	
	public static void main(String[] args) {

		if(args.length != 1)
		{
			System.out.println("Usage : ClientTopTen <output file>");
		}
		
		String pathOutputFile = args[0];

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

		    //Get the topTen
            TopTen tt = profilerImpl.getTopTenUsers();
            IOFileParsing.writeInOutputLine(tt, pathOutputFile);

	
		} catch (Exception e) {
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		}

	}

}

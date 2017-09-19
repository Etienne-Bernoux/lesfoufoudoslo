package tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import client.FormatRequest;

/**
 * This class provide all method for parsing the input file and write the output file
 *
 */
public class IOFileParsing {
	
	
	public static String[] parseInputLine(String line)
	{
		return line.split("\t");
	}
	
	public static void writeInOutputLine(int resTimes, FormatRequest fr, long execTimes, String nameFile) throws IOException
	{
		FileWriter fw = new FileWriter(nameFile, true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write("Song " + fr.getSongId() + " played " + resTimes + " times" + (fr.getUserId() == null ? "." : " by user " + fr.getUserId()+ ".") + " (" + execTimes + " ms)\n");
		bw.flush();
		
		// close stream
		if(bw != null)
			bw.close();
		if(fw != null)
			fw.close();
		
	}

}

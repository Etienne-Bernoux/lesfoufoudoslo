package tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import client.FormatRequest;
import profileapp.Song;
import profileapp.TopTen;
import profileapp.User;

/**
 * This class provide all method for parsing the input file and write the output file
 *
 */
public class  IOFileParsing {
	
	
	public static String[] parseLineTab(String line)
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




	public static void writeInOutputLine(TopTen topten, String nameFile) throws IOException
	{
		FileWriter fw = new FileWriter(nameFile, true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for(User u : topten.topTenUsers){
			int nb = 0;
			for(Song s : u.songs){
				nb = nb + s.play_count;
			}
			bw.write(u.id + "\t" + nb + "\n");
		}
		bw.flush();
		
		// close stream
		if(bw != null)
			bw.close();
		if(fw != null)
			fw.close();
		
	}

}

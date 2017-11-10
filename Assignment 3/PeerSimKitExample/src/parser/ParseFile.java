package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseFile {

	static void modifyFile(String pathSrc, String pathDst)
    {
        File fileToBeModified = new File(pathSrc);
        BufferedReader reader = null;
        FileWriter writer = null;
        
        Pattern pattern = Pattern.compile("([0-9]+.[0-9]{11,})");
         
        try
        {
            writer = new FileWriter(pathDst);
        	reader = new BufferedReader(new FileReader(fileToBeModified));
            //Reading all the lines of input text file into oldContent
            String line = reader.readLine();
            while (line != null) 
            {
            	Matcher matcher = pattern.matcher(line);
            	if(matcher.find())
            	{
                    writer.write(matcher.group(1)+"\n");
            	}
                line = reader.readLine();
            }  
        }
        catch (IOException e)
        {
        	System.out.println("Erreur with file: " + pathSrc);
        }
        finally
        {
			try
			{
				//Closing the resources
				reader.close();
				writer.close();
			} 
			catch (Exception e) 
			{
//	        	System.out.println("Erreur with file: " + pathSrc);
			}
        }
    }
	
	
	static void modifyFileInDegree(String pathSrc, String pathDst)
    {
        File fileToBeModified = new File(pathSrc);
        BufferedReader reader = null;
        FileWriter writer = null;
        
        Pattern pattern = Pattern.compile("^([0-9]+)$");
         
        try
        {
            writer = new FileWriter(pathDst);
        	reader = new BufferedReader(new FileReader(fileToBeModified));
            //Reading all the lines of input text file into oldContent
            String line = reader.readLine();
            while (line != null) 
            {
            	Matcher matcher = pattern.matcher(line);
            	if(matcher.find())
            	{
                    writer.write(matcher.group(1)+"\n");
            	}
                line = reader.readLine();
            }  
        }
        catch (IOException e)
        {
        	System.out.println("Erreur with file: " + pathSrc);
        }
        finally
        {
			try
			{
				//Closing the resources
				reader.close();
				writer.close();
			} 
			catch (Exception e) 
			{
//	        	System.out.println("Erreur with file: " + pathSrc);
			}
        }
    }
	
	
	public static void main(String[] argv)
	{
		ParseFile.modifyFile("./out/pa30.txt", "./plot/pa30.txt");
		ParseFile.modifyFile("./out/pa50.txt", "./plot/pa50.txt");
		ParseFile.modifyFile("./out/paRandom30.txt", "./plot/paRandom30.txt");
		ParseFile.modifyFile("./out/paRandom50.txt", "./plot/paRandom50.txt");
		
		
		ParseFile.modifyFile("./out/cc30.txt", "./plot/cc30.txt");
		ParseFile.modifyFile("./out/cc50.txt", "./plot/cc50.txt");
		ParseFile.modifyFile("./out/ccRandom30.txt", "./plot/ccRandom30.txt");
		ParseFile.modifyFile("./out/ccRandom50.txt", "./plot/ccRandom50.txt");
		
		ParseFile.modifyFileInDegree("./out/id30.txt", "./plot/id30.txt");
		ParseFile.modifyFileInDegree("./out/id50.txt", "./plot/id50.txt");
		ParseFile.modifyFileInDegree("./out/idRandom30.txt", "./plot/idRandom30.txt");
		ParseFile.modifyFileInDegree("./out/idRandom50.txt", "./plot/idRandom50.txt");
		
		System.out.println("It's done Dude!");
	}
}

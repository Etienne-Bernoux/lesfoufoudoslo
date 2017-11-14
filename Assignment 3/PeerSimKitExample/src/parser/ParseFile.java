package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseFile {

	static void modifyFile(String pathSrc, String pathDst)
    {
        File fileToBeModified = new File(pathSrc);
        BufferedReader reader = null;
        PrintWriter writer = null;

        Pattern pattern = Pattern.compile("([0-9]+.[0-9]+)");
         
        try
        {
            writer = new PrintWriter(pathDst, "ASCII");
        	reader = new BufferedReader(new FileReader(fileToBeModified));
            //Reading all the lines of input text file into oldContent
        	
        	
            String line = reader.readLine();
            while (line != null) 
            {
            	Matcher matcher = pattern.matcher(line);
            	if(matcher.find())
            	{
                    writer.println(matcher.group(1));
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
            		System.out.println("Find");
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
		ParseFile.modifyFile("./out/ring-pa30.txt", "./plot/ring/pa30.txt");
		ParseFile.modifyFile("./out/ring-pa50.txt", "./plot/ring/pa50.txt");
		ParseFile.modifyFile("./out/star-pa30.txt", "./plot/star/pa30.txt");
		ParseFile.modifyFile("./out/star-pa50.txt", "./plot/star/pa50.txt");
		ParseFile.modifyFile("./out/random-paRandom30.txt", "./plot/random/paRandom30.txt");
		ParseFile.modifyFile("./out/random-paRandom50.txt", "./plot/random/paRandom50.txt");
		
		
		ParseFile.modifyFile("./out/ring-cc30.txt", "./plot/ring/cc30.txt");
		ParseFile.modifyFile("./out/ring-cc50.txt", "./plot/ring/cc50.txt");
		ParseFile.modifyFile("./out/star-cc30.txt", "./plot/star/cc30.txt");
		ParseFile.modifyFile("./out/star-cc50.txt", "./plot/star/cc50.txt");
		ParseFile.modifyFile("./out/random-ccRandom30.txt", "./plot/random/ccRandom30.txt");
		ParseFile.modifyFile("./out/random-ccRandom50.txt", "./plot/random/ccRandom50.txt");
		
		ParseFile.modifyFileInDegree("./out/ring-id30.txt", "./plot/ring/id30.txt");
		ParseFile.modifyFileInDegree("./out/ring-id50.txt", "./plot/ring/id50.txt");
		ParseFile.modifyFileInDegree("./out/star-id30.txt", "./plot/star/id30.txt");
		ParseFile.modifyFileInDegree("./out/star-id50.txt", "./plot/star/id50.txt");
		ParseFile.modifyFileInDegree("./out/random-idRandom30.txt", "./plot/random/idRandom30.txt");
		ParseFile.modifyFileInDegree("./out/random-idRandom50.txt", "./plot/random/idRandom50.txt");
		
		System.out.println("It's done Dude!");
	}
}

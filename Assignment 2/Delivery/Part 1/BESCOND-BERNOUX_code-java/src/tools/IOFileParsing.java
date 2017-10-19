package tools;



import model.FormatCommand;
import org.jetbrains.annotations.NotNull;

/**
 * This class provide all method for parsing the input file, and command line
 *
 */
public class  IOFileParsing {
	

	public static String[] parseLineTab(String line)
	{
		return line.split("\t| ");
	}

	
	public static FormatCommand getFormatCommandFromLine(String line)
	{
		
		return new FormatCommand(IOFileParsing.parseLineTab(line));
	}
}

package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.FormatCommand;
import model.UnknowAction;
import spread.SpreadException;
import tools.IOFileParsing;

public class AccountReplica {
	
	
	private static void printHelpStatement()
	{
		System.out.println("balance");
		System.out.println("	This command causes the client to print the current balance on the account.");
		System.out.println("");
		System.out.println("deposit <amount>");
		System.out.println("	This command causes the balance to increase by <amount>. This increase should be");
		System.out.println("	performed on all the replicas in the group.");
		System.out.println("");
		System.out.println("addinterest <percent>");
		System.out.println("	This command causes the balance to increase by <percent> percent of the current value. In");
		System.out.println("	other words, the balance should be multiplied by (1 + <percent>/100). This update should");
		System.out.println("	be performed on all the replicas in the group.");
		System.out.println("");
		System.out.println("exchange <from> <to>");
		System.out.println("	Exchanges the currency from e.g. NOK to USD. Support these currencies only: NOK,");
		System.out.println("	USD and EUR. Look at the “Currency table” down below for exchange rates.");
		System.out.println("");
		System.out.println("memberinfo");
		System.out.println("	Returns the names of the current participants in the group, and prints it to the screen.");
		System.out.println("");
		System.out.println("sleep <duration>");
		System.out.println("	This command causes the client to do nothing for <duration> seconds. It is only useful in");
		System.out.println("	a batch file.");
		System.out.println("");
		System.out.println("exit");
		System.out.println("	This command causes the client to exit. Alternatively, the client process can be just killed");
		System.out.println("	by the means of the operating system.");
	}

	public static void main(String[] args) throws IOException, SpreadException {

		if(args.length != 3 && args.length != 4)
		{
			System.out.println("Usage: accountReplica <server address> <account name> <number of replicas> [file name]");
			return;
		}

		String serverAddr = args[0];
		String accountName = args[1];
		Integer nbReplicas = new Integer(args[2]);
		String fileName = args.length == 4 ? args[3] : null;
		
		
		// Creation of the replica and lanch each listener (light Thread)
		Replica[] replicas = new Replica[nbReplicas];
		Thread[] replicasListener = new Thread[nbReplicas];
		
		for(int i = 0; i < nbReplicas; i++)
		{
			replicas[i] = new Replica(accountName, serverAddr);
		}

		
		/**
		 * 
		 * Put code init client
		 */
		
		// Then we print a prompt
		if(fileName == null)
		{
			int i = 1;
			boolean quit = false;
			String line = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Pattern emptyLine = Pattern.compile("^[ \t]*$");
			Matcher m = null;
			FormatCommand fc = null;
			
			System.out.println("Welcome on our Non-UI bank account. Write \"exit\" to show the command list:");

			while(!quit)
			{
				System.out.print("["+ i + "]: ");
				System.out.flush();
				line = br.readLine();
				m = emptyLine.matcher(line);
				// If we do not have an empty line
				if(!m.find())
				{
					fc = IOFileParsing.getFormatCommandFromLine(line);
					switch (fc.getAction())
					{
						case "help":
							AccountReplica.printHelpStatement();
							break;
						case "exit":
							quit = true;
						default:
							try {
								fc.executeOn(replicas[i%nbReplicas]);
							} catch (UnknowAction e) {
								System.out.println("Unkown action! See help.");
							}
					}
					
					i = i + 1;
				}
			}
			
			System.out.println("Ciao!");

			br.close();
			

		}
		// Then we execute the file
		else
		{
			BufferedReader br = null;
			// open input file
			br = new BufferedReader(new FileReader(fileName));
		    String line = null;
		    FormatCommand fc = null;
		    int i = 0;
		    while ((line = br.readLine()) != null)
		    {
		    	 fc = IOFileParsing.getFormatCommandFromLine(line);
		    	 try {
					fc.executeOn(replicas[i%nbReplicas]);
				} catch (UnknowAction e) {
					System.out.println("Unkown action! See help.");
				}
		    	i ++;
		    }
			br.close();
		}
		
		
		// we stop each thread
		for(int i = 0; i < nbReplicas; i++)
		{
			replicasListener[i].interrupt();
		}
		
	}

}

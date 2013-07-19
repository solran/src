package core;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import utilities.ReadLog;
import utilities.WriteLog;

// POur l'instant les instructions du 3 back sont les mêmes que pour le 2 back

public class Main {

	
	private static Menu myMenu;
	public static Task myTask;
	public static Fenetre myWindow = new Fenetre();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		myMenu = new Menu();			
	}

	@SuppressWarnings("deprecation")
	public static void start(HashMap<String,Integer> myParameters){
		/*PrintStream orgStream 	= null;
		PrintStream fileStream 	= null;
		try
		{
			// Saving the orginal stream
			orgStream = System.out;
			fileStream = new PrintStream(new FileOutputStream("data/console_" + myParameters.get("sujetID") + ".txt",true));
			// Redirecting console output to file
			System.setOut(fileStream);
			// Redirecting runtime exceptions to file
			System.setErr(fileStream);
			throw new Exception("Test Exception");

		}
		catch (FileNotFoundException fnfEx)
		{
			System.out.println("Error in IO Redirection");
			fnfEx.printStackTrace();
		}
		catch (Exception ex)
		{
			//Gets printed in the file
			System.out.println("Redirecting output & exceptions to file");
			ex.printStackTrace();
		}
		finally
		{
			//Restoring back to console
			System.setOut(orgStream);
			//Gets printed in the console
			System.out.println("Redirecting file output back to console");

		}*/
		
		myMenu.getIntroSong().getClip().stop();
		myMenu.clearMenu();
		myWindow.setTask();
		myWindow.revalidate();
		myWindow.repaint();
		myWindow.requestFocus();
		myTask = new Task(myParameters);
	}
}

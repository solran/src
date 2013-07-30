package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import overview.SimplifiedTask;

import core.Main;
import core.Task;


public class ReadLog {
	/* %!#Applet
	public static String sTab = "espacement", sParam = "findeligne";   // sParameter = separateur entre les lignes envoyées par param dans l'applet
	//*/
	///* %!#Desktop
	public static String sTab = "\t";
	//*/
	
	//??? Remove all?
	private static int  nbColomn = 0;  //??? Column
	private static String  sChariot = System.getProperty("line.separator");
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private static Date date = new Date();
	
	//permet de de vérifier dans le log si une session n'a pas été écrite précédemment
	public static int trouveSession (int ID, String location)
	{
		int session = 0;
		String line, lineSep[];	
		
		if(! Main.isApplet){
			try
			{           
				FileReader fic = new FileReader(location + ID + ".txt");
				BufferedReader in = new BufferedReader(fic);				
				in.readLine();		// saut de la ligne de l'entête
				while (in.ready()) 
				{
					line = in.readLine();
					lineSep = line.split(sTab);
	                session = Integer.parseInt(lineSep[1].trim()); //??? Pourquoi lire toutes les info à chaque fois? Sortir les trois lignes du while
				}
			}
			// Y'a rien dans le catch pcq ca veut dire que c'est la 1ere session
			catch (IOException E){
				System.out.print("première session");
				//Write title for `log` and `donnee`
				WriteLog.writeTitle("log", ID);
				WriteLog.writeTitle("donnee", ID);
			}
		}else if(Main.isApplet){
			/* %!#Applet
			String[] lastLine;
			String[] allLine;
			
			if(! Main.getInstance().getParameter("log").equals("0")){
				allLine = Main.getInstance().getParameter("log").split(sParam); //sParam
				lastLine = allLine[allLine.length - 1].split(sTab);		
				session = Integer.parseInt(lastLine[1].trim());
				System.out.println("Session: " + session);
			}else{
				System.out.print("première session");
			}
			//*/
		}
		
		return session;
	}
	
	public static boolean hasBeenTrained (int ID, String location, int howManyTime)
	{
		int timesTrained = 0;
		String type = "";
		String completed = "";
		String line, lineSep[];	
		
		if(! Main.isApplet){
			try
			{           
				FileReader fic = new FileReader(location + ID + ".txt");
				BufferedReader in = new BufferedReader(fic);			
				in.readLine();		// saut de la ligne de l'entête
				while (in.ready()) 
				{
					line = in.readLine();
					lineSep = line.split("\t");
	                type = lineSep[3].trim();
	                completed = lineSep[4].trim();
	                if (type.equals("training") && completed.equalsIgnoreCase("true"))
	                	timesTrained++;
				}
			}
			catch (IOException E){
				System.out.println("Erreur! Fichier en lecture inexistant");
			}
		}else if(Main.isApplet){
			/* %!#Applet
			String[] thisLine, allLine;
			
			allLine = Main.getInstance().getParameter("log").split(sParam);
			for(int i=1; i < allLine.length; i++){     // Débute à 1 pour sauter l'entete
				thisLine = allLine[i].split(sTab);
				type = thisLine[3].trim();
                completed = thisLine[4].trim();
                if (type.equals("training") && completed.equalsIgnoreCase("true"))
                	timesTrained++;
			}
			//*/
		}
		
		if (howManyTime <= timesTrained)
			return true;
		else
			return false;
	}
	
	public static ArrayList<String> readFullFile (Task task, String location)
	{
		ArrayList<String> fullFile = new ArrayList<String>();
		String lineSep[];	
		
		if(! Main.isApplet){
			try
			{           
				FileReader fic = new FileReader(location + ".txt");
				BufferedReader in = new BufferedReader(fic);
				while (in.ready()) 
				{
					fullFile.add(in.readLine());
				}
			}
			catch (IOException E){
				System.out.print("Document inexistant");
			}
		}else if(Main.isApplet){
			String[] allLine;
			
			/* %!#Applet
				allLine = Main.getInstance().getParameter("log").split(sParam);
				for(int i=0; i < allLine.length; i++){
					fullFile.add(allLine[i]);
				}
			//*/
		}
		
		return fullFile;
	}

	
	

	
	public static ArrayList<ArrayList<SimplifiedTask>> forSpeedOverview (int ID, String location, String version)
	{
		float score;
		int nbRow = 0;
		
		ArrayList<HashMap<String, String>> allLines = new ArrayList<HashMap<String, String>>();			
		HashMap<String, String> tempHashMap = new HashMap<String, String>();
		
		ArrayList<ArrayList<SimplifiedTask>> taskList = new ArrayList<ArrayList<SimplifiedTask>>();
		ArrayList<SimplifiedTask> tempTaskList = new ArrayList<SimplifiedTask>(), tempTaskList2 = new ArrayList<SimplifiedTask>();
		
		try
		{           
			FileReader fic = new FileReader(location + ID + ".txt");
			BufferedReader in = new BufferedReader(fic);
			String[] header, splittedLine;

			// entête
			header = in.readLine().split("\t");
						
			while (in.ready()) 
			{
				splittedLine = in.readLine().split("\t");
				
				for(int i = 0; i < header.length; i++){		//splittedLine.length
					tempHashMap.put(header[i], splittedLine[i]);
					//System.out.print(header[i] + " ");
				}
				allLines.add(tempHashMap);
				tempHashMap = new HashMap<String, String>();
				
				nbRow++;
			}
			
			
			if (version == "dtCost"){
				for(int i = 0; i < nbRow; i++){
	
					if(correctData(allLines, i)){
						score = (Float.parseFloat(allLines.get(i).get("dmgRt")) - Float.parseFloat(allLines.get(i).get("smgRt")))/Float.parseFloat(allLines.get(i).get("spgRt")) * 1000;
						tempTaskList.add(new SimplifiedTask(allLines.get(i).get("date"), Integer.parseInt(allLines.get(i).get("version").split(":")[0]), score));
						
						score = (Float.parseFloat(allLines.get(i).get("dmdRt")) - Float.parseFloat(allLines.get(i).get("smdRt")))/Float.parseFloat(allLines.get(i).get("spdRt")) * 1000;
						tempTaskList2.add(new SimplifiedTask(allLines.get(i).get("date"), Integer.parseInt(allLines.get(i).get("version").split(":")[0]), score));
					}
				}
			}else if (version == "taskCost"){
				for(int i = 0; i < nbRow; i++){
					
					if(correctData(allLines, i)){
						score = (Float.parseFloat(allLines.get(i).get("smgRt")) - Float.parseFloat(allLines.get(i).get("spgRt")))/Float.parseFloat(allLines.get(i).get("spgRt")) * 1000;
						tempTaskList.add(new SimplifiedTask(allLines.get(i).get("date"), Integer.parseInt(allLines.get(i).get("version").split(":")[0]), score));
						
						score = (Float.parseFloat(allLines.get(i).get("smdRt")) - Float.parseFloat(allLines.get(i).get("spdRt")))/Float.parseFloat(allLines.get(i).get("spdRt")) * 1000;
						tempTaskList2.add(new SimplifiedTask(allLines.get(i).get("date"), Integer.parseInt(allLines.get(i).get("version").split(":")[0]), score));
					}
				}
			}
			
		}catch (IOException E){
			E.printStackTrace();
		}
		
		taskList.add(tempTaskList);
		taskList.add(tempTaskList2);
		
		return taskList;
		
	}
	
	public static ArrayList<ArrayList<SimplifiedTask>> forAccOverview (int ID, String location)
	{

		int nbRow = 0;
		
		ArrayList<HashMap<String, String>> allLines = new ArrayList<HashMap<String, String>>();			
		HashMap<String, String> tempHashMap = new HashMap<String, String>();
		
		ArrayList<ArrayList<SimplifiedTask>> taskList = new ArrayList<ArrayList<SimplifiedTask>>();
		ArrayList<SimplifiedTask> tempTaskList = new ArrayList<SimplifiedTask>(), tempTaskList2 = new ArrayList<SimplifiedTask>();
		
		float tempScore = 0.f;
		float tempScore2 = 0.f;
		
		try
		{           
			FileReader fic = new FileReader(location + ID + ".txt");
			BufferedReader in = new BufferedReader(fic);
			String[] header, splittedLine;

			
			// entête
			header = in.readLine().split("\t");
			
			
			while (in.ready()) 
			{
				splittedLine = in.readLine().split("\t");
				
				for(int i = 0; i < header.length; i++){		//splittedLine.length
					tempHashMap.put(header[i], splittedLine[i]);
					//System.out.print(header[i] + " ");
				}
				allLines.add(tempHashMap);
				tempHashMap = new HashMap<String, String>();
				
				nbRow++;
			}

				for(int i = 0; i < nbRow; i++){
	
					if(correctData(allLines, i)){
						tempScore = (Float.parseFloat(allLines.get(i).get("dmgAll")) - Float.parseFloat(allLines.get(i).get("dmgError"))) / Float.parseFloat(allLines.get(i).get("dmgAll")) * 100;
						System.out.println("tempScore: " + tempScore);
						tempScore2 = (Float.parseFloat(allLines.get(i).get("dmdAll")) - Float.parseFloat(allLines.get(i).get("dmdError"))) / Float.parseFloat(allLines.get(i).get("dmdAll")) * 100;
						
						
						tempTaskList.add(new SimplifiedTask(allLines.get(i).get("date"), Integer.parseInt(allLines.get(i).get("version").split(":")[0]), tempScore)); 
						//Philippe
						tempTaskList2.add(new SimplifiedTask(allLines.get(i).get("date"), Integer.parseInt(allLines.get(i).get("version").split(":")[0]), tempScore2)); 					
					}
				}
	
			
		}catch (IOException E){
			E.printStackTrace();
		}
		
		taskList.add(tempTaskList);
		taskList.add(tempTaskList2);
		
		return taskList;		
	}
	
	
	
	
	private static boolean correctData(ArrayList<HashMap<String, String>> allLines, int i){
		if(! allLines.get(i).get("spgRt").equals("NaN") &&  ! allLines.get(i).get("spgRt").equals("0.0") && 
		   ! allLines.get(i).get("smgRt").equals("NaN") &&  ! allLines.get(i).get("smgRt").equals("0.0") &&
		   ! allLines.get(i).get("dmgRt").equals("NaN") &&  ! allLines.get(i).get("dmgRt").equals("0.0") &&
		   ! allLines.get(i).get("spdRt").equals("NaN") &&  ! allLines.get(i).get("spdRt").equals("0.0") &&
		   ! allLines.get(i).get("smdRt").equals("NaN") &&  ! allLines.get(i).get("smdRt").equals("0.0") &&
		   ! allLines.get(i).get("dmdRt").equals("NaN") &&  ! allLines.get(i).get("dmdRt").equals("0.0")){
			return true;
		}
		return false;		
	}
}

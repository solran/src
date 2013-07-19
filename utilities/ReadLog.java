package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import overview.SimplifiedTask;

import core.Task;

public class ReadLog {

	private static int  nbColomn = 0;
	private static String  sChariot = System.getProperty("line.separator");
	private static String sTab = "\t";
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private static Date date = new Date();
	
	//permet de de v�rifier si une session n,a pas �t� �crite pr�c�demment
	public static int trouveSession (int ID, String location)
	{
		int session = 0;
		try
		{           
			FileReader fic = new FileReader(location + ID + ".txt");
			BufferedReader in = new BufferedReader(fic);
			String line;	String lineSep[];				
			// on saute la ligne de titre de colonne
			in.readLine();
			while (in.ready()) 
			{
				line = in.readLine();
				lineSep = line.split("\t");
                session = Integer.parseInt(lineSep[1].trim());
			}
		}
		// Y'a rien dans le catch pcq ca veut dire que c'est la 1ere session
		catch (IOException E){
			System.out.print("premi�re session");
			//log
			String s = "";
			String t = "";

			try
			{           
				FileWriter fic = new FileWriter("data/log_" + ID + ".txt", true);
				PrintWriter out = new PrintWriter(fic);
				s +="sujetId" + sTab; 
				s +="session" + sTab;
				s +="date" + sTab;
				s +="type" + sTab; 
				s +="completed" + sTab; 
				s +="version" + sTab;
				s += "qte" + sTab;
				s += "langue" + sTab;
				s += "tempsStim" + sTab;
				s += "tempsAnswer" + sTab;
				s += "tempsIsi" + sTab;
				s += "nBack" + sTab;	
				s += "typedeNBack" + sTab;
				s += "pourcentage d'essais mixtes" + sTab;	


				
				t += "spgRt" + sTab;
				t += "smgRt" + sTab;
				t += "dmgRt" + sTab;
				t += "spdRt" + sTab;
				t += "smdRt" + sTab;
				t += "dmdRt" + sTab;
				t += "spgError" + sTab;
				t += "smgError" + sTab;
				t += "dmgError" + sTab;
				t += "spdError" + sTab;
				t += "smdError" + sTab;
				t += "dmdError" + sTab;
				t += "spgLate" + sTab;
				t += "smgLate" + sTab;
				t += "dmgLate" + sTab;
				t += "spdLate" + sTab;
				t += "smdLate" + sTab;
				t += "dmdLate" + sTab;
				t += "spgAll" + sTab;
				t += "smgAll" + sTab;
				t += "dmgAll" + sTab;
				t += "spdAll" + sTab;
				t += "smdAll" + sTab;
				t += "dmdAll" + sTab;

			//	writing  + sTab + true + sTab + monTrial.getAttendu() + sTab + myKey + sTab + clock2Fleche +  sTab + ISIRTFleche + sTab + this.erreur, "statsForExcel");
				out.write(s + t + sChariot);
				
			    // Fermeture du fichier
			    out.close();
			}
			catch (IOException F)
			{
			   Utilities.msgErreur("Le programme a g�n�r� une erreur lors de l'�criture des donn�es !");
			}
			//donnees
			try
			{           
				FileWriter fic = new FileWriter("data/donnees_" + ID + ".txt", true);			
				PrintWriter out = new PrintWriter(fic);
				s += "bloc" + sTab;
				s += "trialLoc" + sTab;
				s += "trialUnique" + sTab;
				s += "trialAbsLoc" + sTab;
				s += "trialAbsUnique" + sTab;
				s += "SpgSpdSmDm" + sTab;
				s += "isMixed" + sTab;
				s += "isDouble" + sTab;
				s += "isLeft" + sTab;
				s += "stimName" + sTab;
				s += "isMatch" + sTab;
				s += "expectedKey" + sTab;
				s += "pressedKey" + sTab;
				s += "accuracy" + sTab;
				s += "rt" + sTab;
				s += "rtTotal" + sTab;
				s += "rtSync" + sTab;
				s += "stimDisplaySync" + sTab;
				s += "stimRemovedSync" + sTab;
				s += "barRang" + sTab;
				s += "percentiles" + sTab;
				s += "valeurGraph" + sTab;

			//	writing  + sTab + true + sTab + monTrial.getAttendu() + sTab + myKey + sTab + clock2Fleche +  sTab + ISIRTFleche + sTab + this.erreur, "statsForExcel");
				out.write(s + sChariot);
				
			    // Fermeture du fichier
			    out.close();
			}
			catch (IOException F)
			{
				Utilities.msgErreur("Le programme a généré une erreur lors de l'écriture des données !");
			}
		}
		return session;
	}
	
	public static boolean hasBeenTrained (int ID, String location, int howManyTime)
	{
		int timesTrained = 0;
		String type = "";
		String completed = "";
		try
		{           
			FileReader fic = new FileReader(location + ID + ".txt");
			BufferedReader in = new BufferedReader(fic);
			String line;	String lineSep[];				
			// on saute la ligne de titre de colonne
			in.readLine();
			while (in.ready()) 
			{
				line = in.readLine();
				lineSep = line.split("\t");
                type = lineSep[3].trim();
                completed = lineSep[4].trim();
                if (type.equals("training") && (completed.equals("true") || completed.equals("TRUE")))
                	timesTrained++;
			}
		}
		catch (IOException E){}
		if (howManyTime <= timesTrained)
			return true;
		else
			return false;
	}
	
	public static ArrayList readFullFile (Task task, String location)
	{
		ArrayList fullFile = new ArrayList();
		try
		{           
			FileReader fic = new FileReader(location + task.getSujetID() + ".txt");
			BufferedReader in = new BufferedReader(fic);
			String line;	String lineSep[];				
			// on saute la ligne de titre de colonne
			while (in.ready()) 
			{
				fullFile.add(in.readLine());
			}
		}
		catch (IOException E){
			System.out.print("Document inexistant");
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

			// ent�te
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
		
		try
		{           
			FileReader fic = new FileReader(location + ID + ".txt");
			BufferedReader in = new BufferedReader(fic);
			String[] header, splittedLine;

			
			// ent�te
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
						//Philippe
						tempTaskList.add(new SimplifiedTask(allLines.get(i).get("date"), Integer.parseInt(allLines.get(i).get("version").split(":")[0]), (Float.parseFloat(allLines.get(i).get("dmgTrial")) - Float.parseFloat(allLines.get(i).get("dmgAcc"))) / Float.parseFloat(allLines.get(i).get("dmgTrial")) * 100)); 
						//Philippe
						tempTaskList2.add(new SimplifiedTask(allLines.get(i).get("date"), Integer.parseInt(allLines.get(i).get("version").split(":")[0]), (Float.parseFloat(allLines.get(i).get("dmgTrial")) - Float.parseFloat(allLines.get(i).get("dmdAcc"))) / Float.parseFloat(allLines.get(i).get("dmdTrial")) * 100));
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

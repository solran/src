package utilities;
import graphic.AnimateBar;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import overview.SimplifiedTask;

import core.Main;
import core.Stimulus;
import core.Task;



public class WriteLog {
	
	public static String writeError = "Le programme a généré une erreur lors de l'écriture!";
	
	private static int  nbColomn = 0;
	private static String  sChariot = System.getProperty("line.separator");
	private static String sTab = "\t";
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private static Date date = new Date();
	//private static ArrayList fullFile = new ArrayList();

	public static void writing (Stimulus stim, Task task, String location)	//??? Renommer WriteData  ?
	{
		String s = "";
		
		//Écriture de l'ensemble des données dans la String s
		s += task.getSujetID() + sTab; 
		s += task.getSession() + sTab;
		s += dateFormat.format(date) + sTab;
		s += task.getType() + sTab; 
		s += "non-applicable"+ sTab; 
		s += Langue.translate(new String[] {"radioVersion",""+ task.getVersion()}) + sTab;
		s += task.getQte() + sTab; 
		s += task.getLangue() + sTab;
		s += task.getStimT()*100 + sTab;
		s += task.getAnswerT()*100 + sTab;
		s += task.getISI()*100 + sTab;
		s += task.getnBack() + sTab;
		s += task.getTypeNback() + sTab;
		s += task.getMixedPourc() + sTab;
		s += stim.getBloc().getBlocID() + sTab;
		s += stim.getStimulusLocCpt() + sTab;
		s += stim.getStimulusLocUniqueCpt() + sTab;
		s += stim.getStimulusAbsCpt() + sTab;
		s += stim.getStimulusAbsUniqueCpt() + sTab;
		s += stim.getSPG_SPD_SM_DM() + sTab;
		if (stim.getSPG_SPD_SM_DM()== "SPG" ||stim.getSPG_SPD_SM_DM()== "SPD" )
		{
			s += false + sTab;
			s += false + sTab;
		}
		else if (stim.getSPG_SPD_SM_DM()== "SM")
		{
			s += true + sTab;
			s += false + sTab;
		}
		else if (stim.getSPG_SPD_SM_DM()== "DM")
		{
			s += true + sTab;
			s += true + sTab;
		}
		s += stim.getIsLeft() + sTab;
		s += stim.getName() + sTab;
		s += stim.getMatch() + sTab;
		s += stim.getKey() + sTab;
		s += stim.getKeyPressed() + sTab;
		s += stim.getIsAcc() + sTab;
		s += stim.getRt() + sTab;
		s += stim.getRtt() + sTab;
		s += stim.getRtSync() + sTab;
		s += stim.getStimDisplaySync() + sTab;
		s += stim.getStimRemovedSync() + sTab;	
		s += stim.getBarRang() + sTab;	
		for (int i=0; i < stim.getPercentiles().length; i++)
		{
			s += stim.getPercentiles()[i] + " ";
		}
		s += sTab;
		s += stim.getWeight() + sTab;
		
		
		writeString(s + sChariot, location + task.getSujetID());
	}
	
	public static String firstCol;
	public static void writeMeans(Task task, String location){
		System.out.println("test");
		writeMeans(task, location, firstCol);
	}
	
	public static void writeMeans (Task task, String location, String firstColumns)
	{
		//ArrayList<String> fullFile = ReadLog.readFullFile (task, location);
		//String lastline =  (String) fullFile.get(fullFile.size()-1);
		firstCol = firstColumns;
		String[] aLastline = firstColumns.split(sTab);
			
		
		
		String s = "";
		for (int i = 0; i < nbColomn; i++)
		{
			s += aLastline[i] + sTab;
		}	
		//rt
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSPG), true, "left","good") + sTab; ;
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSM), true, "left","good") + sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsDM), true, "left","good")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSPD), true, "right","good")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSM), true, "right","good")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.otherBlocsDM), true, "right","good")+ sTab; 
		
		//acc
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSPG), false, "left","error")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSM), false, "left", "error")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsDM), false, "left", "error")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSPD), false, "right", "error")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSM), false, "right", "error")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.otherBlocsDM), false, "right", "error")+ sTab; 
		
		//retard
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSPG), false, "left", "late")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSM), false, "left", "late")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsDM), false, "left", "late")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSPD), false, "right", "late")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSM), false, "right", "late")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.otherBlocsDM), false, "right", "late")+ sTab;
		
		//total
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSPG), false, "left", "all")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSM), false, "left", "all")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsDM), false, "left", "all")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSPD), false, "right", "all")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.blocsSM), false, "right", "all")+ sTab; 
		s +=AnimateBar.getStimQteOrMean(Utilities.getallStim(task.otherBlocsDM), false, "right", "all")+ sTab;
		
		//fullFile.set(fullFile.size()-1, s);	//append s to file

		// Only for app_log
		Pattern p_log = Pattern.compile("^data/log_[0-9]+$");
		Matcher m_log = p_log.matcher(location);
		String tempS = "";
		
		if(m_log.find() == true){
			//tempS = AnimateBar.getStdDeviation(Utilities.getallStim(task.otherBlocsDM), "right", "all") + "";
			System.out.println(tempS);
		}	
		
		
		
		
		if(! Main.isApplet){
			if(first){
				writeString(s, location + task.getSujetID());
				first = false;
			}else
				writeString(s, location + task.getSujetID(), false, task);
		}else
			writeString(s, location + task.getSujetID());
	}
	
	// écriture du Log
	public static String writeLogFirstColumn (Task task, String location)
	{
		String s = "";
		nbColomn = 0;
		
		s += task.getSujetID() + sTab;  		nbColomn ++;
		s += task.getSession() + sTab;  		nbColomn ++;
		s += dateFormat.format(date) + sTab;  		nbColomn ++;
		s += task.getType() + sTab;   		nbColomn ++;
		s += task.getIsCompleted() + sTab; 	nbColomn ++;
		s += Langue.translate(new String[] {"radioVersion",""+ task.getVersion()}) + sTab;  		nbColomn ++;
		
		s += task.getQte() + sTab;   		nbColomn ++;
		s += task.getLangue() + sTab;  		nbColomn ++;
		s += task.getStimT()*100 + sTab;  		nbColomn ++;
		s += task.getAnswerT()*100 + sTab;  		nbColomn ++;
		s += task.getISI()*100 + sTab;  		nbColomn ++;
		s += task.getnBack() + sTab;  		nbColomn ++;
		s += task.getTypeNback() + sTab;	nbColomn ++;
		s += task.getMixedPourc() + sTab;	nbColomn ++;
		
		
		System.out.println("NbColomn: " + nbColomn);
		
		
		return s;
		//writeString(s + sChariot, location + task.getSujetID());
	}
	
	public static void writeTitle(String type, int ID){
		String s = "";
		String t = "";
		
		if(! Main.isApplet){
			if(type.equals("log")){
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
					s += "%mixed" + sTab;
					
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
					writeString(s + t + sChariot, "data/log_" + ID);
	
			}else if(type.equals("donnee")){
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
				    writeString(s + t, "data/donnees_" + ID);
				    //+ sChariot
			}
		}
	}
	
	
	public static void writeString (String s, String location)
	{
		writeString (s, location, true, null);
	}
	
	private static boolean first = true;
	
	public static void writeString (String s, String location, boolean Append, Task task)  //writingStuff
	{
		if(! Main.isApplet){
			if(Append){
				try
				{           
					FileWriter fic = new FileWriter(location + ".txt", true);
					PrintWriter out = new PrintWriter(fic);
					out.write(s);
					
				    // Fermeture du fichier
				    out.close();
				}
				catch (IOException E)
				{
					Utilities.msgErreur(writeError);
				}
			}else{
				
				ArrayList fullFile = ReadLog.readFullFile (task, location);
				String lastline =  (String) fullFile.get(fullFile.size()-1);
				String[] aLastline = lastline.split("\t");
				
				
				
				fullFile.set(fullFile.size()-1, s);
				
				try
				{   
					FileWriter fic = new FileWriter(location + ".txt", false);
					PrintWriter out = new PrintWriter(fic);
					for (int i = 0 ; i< fullFile.size(); i++)
					{
						out.write((String) fullFile.get(i) + sChariot);
					}
					
				    // Fermeture du fichier
				    out.close();
				}
				catch (IOException E){
					Utilities.msgErreur(writeError);
				}
				
			}
		}else if(Main.isApplet){
			
			// Détermine la location à utiliser (log, donnees ou console)
			s = "message=" + s;
			
			String output = "";
			
			Pattern p_log = Pattern.compile("^data/log_[0-9]+$");
			Pattern p_data = Pattern.compile("^data/donnees_[0-9]+$");
			Pattern p_console = Pattern.compile("^data/console_[0-9]+$");
			Pattern p_bloc = Pattern.compile("^data/bloc_[0-9]+$");
			
			
			Matcher m_log = p_log.matcher(location);
			Matcher m_data = p_data.matcher(location);
			Matcher m_console = p_console.matcher(location);
			Matcher m_bloc = p_bloc.matcher(location);
			
			
			if(m_log.find() == true){
				output = "app_log.php";
			}else if(m_data.find() == true){
				output = "app_data.php";
			}else if(m_console.find() == true){
				//output = "app_console.php";  //test
				output= "test.php";
			}else if(m_bloc.find() == true){
				output = "app_bloc.php";
				
			}else{
				System.out.println("Format incorrect de fichier de sortie");
			}
			
			System.out.println(s);
			

		    try {
	            // Send the request
		    	URL url = new URL("http://lesca.ca/test/php/" + output);
	            URLConnection conn = url.openConnection();
	            conn.setDoOutput(true);
	            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
	            
	            //write parameters
	            writer.write(s);
	            writer.flush();
	            
	            // Get the response
	            StringBuffer answer = new StringBuffer();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                answer.append(line);
	            }
	            writer.close();
	            reader.close();
	            
	            //Output the response
	            System.out.println("web response: " + answer.toString());
	            
	        } catch (MalformedURLException ex) {
	            ex.printStackTrace();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		}
	}
}

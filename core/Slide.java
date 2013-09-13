package core;
import java.awt.Color;
import java.util.HashMap;

import utilities.Langue;
import utilities.Utilities;


/*
 * --- Slide ---
 * Classe contenant tous les textes de chaque "slide"
 */
public class Slide {

	private String txtHeader = "", txtFooter = "";
	//private Stimulus myStimulus, myOtherStimulus;

	private Stimulus soloStimulus = null;
	private Stimulus soloOtherStimulus = null;
	private static Stimulus empty = null;
	
	private Stimulus[] myStimulus;
	private Stimulus[] myOtherStimulus;
	private static Stimulus[] empty2 = null;
	private String sync;


	private int myCardinal = 0;
	private boolean isStimulus = false; //a faire

	private char[] expectedKey = {' ', '\0'};
	
	private String slideName;

	//instruction portant sur les stims
	
	//instruction ne portant pas sur les stims
	public Slide(String mySlide){  
		this(mySlide,  empty, empty, empty2, empty2, false, 0, "" );
	}
	
	public Slide(String mySlide, String Sync){  
		this(mySlide,  empty, empty, empty2, empty2, false, 0, Sync);
	}
	
	//instruction portant sur les stims
	public Slide(String mySlide, Stimulus[] myStimulus, Stimulus[] myOtherStimulus)
	{  
		 this(mySlide, empty, empty,  myStimulus, myOtherStimulus, false, 0, "" );
	}
	
	//keydetailedinstrc

	public Slide(String mySlide, Stimulus[] myStimulus, Stimulus[] myOtherStimulus, int cardinal)
	{  
		 this(mySlide, empty, empty,  myStimulus, myOtherStimulus, false, cardinal, "" );
	}
	
	//stims
	public Slide(String mySlide, Stimulus mySoloStimulus, Stimulus mySoloOtherStimulus)
	{  
		 this(mySlide, mySoloStimulus, mySoloOtherStimulus,  new Stimulus[0],  new Stimulus[0], true, 0, "" );
	}

	
	//construction d'une slide d'instructions
	
	public Slide(String mySlide, Stimulus soloStimulus, Stimulus soloOtherStimulus, Stimulus[] myStimulus, Stimulus[] myOtherStimulus,  boolean isStimulus, int cardinal, String sync){  // boolean isLeft,
		
		this.slideName = mySlide;
		this.isStimulus = isStimulus;
		this.soloStimulus = soloStimulus;
		this.soloOtherStimulus = soloOtherStimulus;
		this.myStimulus = myStimulus;
		this.myOtherStimulus = myOtherStimulus;
		this.myCardinal = cardinal;
		this.sync = sync;
		
		//if (isFrancais){
			switch(mySlide){
			
				case "intro":
					if (this.sync == "IRM")
					{
						txtFooter = Langue.translate(new String[] {"intro", "IRMFooter"});			
						expectedKey[0] = '5';	
					}
					else 
					{
						txtFooter = Langue.translate(new String[] {"intro", "noIRMFooter"});			
						expectedKey[0] = ' ';	
					}
		
				break;
			
				case "keyGeneralExplanation":
				
					txtHeader = Langue.translate("keyGeneralExplanationHeader", Utilities.iniHashMap (new String[]{"stim1", "stim2"},  new String[]{writeArray(myStimulus, Langue.translate("or") , true), writeArray(myStimulus, Langue.translate("or"), false)}))  ;
					txtFooter = Langue.translate("keyGeneralExplanationFooter"); 					
					expectedKey[0] = ' ';
					
				break;
				
				//special case for GO/STOP
				case "keyGeneralExplanation2":	
					
					txtHeader = Langue.translate("keyGeneralExplanationHeader2", Utilities.iniHashMap (new String[]{"stim1", "stim2"},  new String[]{writeArray(myStimulus, Langue.translate("or") , true), writeArray(myStimulus, Langue.translate("or"), false)}))  ;
					txtFooter = Langue.translate("keyGeneralExplanationFooter"); 					
					expectedKey[0] = ' ';
					
				break;
				
				case "keyDetailedExplanation":
					if (myStimulus[cardinal].getIsLeft())
						txtHeader = Langue.translate("keyDetailedExplanationHeader", Utilities.iniHashMap (new String[]{"fullname", "key", "finger"},  new String[]{myStimulus[cardinal].getFullName(), myStimulus[cardinal].getKey().toUpperCase(), Langue.translate(new String[]{"finger",  ""+ (cardinal + (4 - myStimulus[cardinal].getStimulusLength()))})}))  ;
					else
						txtHeader = Langue.translate("keyDetailedExplanationHeader", Utilities.iniHashMap (new String[]{"fullname", "key", "finger"},  new String[]{myStimulus[cardinal].getFullName(), myStimulus[cardinal].getKey().toUpperCase(), Langue.translate(new String[]{"finger",  ""+ (4 - (cardinal+1))})}))  ;
					txtFooter = Langue.translate("keyDetailedExplanationFooter", Utilities.iniHashMap (new String[]{"key"},  new String[]{myStimulus[cardinal].getKey().toUpperCase()}))  ;
					expectedKey[0] = myStimulus[cardinal].getKey().charAt(0);
				break;
				
				case "keySimpleMixte":	
					txtHeader = Langue.translate("keyS&DMixteHeader", Utilities.iniHashMap (new String[]{"taskName", "link", "otherTaskName"},  new String[] {Langue.translate(new String[] {"tasksNames", ""+(Task.mainTask.getVersion()-1)}), Langue.translate(new String[] {"links", "|"}), Langue.translate(new String[] {"otherTasksNames", "" + (Task.mainTask.getVersion()-1)})}))  ;

					txtFooter = Langue.translate("keyS&DMixteFooter");
					expectedKey[0] = ' ';
				break;
				
				case "keyDoubleTask":	
					txtHeader = Langue.translate("keyS&DMixteHeader", Utilities.iniHashMap (new String[]{"taskName", "link", "otherTaskName"},  new String[] {Langue.translate(new String[] {"tasksNames", ""+(Task.mainTask.getVersion()-1)}), Langue.translate(new String[] {"links", "&"}),  Langue.translate(new String[] {"otherTasksNames", "" + (Task.mainTask.getVersion()-1)})}))  ;
					txtFooter = Langue.translate("keyS&DMixteFooter");
					expectedKey[0] = ' ';		
				break;
				
				case "pauseInstruction":
					txtHeader = Langue.translate(new String[] {"pause", "header"});			
					txtFooter = Langue.translate(new String[] {"pause", "footer"});
					expectedKey[0] = ' ';			
				break;
				
				case "goodbye":
					txtHeader = Langue.translate("goodbyeCenter");
					txtFooter = "";
					expectedKey[0] = ' ';
				break;
				
				case "allStimuli":
					txtHeader = Langue.translate("allStimuliHeader", Utilities.iniHashMap (new String[]{"stim1", "stim2"},  new String[]{writeArray(myStimulus, Langue.translate("or"), true), writeArray(myStimulus, Langue.translate("or"), false)}))  ;
					txtFooter = Langue.translate("allStimuliFooter");
					expectedKey[0] = ' ';			
				break;
				
				case "allStimuliNback":
					// Pour tâche Phillips
					if (Task.mainTask.getnBack() == 0 && Task.mainTask.getVersion() == 5)
					{
						if (myStimulus[0].getnBackType() == "matching" && myStimulus[0].getIsLeft() )
						{txtHeader = Langue.translate("nBackExplanationHeader0Back", Utilities.iniHashMap (new String[]{"key1", "key2"},  new String[]{Task.mainTask.getLeftKeys()[0].toUpperCase(), Task.mainTask.getLeftKeys()[1].toUpperCase()}))  ;}
						else if (myStimulus[0].getnBackType() == "matching" && !myStimulus[0].getIsLeft() )
						{txtHeader = Langue.translate("nBackExplanationHeader0Back", Utilities.iniHashMap (new String[]{"key1", "key2"},   new String[]{Task.mainTask.getRightKeys()[0].toUpperCase(), Task.mainTask.getRightKeys()[1].toUpperCase()}))  ;}
						else
						{txtHeader = Langue.translate("nBackExplanationHeader0Back", Utilities.iniHashMap (new String[]{"key1", "key2"},  new String[]{myStimulus[0].getKey().toUpperCase(), myStimulus[1].getKey().toUpperCase()}))  ;}
					}
					
					if (Task.mainTask.getnBack() == -1)
					{
						if(Task.mainTask.getnBackKey() == 1 && myStimulus[0].getIsLeft()){
							txtHeader = Langue.translate("nBackExplanationHeader2Back", Utilities.iniHashMap (new String[]{"key1"},   new String[]{Task.mainTask.getLeftKeys()[0].toUpperCase()}));
						}else if(Task.mainTask.getnBackKey() == 1 && !myStimulus[0].getIsLeft()){
							txtHeader = Langue.translate("nBackExplanationHeader2Back", Utilities.iniHashMap (new String[]{"key2"},   new String[]{Task.mainTask.getRightKeys()[0].toUpperCase()}));
						}else if (myStimulus[0].getnBackType() == "matching" && myStimulus[0].getIsLeft() )
						{txtHeader = Langue.translate("nBackExplanationHeader1Back", Utilities.iniHashMap (new String[]{"key1", "key2"},  new String[]{Task.mainTask.getLeftKeys()[0].toUpperCase(), Task.mainTask.getLeftKeys()[1].toUpperCase()}))  ;}
						else if (myStimulus[0].getnBackType() == "matching" && !myStimulus[0].getIsLeft() )
						{txtHeader = Langue.translate("nBackExplanationHeader1Back", Utilities.iniHashMap (new String[]{"key1", "key2"},   new String[]{Task.mainTask.getRightKeys()[0].toUpperCase(), Task.mainTask.getRightKeys()[1].toUpperCase()}))  ;}
						else
						{txtHeader = Langue.translate("nBackExplanationHeader1Back", Utilities.iniHashMap (new String[]{"key1", "key2"},  new String[]{myStimulus[0].getKey().toUpperCase(), myStimulus[1].getKey().toUpperCase()}))  ;}
					}
					else if (Task.mainTask.getnBack() == -2)
					{
						if(Task.mainTask.getnBackKey() == 1 && myStimulus[0].getIsLeft()){
							txtHeader = Langue.translate("nBackExplanationHeader2Back", Utilities.iniHashMap (new String[]{"key1"},   new String[]{Task.mainTask.getLeftKeys()[0].toUpperCase()}));
						}else if(Task.mainTask.getnBackKey() == 1 && !myStimulus[0].getIsLeft()){
							txtHeader = Langue.translate("nBackExplanationHeader2Back", Utilities.iniHashMap (new String[]{"key2"},   new String[]{Task.mainTask.getLeftKeys()[1].toUpperCase()}));
						}else if (myStimulus[0].getnBackType() == "matching" && myStimulus[0].getIsLeft() )
						{txtHeader = Langue.translate("nBackExplanationHeader2Back", Utilities.iniHashMap (new String[]{"key1", "key2"},  new String[]{Task.mainTask.getLeftKeys()[0].toUpperCase(), Task.mainTask.getLeftKeys()[1].toUpperCase()}))  ;}
						else if (myStimulus[0].getnBackType() == "matching" && !myStimulus[0].getIsLeft() )
						{txtHeader = Langue.translate("nBackExplanationHeader2Back", Utilities.iniHashMap (new String[]{"key1", "key2"},   new String[]{Task.mainTask.getRightKeys()[0].toUpperCase(), Task.mainTask.getRightKeys()[1].toUpperCase()}))  ;}
						else
						{txtHeader = Langue.translate("nBackExplanationHeader2Back", Utilities.iniHashMap (new String[]{"key1", "key2"},  new String[]{myStimulus[0].getKey().toUpperCase(), myStimulus[1].getKey().toUpperCase()}))  ;}
					}	
					else if (Task.mainTask.getnBack() == -3)
					{
						if(Task.mainTask.getnBackKey() == 1 && myStimulus[0].getIsLeft()){
							txtHeader = Langue.translate("nBackExplanationHeader2Back", Utilities.iniHashMap (new String[]{"key1"},   new String[]{Task.mainTask.getLeftKeys()[0].toUpperCase()}));
						}else if(Task.mainTask.getnBackKey() == 1 && !myStimulus[0].getIsLeft()){
							txtHeader = Langue.translate("nBackExplanationHeader2Back", Utilities.iniHashMap (new String[]{"key2"},   new String[]{Task.mainTask.getRightKeys()[0].toUpperCase()}));
						}else if (myStimulus[0].getnBackType() == "matching" && myStimulus[0].getIsLeft() )
						{txtHeader = Langue.translate("nBackExplanationHeader3Back", Utilities.iniHashMap (new String[]{"key1", "key2"},  new String[]{Task.mainTask.getLeftKeys()[0].toUpperCase(), Task.mainTask.getLeftKeys()[1].toUpperCase()}))  ;}
						else if (myStimulus[0].getnBackType() == "matching" && !myStimulus[0].getIsLeft() )
						{txtHeader = Langue.translate("nBackExplanationHeader3Back", Utilities.iniHashMap (new String[]{"key1", "key2"},   new String[]{Task.mainTask.getRightKeys()[0].toUpperCase(), Task.mainTask.getRightKeys()[1].toUpperCase()}))  ;}
						else
						{txtHeader = Langue.translate("nBackExplanationHeader3Back", Utilities.iniHashMap (new String[]{"key1", "key2"},  new String[]{myStimulus[0].getKey().toUpperCase(), myStimulus[1].getKey().toUpperCase()}))  ;}
					}				

					
					txtFooter = Langue.translate("allStimuliFooter");
					expectedKey[0] = ' ';			
				break;
				
				case "feedback":
					txtHeader = Langue.translate("feedbackHeader");					
					txtFooter = Langue.translate("feedbackFooter");
					expectedKey[0] = ' ';
				break;
				
				case "priorite":
					txtHeader = Langue.translate("prioriteHeader");					
					txtFooter = Langue.translate("prioriteFooter");
					expectedKey[0] = ' ';
				break;
				
				case "countdown":
					txtHeader = "";					
					txtFooter = "";
					expectedKey[0] = '#';
				break;

				case "stimulus":
					txtHeader = "" ;
					if (soloStimulus != null && (soloStimulus.getSPG_SPD_SM_DM() == "SPG" || soloStimulus.getSPG_SPD_SM_DM() == "SM" || soloStimulus.getSPG_SPD_SM_DM() == "DM"))
					{
						expectedKey[0] = soloStimulus.getKey().charAt(0);
					}
					if (soloOtherStimulus != null && (soloOtherStimulus.getSPG_SPD_SM_DM() == "SPD" || soloOtherStimulus.getSPG_SPD_SM_DM() == "DM" ))
					{
						expectedKey[1] = soloOtherStimulus.getKey().charAt(0);
					}
				break;
				
				case "pause":
					txtHeader = "";				
					txtFooter = "";
					expectedKey[0] = '#';			
				break;
				
				case "reminderExplanation":
					txtHeader = "";				
					txtFooter = "";
					expectedKey[0] = ' ';			
				break;
				
				case "nBackExplanation":
					if (Task.mainTask.getnBack() == -1)
						txtHeader = Langue.translate("nBackExplanationHeader1Back");				
					else if (Task.mainTask.getnBack() == -2)
						txtHeader = Langue.translate("nBackExplanationHeader2Back");	
					else if (Task.mainTask.getnBack() == -3)
						txtHeader = Langue.translate("nBackExplanationHeader3Back");
					
					
					System.out.println("nBack: " + Task.mainTask.getnBack());
					
					txtFooter = "";
					expectedKey[0] = ' ';	
				break;
				
				case "nBackAsterisk":	
					txtHeader = Langue.translate("nBackAsteriskHeader");					

					if (Task.mainTask.getnBack() == -1)
						txtFooter = Langue.translate("nBackAsteriskFooter1Back");
					else if (Task.mainTask.getnBack() == -2)
						txtFooter = Langue.translate("nBackAsteriskFooter2Back");
					else if (Task.mainTask.getnBack() == -3)
						txtFooter = Langue.translate("nBackAsteriskFooter2Back");

					expectedKey[0] = ' ';	
				break;
				
				case "preSpeedOverviewInstruction":					
					txtHeader = Langue.translate(new String[] {"overview", "preSpeedHeader"});			
					txtFooter = Langue.translate(new String[] {"overview", "speedFooter"});			
					expectedKey[0] = ' ';	
				break;
				
				case "accOverviewInstruction":					
					txtHeader = Langue.translate(new String[] {"overview", "accHeader"});			
					txtFooter = Langue.translate(new String[] {"overview", "accFooter"});			
					expectedKey[0] = ' ';	
				break;
				
				case "postSpeedOverviewInstruction":					
					txtHeader = Langue.translate(new String[] {"overview", "postSpeedHeader"});			
					txtFooter = Langue.translate(new String[] {"overview", "speedFooter"});			
					expectedKey[0] = ' ';	
				break;
				
				case "speedOverview":
					txtHeader = "";					
					txtFooter = "";
					expectedKey[0] = ' ';
				break;
				case "accOverview":
					txtHeader = "";					
					txtFooter = "";
					expectedKey[0] = ' ';
				break;
				
				case "nBackMatching":
					if (Task.mainTask.getnBack() == -1)
						txtHeader = Langue.translate("nBackExplanationHeader1Back");				
					else if (Task.mainTask.getnBack() == -2)
						txtHeader = Langue.translate("nBackExplanationHeader2Back");
					else if (Task.mainTask.getnBack() == -3)
						txtHeader = Langue.translate("nBackExplanationHeader3Back");
					txtFooter = "";
					expectedKey[0] = ' ';	
				break;
			}

	}
	
	
	

	private String writeArray(Stimulus[] el, String lastSeparator, boolean isFullName){
		String output = "";
		int arrayLength = el.length;
		
		for(int i = 0; i < arrayLength; i++){
			if(isFullName)
				output += el[i].getFullName();
			else
				output += el[i].getName();
			if(i == arrayLength - 2){
				output += " " + lastSeparator + " ";
			}else if (i < arrayLength - 1){
				output += ", ";
			}
		}
		return output;
	}


	
// --- Getter and Setter ---

	public String getTxtHeader() {
		return txtHeader;
	}
	
	public String getSync() {
		return sync;
	}
	
	public void setTxtHeader(String txtHeader) {
		this.txtHeader = txtHeader;
	}
	
	public String getTxtFooter() {
		return txtFooter;
	}
	public void setTxtFooter(String txtFooter) {
		this.txtFooter = txtFooter;
	}

	public char getExpectedKey(int i) {
		return expectedKey[i];
	}
	public void setExpectedKey(char[] expectedKey) {
		this.expectedKey = expectedKey ;
	}
	public void setExpectedKey(char expectedKey, int i) {
		this.expectedKey[i] = expectedKey;
	}
	
	public String getSlideName() {
		return slideName;
	}
	public void setSlideName(String slideName) {
		this.slideName = slideName;
	}

	public Stimulus getSoloStimulus() {
		return soloStimulus;
	}

	public void setSoloStimulus(Stimulus soloStimulus) {
		this.soloStimulus = soloStimulus;
	}
	public boolean isStimulus() {
		return isStimulus;
	}
	public void setStimulus(boolean isStimulus) {
		this.isStimulus = isStimulus;
	}

	public Stimulus[] getMyStimulus() {
		return myStimulus;
	}
	public void setMyStimulus(Stimulus[] myStimulus) {
		this.myStimulus = myStimulus;
	}

	public Stimulus[] getMyOtherStimulus() {
		return myOtherStimulus;
	}
	public void setMyOtherStimulus(Stimulus[] myOtherStimulus) {
		this.myOtherStimulus = myOtherStimulus;
	}
	public Stimulus getSoloOtherStimulus() {
		return soloOtherStimulus;
	}

	public void setSoloOtherStimulus(Stimulus soloOtherStimulus) {
		this.soloOtherStimulus = soloOtherStimulus;
	}

	public int getMyCardinal() {
		return myCardinal;
	}
}

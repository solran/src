package core;
import graphic.Animate;
import graphic.GraphicEngine;
import javax.sound.sampled.*;

import graphic.ImageBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

import utilities.Langue;
import utilities.ReadLog;
import utilities.Signal;
import utilities.SoundClip;
import utilities.WriteLog;


/*
 * --- Task ---
 * Classe contenant l'ordre des slides d'instructions ainsi que les noms des stimuli
 */

public class Task {

	public static Task mainTask;
	//variable pour le cas d'urgence (urgency)
	public static int onOff = 1;
	
	private boolean firstDm = true;
	private int slideCpt = 0; 
	
	private Font keyFont = new Font("Arial", Font.BOLD, 36);
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private Date date = new Date();
	private Stimulus[] myStimulus;
	private Stimulus[] myOtherStimulus;
	
	private int blocQte;
	private Slide mySlide;
	private int nbLeftDetailedExplanation = 0;
	private int nbRightDetailedExplanation = 0;
	private int nbSlide;
	
	private String myExpectedKeys = "";
	private String myOtherExpectedKeys = "";
	private final String MYNEARKEYS = "qwertgvcxzasdf";
	private final String MYOTHERNEARKEYS = "uiopkl;`,.é^¸<nm,.4567890+-*/";

	private static String[] NORMAL_LEFT_KEY  = {"a", "s", "d", "f"};
	private static String[] NORMAL_RIGHT_KEY = {"7", "8", "9", "+"};

	private static String[] NO_NUMPAD_RIGHT_KEY = {"k", "l", ";", "`"};
	
	private String leftReminder = "", rightReminder = "";
	
	//mettre une constante pour le dossier???   pas n�cessaire

	private Stimulus VIDE = new Stimulus();
	private boolean isThereSP;
	private boolean isThereSM;
	private boolean isThereDM;

	private boolean isMixed = true;
	
	static final String ENVIRONNEMENT = "images/Environnement/";
	
	//test
	private ImageBox background;
	
	private ImageBox helloString, offsetString, doNotAnswer, orString, andString, reminderExplanation1, reminderExplanation2, reminderExplanation3, urgency;

	private HashMap<String, ImageBox> myImgKeyboard = new HashMap<String, ImageBox> ();
	private HashMap<String, ImageBox> myImgKeys = new HashMap<String, ImageBox> ();
	private HashMap<String, ImageBox> myImgHands = new HashMap<String, ImageBox> ();
	private  HashMap<String, ImageBox[]> myImages = new HashMap<String, ImageBox[]> ();
	private  HashMap<String, ImageBox[]> mySmallImages = new HashMap<String, ImageBox[]> ();
    private  HashMap<String, ImageBox[]> mySmallerImages = new HashMap<String, ImageBox[]> ();
    private  HashMap<String, SoundClip> mySounds = new HashMap<String, SoundClip> ();
    
    //private ImageBox speed = new ImageBox(0, 0, 300, 243, ENVIRONNEMENT + "speedmeter.png", "speedometer");
    //private ImageBox feedback = new ImageBox(0, 0, 800, 299, ENVIRONNEMENT + "feedback1.png", "feedback");
    private ImageBox[] equal = new ImageBox[] { new ImageBox (0, 0, 50, 50, ENVIRONNEMENT + "equal.png", "equal1"), new ImageBox (0, 0, 50, 50, ENVIRONNEMENT + "equal.png", "equal2")};
    private ImageBox cross = new ImageBox(0, 0, 160, 160, ENVIRONNEMENT + "cross.png", "cross");
    private ImageBox good = new ImageBox(0, 0, 275, 275, ENVIRONNEMENT + "good.png", "good");
    private ImageBox pause = new ImageBox(0, 0, 160, 160, ENVIRONNEMENT + "pause.png", "pause");
    private ImageBox brain = new ImageBox(0, 0, 347, 347, ENVIRONNEMENT + "brain.png", "brain");
    public static double syncTime;
    
    private ImageBox[] blackSquareSmall = new ImageBox[120];
    private ImageBox[] blackSquareSmaller = new ImageBox[9];
    private ImageBox[] blackSquare = new ImageBox[9];
	
    private SoundClip errorSound = new SoundClip( "error.wav", "error");
    private SoundClip badSound = new SoundClip( "bad.wav", "bad");
	private SoundClip goodSound = new SoundClip("good.wav", "goodSound");
	
	//private SoundClip overviewSound = new SoundClip("overview.wav", "overviewSound");



	private ImageBox backProgressBar = new ImageBox(0, 0, 760, 31, ENVIRONNEMENT + "progress_bar_back.png", "progress_bar_back");
    
    private ImageBox progressBar = new ImageBox(Color.YELLOW, 0, 0, 1, 25, "progress_bar");
    
    private ImageBox frontProgressBar = new ImageBox(0, 0, 955, 70, ENVIRONNEMENT + "progress_bar_front.png", "progress_bar_front");
    private ImageBox circleProgressBar = new ImageBox(0, 0, 13, 12, ENVIRONNEMENT + "progress_bar_circle.png", "progress_bar_circle");
    
    private ImageBox overview = new ImageBox(0, 0, 400, 309, ENVIRONNEMENT + "overview.png", "overview");

	private ImageBox leftReminderString, rightReminderString;
    
    
	//private ArrayList<String> mySlides = new ArrayList<String>();
	public Bloc[] blocsSPG;
	public Bloc[] blocsSPD;
	public Bloc[] blocsSM;
	public Bloc[] blocsDM;
	public Bloc[] otherBlocsDM;
	
    private Slide[] slideStack = new Slide[500];				//hum!?... Mettre un arraylist???
	
    private int sujetID;
    private int session;
	private int version;
	private int qte;
	private String type;	
	private int nBack;
	private int nBackKey;
	private String typeNback = "na";
	private String langue;
	private boolean isTimeUnlock;
	private int ISI;
	private int stimT;
	private int answerT;
	private String format; 
	private int pauseT;
	private int mixedPourc;
	private String imagerie;
	private boolean isCompleted = false;
	private String[] name;
	private String[] fullName;
	private String[] leftKeys;
	private String[] name2;
	private String[] fullName2;
	private String[] rightKeys;

	
	
	private Presentation myGUI;
	
	private GraphicEngine ge;
	private boolean stimsAreSounds = false;


	public Task(HashMap<String, Integer> myParameters){
		
		mainTask = this;
		myGUI = new Presentation( this);
			
		
		
		this.sujetID = (myParameters.get("sujetID"));
		this.version = myParameters.get("version");
		

		if(this.version == 6 || this.version == 7)
			stimsAreSounds = true;

		
		
		this.qte = myParameters.get("qte") + 1 ;
		if (version == 5)
			this.qte = 9 ;

		this.mixedPourc = myParameters.get("%mixed");
		
		if (myParameters.get("type") == 1)
			this.type = "eval";		
		else if (myParameters.get("type") == 2)
			this.type = "training";
		
		if (myParameters.get("langue") == 1)
		{
			Langue.setTaskLangue("francais");
			this.langue = "francais";
		}
		else if (myParameters.get("langue") == 2)
		{
			Langue.setTaskLangue("francais");  //temporairement pcq tout les mots n'existe pas encore en anglais
			Langue.setTaskLangue("english");
			this.langue = "english";
		}
		
		Langue.setPriority("right");
		
		if (myParameters.get("essai") == 1)
		{
			Bloc.setSPLength(10);	Bloc.setSMLength(16);	Bloc.setDMLength(20);
		}
		else if (myParameters.get("essai") == 2)
		{
			Bloc.setSPLength(10);	Bloc.setSMLength(16);	Bloc.setDMLength(24);
		}
		else if (myParameters.get("essai") == 3)
		{
			Bloc.setSPLength(12);	Bloc.setSMLength(20);	Bloc.setDMLength(24);
		}
		else if (myParameters.get("essai") == 4)
		{
			Bloc.setSPLength(16);	Bloc.setSMLength(26);	Bloc.setDMLength(36);
		}
		else if (myParameters.get("essai") == 5)
		{
			Bloc.setSPLength(24);	Bloc.setSMLength(36);	Bloc.setDMLength(42);
		}
		else if (myParameters.get("essai") == 6)
		{
			Bloc.setSPLength(36);	Bloc.setSMLength(36);	Bloc.setDMLength(36);
		}
		else if (myParameters.get("essai") == 7)
		{
			Bloc.setSPLength(48);	Bloc.setSMLength(48);	Bloc.setDMLength(48);
		}
		
		//Harcodé pour la tâche de Phillips
		if(version == 5)
			Bloc.setSPLength(61);  // Pourquoi pas 62???
		
		


		if (myParameters.get("nBack") == 1)
			this.nBack = 0;
		else if (myParameters.get("nBack") == 2)
			this.nBack = -1;
		else if (myParameters.get("nBack") == 3)
			this.nBack = -2;
		else if (myParameters.get("nBack") == 4)
			this.nBack = -3;
		
		
	
		this.nBackKey = myParameters.get("nBackKey");
		
		
		if (myParameters.get("format") == 1)
			this.format = "normal";
		else if (myParameters.get("format") == 2)
			this.format = "noNumpad";
		
		//exception pour la tâche de Phillips en 0-back
		if (nBack<0 || version == 5)
		{
			if (myParameters.get("boxNback") == 1)
				this.typeNback = "noNBack";
			if (myParameters.get("boxNback") == 2)
			{
				this.typeNback = "matching";
				this.format = "noNumpad";
				Langue.setMatching(this.langue);
			}
			else if (myParameters.get("boxNback") == 3)
				this.typeNback = "retrival";
		}
		if (myParameters.get("withSP") == 1)
			this.isThereSP = true;
		else if (myParameters.get("withSP") == 0)
			this.isThereSP = false;
		if (myParameters.get("withSM") == 1)
			this.isThereSM = true;
		else if (myParameters.get("withSM") == 0)
			this.isThereSM = false;
		if (myParameters.get("withDM") == 1)
			this.isThereDM = true;
		else if (myParameters.get("withDM") == 0)
			this.isThereDM = false;
		
		if (myParameters.get("isTimeUnlock") == 0)
			this.isTimeUnlock = false;
		else 
			this.isTimeUnlock = true;

		// égal le nombre de tic (1 tic = 100 ms)
		if (myParameters.get("stimT") == 1)
			this.stimT = 0;
		else if (myParameters.get("stimT") == 2)
			this.stimT = 2;
		else if (myParameters.get("stimT") == 3)
			this.stimT = 5;
		else if (myParameters.get("stimT") == 4)
			this.stimT = 10;
		else if (myParameters.get("stimT") == 5)
			this.stimT = 15;
		else if (myParameters.get("stimT") == 6)
			this.stimT = 20;
		else if (myParameters.get("stimT") == 7)
			this.stimT = 24;
		else if (myParameters.get("stimT") == 8)
			this.stimT = 30;
		else if (myParameters.get("stimT") == 9)
			this.stimT = 35;
		else if (myParameters.get("stimT") == 10)
			this.stimT = 40;
		
		if (myParameters.get("answerT") == 1 & myParameters.get("stimT") == 1)
			this.answerT = this.stimT;
		else if (myParameters.get("answerT") == 2)
			this.answerT = 5 +this.stimT;
		else if (myParameters.get("answerT") == 3)
			this.answerT = 10 +this.stimT;
		else if (myParameters.get("answerT") == 4)
			this.answerT = 15 +this.stimT;
		else if (myParameters.get("answerT") == 5)
			this.answerT = 20 +this.stimT;
		else if (myParameters.get("answerT") == 6)
			this.answerT = 25 +this.stimT;
		else if (myParameters.get("answerT") == 7)
			this.answerT = 30 +this.stimT;
		else if (myParameters.get("answerT") == 8)
			this.answerT = 35 +this.stimT;
		
		if (myParameters.get("ISI") == 1)
			this.ISI = 5 +this.answerT;
		else if (myParameters.get("ISI") == 2)
			this.ISI = 10 +this.answerT;
		else if (myParameters.get("ISI") == 3)
			this.ISI = 15 +this.answerT;
		else if (myParameters.get("ISI") == 4)
			this.ISI = 20 +this.answerT;
		else if (myParameters.get("ISI") == 5)
			this.ISI = 25 +this.answerT;
		else if (myParameters.get("ISI") == 6)
			this.ISI = 30 +this.answerT;
		
		
		if (myParameters.get("isIO") == 1)
			this.imagerie = "aucune";
		else if (myParameters.get("isIO") == 2)
			this.imagerie = "IO";
		else if (myParameters.get("isIO") == 3)
			this.imagerie = "IRM";
		else if (myParameters.get("isIO") == 4)
			this.imagerie = "EEG";
		
		// égal le nombre de tic (1 tic = 30sec)
		this.pauseT = (myParameters.get("pauseT"))-1;
		
		// Instanciation of arrays
		name = new String[qte];
		fullName = new String[qte];
		leftKeys = new String[qte];
		
		name2 = new String[qte];
		fullName2 = new String[qte];
		rightKeys = new String[qte];

		// key assignation
		if(this.format == "noNumpad" || this.typeNback == "matching"){
			if (this.typeNback == "matching"){
				if(nBackKey == 1){
					leftKeys = new String[2];
					//rightKeys = new String[1];
					
					//leftKeys =
							
					
					leftKeys = Arrays.copyOfRange(NORMAL_LEFT_KEY, 4 - 3, 4);
					rightKeys = Arrays.copyOfRange(NO_NUMPAD_RIGHT_KEY, 0, 1);
					
					
				}else{
					leftKeys = new String[2];
					rightKeys = new String[2];
					leftKeys = Arrays.copyOfRange(NORMAL_LEFT_KEY, 4 - 2, 4);
					rightKeys = Arrays.copyOfRange(NO_NUMPAD_RIGHT_KEY, 0, 2);
				}
			}
			else
			{
				leftKeys = Arrays.copyOfRange(NORMAL_LEFT_KEY, 4 - qte, 4);
				rightKeys = Arrays.copyOfRange(NO_NUMPAD_RIGHT_KEY, 0, qte);
			}
		}else if(this.format == "normal"){
			leftKeys = Arrays.copyOfRange(NORMAL_LEFT_KEY, 4 - qte, 4);
			rightKeys = Arrays.copyOfRange(NORMAL_RIGHT_KEY, 0, qte);
		}
		
//-----------------------------------------------------------------------------------		

		setBackground(new ImageBox(0, 0, 900, 700, ENVIRONNEMENT + "background.png", "background"));

		helloString = new ImageBox(Color.BLACK, 48, Langue.translate(new String[] {"intro", "introCenter"}), 0, 0, 100, "hello");
		
		offsetString = new ImageBox(Color.WHITE, 24, Langue.translate("decalage"), 0, 0, 100, "offset");
		
		doNotAnswer = new ImageBox(Color.WHITE, 24, Langue.translate("doNotAnswer"), 0, 0, 100, "doNotAnswer");
		
		reminderExplanation1 = new ImageBox(new Color(255, 255, 7, 255), new Font("Arial", Font.BOLD, 16), 0, 0, 500, 500, Langue.translate("reminderExplanation1"), "reminderExplanation1");
		reminderExplanation2 = new ImageBox(new Color(255, 255, 7, 255), new Font("Arial", Font.BOLD, 16), 0, 0, 500, 500, Langue.translate("reminderExplanation2"), "reminderExplanation2");
		reminderExplanation3 = new ImageBox(new Color(255, 255, 7, 255), new Font("Arial", Font.BOLD, 16), 0, 0, 300, 500, Langue.translate("reminderExplanation3"), "reminderExplanation3");

		urgency = new ImageBox(Color.RED, new Font("Arial", Font.BOLD, 30), 0, 0, 650, 500, Langue.translate("urgency"), "urgency");
		
		andString = new ImageBox(Color.BLACK, 48, Langue.translate("and"), 0, 0, 90, "and");
		orString = new ImageBox(Color.BLACK, 48, Langue.translate("or"), 0, 0, 90, "or");
		
		
		//création unique de toute les clefs et claviers
		for (int i = 0; i < leftKeys.length; i++)
		{
			myImgKeys.put(leftKeys[i], new ImageBox(Color.BLACK, keyFont, 0, 0, 80, 66, ENVIRONNEMENT + "touche.png", "   " +leftKeys[i].toUpperCase(), "imgKeyLeft" + leftKeys[i]));
			myImgKeyboard.put(leftKeys[i], new ImageBox(0, 0, 750, 275, 826, 275, ENVIRONNEMENT + "clavier" + leftKeys[i] + ".png", "imgKeyBoardLeft" + leftKeys[i]));
			myImgHands.put(leftKeys[i], new ImageBox(0, 0,  200, 252, ENVIRONNEMENT + "mainG" + (leftKeys.length-i) + ".png", "imgKeyHandsLeft" + leftKeys[i]));

		}
		for (int i = 0; i < rightKeys.length; i++)
		{
			myImgKeys.put(rightKeys[i], new ImageBox(Color.BLACK, keyFont,0, 0, 80, 66, ENVIRONNEMENT + "touche.png", "   " +rightKeys[i].toUpperCase(), "imgKeyRight" + rightKeys[i]));
			myImgKeyboard.put(rightKeys[i], new ImageBox(0, 0, 750, 275, 826, 275, ENVIRONNEMENT + "clavier" + rightKeys[i] + ".png", "imgKeyBoardRight" + rightKeys[i]));
			myImgHands.put(rightKeys[i], new ImageBox(0, 0,  200, 252, ENVIRONNEMENT + "mainD" + (i+1) + ".png", "imgKeyHandsRight" + rightKeys[i]));
		}
		
		myImgHands.put("droite", new ImageBox(0, 0,  200, 252, ENVIRONNEMENT + "mainD.png", "droite"));
		myImgHands.put("gauche", new ImageBox(0, 0,  200, 252, ENVIRONNEMENT + "mainG.png", "gauche"));

		//Create the stimuli
		// j'ai utiliser Langue.stims.get("taskName1")[version -1 ].length parce que le nombre d'images devrait varier en m�me temps de le nombre de noms d'images que nous avons introduits
		int departGauche = Langue.stims.get("taskName1")[version -1 ].length-qte;
		System.arraycopy(Langue.stims.get("taskName1")[version -1 ], departGauche, name, 0, qte);
		System.arraycopy(Langue.stims.get("taskFullName1")[version - 1], departGauche, fullName, 0, qte);
		
		System.arraycopy(Langue.stims.get("taskName2")[version -1 ], 0, name2, 0, qte);
		System.arraycopy(Langue.stims.get("taskFullName2")[version - 1], 0, fullName2, 0, qte);
		
		myStimulus = new Stimulus[qte];
		myOtherStimulus = new Stimulus[qte];			
		
		//on fait toujours 4 carrés noir pour l'animation de n-back
		for(int i=0; i < 4; i++){
			blackSquare[i] = new ImageBox(Color.BLACK, 0, 0, 160, 160, "blackSquare" + i);
		}
		//pairage devrait faire effet ici!!!!

		for(int i=0; i < qte; i++){
			if(! stimsAreSounds){
				if (this.typeNback == "matching")
				{
					if(nBackKey == 1){
						myStimulus[i] = new Stimulus(name[i] , fullName[i], "n/a", version, true, i, qte, nBack , typeNback);
						myOtherStimulus[i] = new Stimulus(name2[i] , fullName2[i], "n/a", version, false, i, qte, nBack, typeNback);
						this.myExpectedKeys += leftKeys[0];		this.myExpectedKeys += rightKeys[0];

					
						
					}else{
						myStimulus[i] = new Stimulus(name[i] , fullName[i], "n/a", version, true, i, qte, nBack , typeNback);
						myOtherStimulus[i] = new Stimulus(name2[i] , fullName2[i], "n/a", version, false, i, qte, nBack, typeNback);
						this.myExpectedKeys += leftKeys[0];		this.myExpectedKeys += leftKeys[1];
						this.myOtherExpectedKeys+= rightKeys[0];		this.myOtherExpectedKeys+= rightKeys[1];
					}
				}
				else
				{
					myStimulus[i] = new Stimulus(name[i] , fullName[i], leftKeys[i], version, true, i, qte, nBack, typeNback);
					myOtherStimulus[i] = new Stimulus(name2[i] , fullName2[i], rightKeys[i], version, false, i, qte, nBack, typeNback);
					this.myExpectedKeys += leftKeys[i];
					this.myOtherExpectedKeys+= rightKeys[i];
				}
			}else if(stimsAreSounds){
				
				System.out.println("Name: " + name[i] + " fullName: " + fullName[i]);
				
				
				if (this.typeNback == "matching")
				{
					myStimulus[i] = new Stimulus(name[i] , fullName[i], "n/a", version, true, i, qte, nBack , typeNback);
					myOtherStimulus[i] = new Stimulus(name2[i] , fullName2[i], "n/a", version, false, i, qte, nBack, typeNback);
					
					this.myExpectedKeys += leftKeys[0];		this.myExpectedKeys += leftKeys[1];
					this.myOtherExpectedKeys+= rightKeys[0];		this.myOtherExpectedKeys+= rightKeys[1];
				}
				else
				{
					myStimulus[i] = new Stimulus(name[i] , fullName[i], leftKeys[i], version, true, i, qte, nBack, typeNback);
					myOtherStimulus[i] = new Stimulus(name2[i] , fullName2[i], rightKeys[i], version, false, i, qte, nBack, typeNback);
					
					this.myExpectedKeys += leftKeys[i];
					this.myOtherExpectedKeys+= rightKeys[i];
				}
				
				if(this.version == 6){
					if(i == 0){
						mySounds.put(name[i], new SoundClip("6/A/left.wav", "left"));
						mySounds.put(name2[i], new SoundClip("6/B/go.wav", "go"));
					}else if(i == 1){
						mySounds.put(name[i], new SoundClip("6/A/right.wav", "right"));
						mySounds.put(name2[i], new SoundClip("6/B/stop.wav", "stop"));
					}
				}else if(this.version == 7){
					if(i == 0){
						mySounds.put(name[i], new SoundClip("7/A/400.wav", "400"));
						mySounds.put(name2[i], new SoundClip("7/B/i.wav", "i"));
					}else if(i == 1){
						mySounds.put(name[i], new SoundClip("7/A/1000.wav", "1000"));
						mySounds.put(name2[i], new SoundClip("7/B/o.wav", "o"));
					}else if(i == 2){
						mySounds.put(name[i], new SoundClip("7/A/700.wav", "700"));
						mySounds.put(name2[i], new SoundClip("7/B/a.wav", "a"));
					}
				}
			}
			
			//création unique de toute les images
			if(!stimsAreSounds){
				myImages.put(name[i], new ImageBox[] {new ImageBox(0, 0, 160,160,  "images/" + version + "/A/" + (i + departGauche + 1) + "a.png", "images1" + name[i]), new ImageBox(0, 0, 160,160, "images/" + version + "/A/" + (i + departGauche + 1) + "b.png", "images2" + name[i]), new ImageBox(0, 0, 160,160, "images/" + version + "/A/" + (i + departGauche + 1) + "c.png", "images3" + name[i])});
				mySmallImages.put(name[i], new ImageBox[] {new ImageBox(0, 0, 90, 90,160, 160,  "images/" + version + "/A/" + (i + departGauche +1) + "a.png", "smallImages1" + name[i]), new ImageBox(0, 0, 90, 90,160, 160,  "images/" + version + "/A/" + (i + departGauche +1) + "b.png", "smallImages2" + name[i]), new ImageBox(0, 0, 90, 90,160, 160, "images/" + version + "/A/" + (i + departGauche +1) + "c.png", "smallImages3" + name[i])});
				mySmallerImages.put(name[i], new ImageBox[] {new ImageBox(0, 0, 70, 70, 160, 160,   "images/" + version + "/A/" + (i + departGauche +1) + "a.png", "smallerImages1" + name[i]), new ImageBox(0, 0, 70, 70, 160, 160,  "images/" + version + "/A/" + (i + departGauche +1) + "b.png", "smallerImages2" + name[i]), new ImageBox(0, 0, 70, 70, 160, 160,  "images/" + version + "/A/" + (i + departGauche +1) + "c.png", "smallerImages3" + name[i])});
				
				myImages.put(name2[i], new ImageBox[] {new ImageBox(0, 0, 160,160,  "images/" + version + "/B/" + (i + 1) + "a.png", "images1" + name2[i]), new ImageBox(0, 0, 160,160,  "images/" + version + "/B/" + (i + 1) + "b.png", "images2" + name2[i]), new ImageBox(0, 0, 160,160, "images/" + version + "/B/" + (i + 1) + "c.png", "images3" + name2[i])});
				mySmallImages.put(name2[i], new ImageBox[] {new ImageBox(0, 0, 90, 90,160, 160,  "images/" + version + "/B/" + (i + 1) + "a.png", "smallImages1" + name2[i]), new ImageBox(0, 0, 90, 90,160, 160,  "images/" + version + "/B/" + (i + 1) + "b.png", "smallImages2" + name2[i]), new ImageBox(0, 0, 90, 90,160, 160, "images/" + version + "/B/" + (i + 1) + "c.png", "smallImages3" + name2[i])});
				mySmallerImages.put(name2[i], new ImageBox[] {new ImageBox(0, 0, 70, 70, 160, 160,   "images/" + version + "/B/" + (i + 1) + "a.png", "smallerImages1" + name2[i]), new ImageBox(0, 0, 70, 70, 160, 160,  "images/" + version + "/B/" + (i + 1) + "b.png", "smallerImages2" + name[i]), new ImageBox(0, 0, 70, 70, 160, 160,  "images/" + version + "/B/" + (i + 1) + "c.png", "smallerImages3" + name[i])});
				
			}else{
				myImages.put(name[i], new ImageBox[] {new ImageBox(0, 0, 160,160,  "images/" + version + "/A/" + (i + departGauche + 1) + "a.png", "images1" + name[i])});
				mySmallImages.put(name[i], new ImageBox[] {new ImageBox(0, 0, 90, 90,160, 160,  "images/" + version + "/A/" + (i + departGauche +1) + "a.png", "smallImages1" + name[i])});
				mySmallerImages.put(name[i], new ImageBox[] {new ImageBox(0, 0, 70, 70, 160, 160,   "images/" + version + "/A/" + (i + departGauche +1) + "a.png", "smallerImages1" + name[i])});
				
				myImages.put(name2[i], new ImageBox[] {new ImageBox(0, 0, 160,160,  "images/" + version + "/B/" + (i + 1) + "a.png", "images1" + name2[i])});
				mySmallImages.put(name2[i], new ImageBox[] {new ImageBox(0, 0, 90, 90,160, 160,  "images/" + version + "/B/" + (i + 1) + "a.png", "smallImages1" + name2[i])});
				mySmallerImages.put(name2[i], new ImageBox[] {new ImageBox(0, 0, 70, 70, 160, 160,   "images/" + version + "/B/" + (i + 1) + "a.png", "smallerImages1" + name2[i])});
			}
			
			blackSquareSmall[i * 3] = new ImageBox(Color.BLACK, 0, 0, 90, 90, "blackSquareSmall" + (i * 3));
			blackSquareSmall[i * 3 + 1] = new ImageBox(Color.BLACK, 0, 0, 90, 90, "blackSquareSmall" + (i * 3 + 1));
			blackSquareSmall[i * 3 + 2] = new ImageBox(Color.BLACK, 0, 0, 90, 90, "blackSquareSmall" + (i * 3 + 2));

			blackSquareSmaller[i] = new ImageBox(Color.BLACK, 0, 0, 60, 60, "blackSquareSmaller" + i);
		
		}
		
		ge = new GraphicEngine(myGUI.getGraphic(), myGUI, 100);	//80
		
		
//--------------------------------------------------------------------------------		
		
		
		// les rappels sont settés ici
		leftReminder = "";
		rightReminder = "";

		if (this.typeNback == "matching")
		{
			leftReminder = Langue.translate("matchingInstG");
			rightReminder = Langue.translate("matchingInstD");
			if (this.nBack == 0)
				leftReminder = Langue.translate("matching0back");
				rightReminder = Langue.translate("matching0back");
		}
		else
		{
			for(int i=0;i < myStimulus[0].getStimulusLength(); i++){
				leftReminder += myStimulus[i].getKey().toUpperCase() + "=" + myStimulus[i].getName() + " ";
				rightReminder += myOtherStimulus[i].getKey().toUpperCase() + "=" + myOtherStimulus[i].getName() + " ";
			}
		}
		
		
		int fontSize = 24;
		if(this.qte == 4)
			fontSize = 18;
		
		leftReminderString = new ImageBox(Color.WHITE, fontSize, leftReminder, 0, 0, 90, "leftReminder");
		rightReminderString = new ImageBox(Color.WHITE, fontSize, rightReminder, 0, 0, 90, "rightReminder");
		
		
		leftReminderString.setProperties(25, Main.getInstance().getBigPanel().getHeight() - 70, true);
		rightReminderString.setProperties(Main.getInstance().getBigPanel().getWidth() - (rightReminderString.getWidth() + 25), Main.getInstance().getBigPanel().getHeight() - 70, true);

			
			
			
		//les blocs sont setter ici (pour l'instant, la dur�e n'affecte que le qte de blocs DM
		int d;
		switch(myParameters.get("duree")){
			case 1: 	d=0;	break;	case 2:	d=2;	break;	case 3:	d=4;	break;	case 4:	d=6;	break;	case 5:	d=8;	break; case 6:	d=10;	break;
			case 24: d=40; default : d = 1; break;
		}
		
		
			blocsSPG = new Bloc[d+2];
			blocsSPD = new Bloc[d+2];
			blocsSM = new Bloc[d+2];
			blocsDM = new Bloc[d+2];
			otherBlocsDM = new Bloc[d+2];
			//if(version != 5){
				for (int i =0; i<d+2 ; i++)
				{
					blocsSPG[i] = new Bloc (myStimulus, "SPG", this.nBack, this.typeNback, "left", this.version);
					blocsSPD[i] = new Bloc (myOtherStimulus, "SPD", this.nBack, this.typeNback, "right", this.version);
					blocsSM[i] = new Bloc (myStimulus, myOtherStimulus, "SM", this.nBack, this.typeNback, this.version);
					blocsDM[i] =new Bloc (myStimulus, "DM", this.nBack, this.typeNback, "left", this.version);
					otherBlocsDM[i] =new Bloc (myOtherStimulus, "DM", this.nBack, this.typeNback, "right", this.version);
				}
			/*}else if(version == 5){
				for (int i =0; i< 2 ; i++)
					blocsSPG[i] = new Bloc (myStimulus, "SPG", this.nBack, this.typeNback, "left", this.version);
			}*/
			

			
		//les blocs et les instructions associées sont ordonnés ici

		slideStack[slideCpt++] = new Slide("intro", this.imagerie); 
		
		if (this.type == "training" && ReadLog.hasBeenTrained(this.sujetID, "data/log_", 2) && !typeNback.equals("matching"))
		{
			//Overview
			System.out.println("Overview !");
			slideStack[slideCpt++] = new Slide("preSpeedOverviewInstruction");
			slideStack[slideCpt++] = new Slide("speedOverview");
			
			slideStack[slideCpt++] = new Slide("accOverviewInstruction");
			slideStack[slideCpt++] = new Slide("accOverview");
			
		}
		if (isThereSP)
		{
			addInstructions(blocsSPG[0], myStimulus, myOtherStimulus);	//
			if (version != 5)
				addInstructions(blocsSPD[0], myOtherStimulus, myStimulus);	//
		}
		if (isThereSM)
		{
			addInstructions(blocsSM[0], myStimulus, myOtherStimulus);	//
		}
		
		if(version != 5){
			for (int i = 0; i <d; i++)
			{
				if (isThereDM)
					addInstructions(blocsDM[i], otherBlocsDM[i], myStimulus, myOtherStimulus);
				else if (isThereSM)
					addInstructions(blocsSM[i], myStimulus, myOtherStimulus);
				else if (isThereSP)
				{
					addInstructions(blocsSPG[i], myStimulus, myOtherStimulus);
					addInstructions(blocsSPD[i], myOtherStimulus, myStimulus);
				}
			}
		}
		
		if (isThereSM)
		{
			addInstructions(blocsSM[d+1], myStimulus, myOtherStimulus);
		}
		if (isThereSP)
		{
			if (version != 5)
			{
				addInstructions(blocsSPG[d+1], myStimulus, myOtherStimulus);
				addInstructions(blocsSPD[d+1], myOtherStimulus, myStimulus);	
			}else{
				
				addInstructions(blocsSPG[1], myStimulus, myOtherStimulus);
			}
			/*addInstructions(blocsSPG[d+1], myStimulus, myOtherStimulus);	
			if (version != 5)
				addInstructions(blocsSPD[d+1], myOtherStimulus, myStimulus);	*/
		}
		if (this.type == "training"&& ReadLog.hasBeenTrained(this.sujetID, "data/log_", 1) && !typeNback.equals("matching"))
		{
			slideStack[slideCpt++] = new Slide("postSpeedOverviewInstruction");
			slideStack[slideCpt++] = new Slide("speedOverview");
			
			slideStack[slideCpt++] = new Slide("accOverviewInstruction");
			slideStack[slideCpt++] = new Slide("accOverview");
		}
		slideStack[slideCpt++] = new Slide("goodbye");
		
		//note du d�part
		if (imagerie == "IO"){Signal.sendSignal("start", this.imagerie);}
		
		//note du départ
		//test temp
		Signal.sendSignal("start", this.imagerie);
		this.session = ReadLog.trouveSession(this.sujetID, "data/log_" ) + 1;
		String firstColumns = "";
		
		firstColumns = WriteLog.writeLogFirstColumn (this, "data/log_");
		WriteLog.writeMeans( this, "data/log_", firstColumns);

		
		
		//first slide
		this.nbSlide = slideCpt - 1;		
		slideCpt = 0;
		mySlide = slideStack[slideCpt];
		
		//Paint the "bonjour" slide
		myGUI.paintSlide();
		myGUI.repaint();
		Main.getInstance().addKeyListener(new urgency(this.imagerie, this.getUrgency()));
	}
	
	public Task getNextSlide(char key){
		
		
		if(slideCpt < nbSlide)
		{
			if(!(key == ' ' && mySlide.isStimulus()))
				mySlide = slideStack[++slideCpt];
		}
		else 
		{

		}
		return this;
	}
	
	public Slide lookNextSlide(){
		if(slideCpt < nbSlide)
		{
			return slideStack[slideCpt+1];
		}
		else 
		{
			return null;
		}
	}
	
	public Slide lookNextStim(){
		if(slideCpt < nbSlide)
		{
			int x = 1;
			while (!slideStack[slideCpt+x].isStimulus())
				x ++;
			return slideStack[slideCpt+x];
		}
		else 
		{
			return null;
		}
	}

	private static boolean first = true;
	
	public void addInstructions (Bloc myBloc, Bloc myOtherBloc, Stimulus[] mainStim, Stimulus[] secondStim)
	{	

		Random rgen = new Random();
		int random;
		 if ((this.imagerie == "IO"||this.imagerie == "EEG") & this.pauseT > 0)
		 {
				this.slideStack[this.slideCpt++] = new Slide("pauseInstruction");			
				this.slideStack[this.slideCpt++] = new Slide("countdown");
				this.slideStack[this.slideCpt++] = new Slide("pause");
		 }
		if (myBloc.getStimulusComplet()[0].getSPG_SPD_SM_DM() == "SPG" || myBloc.getStimulusComplet()[0].getSPG_SPD_SM_DM() == "SPD")
		{	
			if (this.typeNback == "matching"){
				if(! stimsAreSounds)
					this.slideStack[this.slideCpt++] = new Slide("allStimuli", mainStim, secondStim);
			}
			else
			{
				if(first || ! stimsAreSounds){
					this.slideStack[this.slideCpt++] = new Slide("keyGeneralExplanation", mainStim, secondStim);
					first = false;
				}else if (! first && stimsAreSounds){
					this.slideStack[this.slideCpt++] = new Slide("keyGeneralExplanation2", mainStim, secondStim);
				}
				
				
				for(int i = this.slideCpt; this.slideCpt < (i + name.length); this.slideCpt++){
					this.slideStack[this.slideCpt] = new Slide("keyDetailedExplanation", mainStim, secondStim, this.slideCpt - i);
				}
				if(! stimsAreSounds)
				this.slideStack[this.slideCpt++] = new Slide("allStimuli", mainStim, secondStim);
			}
			
			myBloc.setBlocID();
		}
		else if (myBloc.getStimulusComplet()[0].getSPG_SPD_SM_DM() == "SM"){
			slideStack[this.slideCpt++] = new Slide("keySimpleMixte", myStimulus, myOtherStimulus);
			for(int i = this.slideCpt; this.slideCpt < (i + name.length); this.slideCpt++)
				this.slideStack[this.slideCpt] = new Slide("keyDetailedExplanation", mainStim, secondStim, this.slideCpt - i);
			for(int i = this.slideCpt; this.slideCpt < (i + name.length); this.slideCpt++)
				this.slideStack[this.slideCpt] = new Slide("keyDetailedExplanation", secondStim, mainStim, this.slideCpt - i);
			
			myBloc.setBlocID();
		}
		else if (myBloc.getStimulusComplet()[0].getSPG_SPD_SM_DM() == "DM"){	
			if (!this.typeNback.equals("matching"))
			{
				slideStack[this.slideCpt++] = new Slide("keyDoubleTask", myStimulus, myOtherStimulus);			
				if(firstDm){
					for(int i = this.slideCpt; this.slideCpt < (i + name.length); this.slideCpt++)
						this.slideStack[this.slideCpt] = new Slide("keyDetailedExplanation", mainStim, secondStim, this.slideCpt - i);
					for(int i = this.slideCpt; this.slideCpt < (i + name.length); this.slideCpt++)
						this.slideStack[this.slideCpt] = new Slide("keyDetailedExplanation", secondStim, mainStim, this.slideCpt - i);
					firstDm = false;
				}	
			}
			Bloc.setBlocID(myBloc,myOtherBloc);
		}
		
		if (this.type == "training" && myBloc.getStimulusComplet()[0].getSPG_SPD_SM_DM() == "DM" && this.typeNback=="noNback")
		{
			this.slideStack[this.slideCpt++] = new Slide("feedback");
		}
		
			//this.slideStack[this.slideCpt++] = new Slide("nBackExplanation", mainStim, secondStim);
		if (this.typeNback == "matching" && ! stimsAreSounds)
		{
			
			this.slideStack[this.slideCpt++] = new Slide("allStimuliNback", mainStim, secondStim);
			if (myBloc.getStimulusComplet()[0].getSPG_SPD_SM_DM() == "DM")
			{
				this.slideStack[this.slideCpt++] = new Slide("allStimuliNback", secondStim, mainStim);
			}
 		}
		if (this.typeNback == "matching" || this.typeNback == "retrival")
		{
			this.slideStack[this.slideCpt++] = new Slide("nBackAsterisk", mainStim, secondStim);  
		}
		
		this.slideStack[slideCpt++] = new Slide("reminderExplanation");

		this.slideStack[this.slideCpt++] = new Slide("countdown");				

		
		
		for (int i = 0; i < myBloc.getStimulusComplet().length; i++, this.slideCpt++){
			if (myBloc.getStimulusComplet()[0].getSPG_SPD_SM_DM() == "SPG" || myBloc.getStimulusComplet()[0].getSPG_SPD_SM_DM() == "SM")
			{
				this.slideStack[this.slideCpt] = new Slide("stimulus", myBloc.getStimulusComplet()[i], VIDE);
				myBloc.getStimulusComplet()[i].setStimulusCpts();
			}
			if (myBloc.getStimulusComplet()[0].getSPG_SPD_SM_DM() == "SPD")
			{
				this.slideStack[this.slideCpt] = new Slide("stimulus", VIDE, myBloc.getStimulusComplet()[i]);
				myBloc.getStimulusComplet()[i].setStimulusCpts();
			}
			
			
			if (myBloc.getStimulusComplet()[0].getSPG_SPD_SM_DM() == "DM")
			{
				//divise en 2 le % mixed choisi par l'expérimentateur. Par example, pour 10%, on roule un dé-100 à chaque essai DM. Entre 0 à 5 = un essai SM à gauche, 5 à 10 pour une essai SM à droite, et 10 et + un double mixte.
				//shuffle
				if (isMixed)
				{

					myBloc.incrementNbSmDmStimuli();
					this.slideCpt--;
					/*
					 //test
					//faire de snotes ici pcq ca fait 50%
					random = rgen.nextInt(100);
					 if ( random <= Math.ceil(mixedPourc/2) )
			    	 {
							this.slideStack[this.slideCpt] = new Slide("stimulus", myBloc.getStimulusComplet()[i], VIDE);			    		 	//myBloc.getStimulusComplet()[i].setSPG_SPD_SM_DM("SM");
							this.slideStack[this.slideCpt].getSoloStimulus().setSPG_SPD_SM_DM("SM");
							this.slideStack[this.slideCpt].getSoloStimulus().setStimulusCpts();
			    	 }
			    	 else  if (random <= Math.ceil(mixedPourc/2)*2)
			    	 {
							this.slideStack[this.slideCpt] = new Slide("stimulus", myOtherBloc.getStimulusComplet()[i], VIDE);
							this.slideStack[this.slideCpt].getSoloStimulus().setSPG_SPD_SM_DM("SM");
							this.slideStack[this.slideCpt].getSoloStimulus().setStimulusCpts();
			    	 }
			    	 else
			    	 {
							slideStack[slideCpt] = new Slide("stimulus", myBloc.getStimulusComplet()[i], myOtherBloc.getStimulusComplet()[i]);		//essai ii deux fois? pour la DT
							Stimulus.setStimulusCpts(myBloc.getStimulusComplet()[i], myOtherBloc.getStimulusComplet()[i]);
			    	 }*/
				}
				else
				{
					slideStack[slideCpt] = new Slide("stimulus", myBloc.getStimulusComplet()[i], myOtherBloc.getStimulusComplet()[i]);		//essai ii deux fois? pour la DT
					Stimulus.setStimulusCpts(myBloc.getStimulusComplet()[i], myOtherBloc.getStimulusComplet()[i]);
				}  
			}
			
			
		}
		
		
		// EverydayImShuffling
		if(myBloc.getStimulusComplet()[0].getSPG_SPD_SM_DM() == "DM" && isMixed){
			
			// Calculate the number of each type of trial
			int nombre_total_essai = myBloc.getNbSmDmStimuli();
			int nombre_total_essai_SM_Gauche = nombre_total_essai * mixedPourc/200 ;
			int nombre_total_essai_SM_Droite = nombre_total_essai * mixedPourc/200 ;
			String[] allStimArray = new String[nombre_total_essai];
			
			//Create a list accordingly
			for(int i = 0; i < nombre_total_essai;i++){
				if(i < nombre_total_essai_SM_Gauche)
					allStimArray[i] = "SMG";
				else if(i < nombre_total_essai_SM_Gauche + nombre_total_essai_SM_Droite)
					allStimArray[i] = "SMD";
				else
					allStimArray[i] = "DM";
			}
			//Shuffle the list
			Collections.shuffle(Arrays.asList(allStimArray));
			
			//Assign the slide to slidestack accordingly to the list
			for(int i = 0; i < allStimArray.length; i++){
				if(allStimArray[i] == "SMG"){
					this.slideStack[this.slideCpt] = new Slide("stimulus", myBloc.getStimulusComplet()[i], VIDE);			    		 	//myBloc.getStimulusComplet()[i].setSPG_SPD_SM_DM("SM");
					this.slideStack[this.slideCpt].getSoloStimulus().setSPG_SPD_SM_DM("SM");
					this.slideStack[this.slideCpt++].getSoloStimulus().setStimulusCpts();
				}
				else if(allStimArray[i] == "SMD"){
					this.slideStack[this.slideCpt] = new Slide("stimulus", myOtherBloc.getStimulusComplet()[i], VIDE);
					this.slideStack[this.slideCpt].getSoloStimulus().setSPG_SPD_SM_DM("SM");
					this.slideStack[this.slideCpt++].getSoloStimulus().setStimulusCpts();
				}
				else if(allStimArray[i] == "DM"){
					this.slideStack[slideCpt++] = new Slide("stimulus", myBloc.getStimulusComplet()[i], myOtherBloc.getStimulusComplet()[i]);
					Stimulus.setStimulusCpts(myBloc.getStimulusComplet()[i], myOtherBloc.getStimulusComplet()[i]);
				}
			}
		}
	}
	
	
	public int getMixedPourc() {
		return mixedPourc;
	}

	public void addInstructions (Bloc myBloc, Stimulus[] mainStim, Stimulus[] secondStim)
	{	
		addInstructions (myBloc, null, mainStim, secondStim);
	}	
	
	public Task getPreviousSlide(char key){
		if(slideCpt > 0)
			if(!(key == '\b' && slideStack[slideCpt - 1].isStimulus()))
				mySlide = slideStack[--slideCpt];
		
		return this;
	}
	
	class urgency implements KeyListener {
		String imagerie;
		ImageBox urgency;
		public urgency (String imagerie, ImageBox urgency)
		{
			this.imagerie = imagerie;
			this.urgency = urgency;
		}	
		public void keyReleased(KeyEvent e) {

			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				System.out.println("before: " +onOff);
				if (onOff==1)
				{
					new Animate(new String[]{"fadein",""}, 300, Main.getInstance().getBigPanel().getWidth()/2 -urgency.getWidth()/2, Main.getInstance().getBigPanel().getHeight()/2-urgency.getHeight()/2, urgency, Main.getInstance().getMainPanel());

					if (imagerie == "IO"){Signal.sendSignal("urgency", this.imagerie);}
					if (imagerie == "EEG"){Signal.sendSignal("urgency", this.imagerie);}


					onOff=0;
				}
				else if (onOff == 0)
					onOff=1;
				System.out.println("after: " +onOff);

			}
		}
		public void keyPressed(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};

	
	
	

// ----- Getter and Setter -----	
	
	public Slide getMySlide() {
		return mySlide;
	}
	public void setMySlide(Slide mySlide) {
		this.mySlide = mySlide;
	}
	
	
	public int getNbLeftDetailedExplanation() {
		return nbLeftDetailedExplanation;
	}
	public void setNbLeftDetailedExplanation(int nbLeftDetailedExplanation) {
		this.nbLeftDetailedExplanation = nbLeftDetailedExplanation;
	}
	
	public int getNbRightDetailedExplanation() {
		return nbRightDetailedExplanation;
	}
	public void setNbRightDetailedExplanation(int nbRightDetailedExplanation) {
		this.nbRightDetailedExplanation = nbRightDetailedExplanation;
	}
	
	public Stimulus[] getStimulus() {
		return myStimulus;
	}
	public void setStimulus(Stimulus[] myStimulus) {
		this.myStimulus = myStimulus;
	}

	public Stimulus[] getOtherStimulus() {
		return myOtherStimulus;
	}
	public void setOtherStimulus(Stimulus[] myOtherStimulus) {
		this.myOtherStimulus = myOtherStimulus;
	}
	
	public String getLeftReminder() {
		return leftReminder;
	}
	public void setLeftReminder(String leftReminder) {
		this.leftReminder = leftReminder;
	}
	

	public String getRightReminder() {
		return rightReminder;
	}
	public void setRightReminder(String rightReminder) {
		this.rightReminder = rightReminder;
	}

	public String getType() {
		return type;
	}
	
	public Slide[] getSlideStack() {
		return slideStack;
	}

	public void setSlideStack(Slide[] slideStack) {
		this.slideStack = slideStack;
	}
	public String getMyExpectedKeys() {
		return myExpectedKeys;
	}

	public void setMyExpectedKeys(String myExpectedKeys) {
		this.myExpectedKeys = myExpectedKeys;
	}

	public String getMyOtherExpectedKeys() {
		return myOtherExpectedKeys;
	}

	public void setMyOtherExpectedKeys(String myOtherExpectedKeys) {
		this.myOtherExpectedKeys = myOtherExpectedKeys;
	}
	
	public String getMYNEARKEYS() {
		return MYNEARKEYS;
	}

	public String getMYOTHERNEARKEYS() {
		return MYOTHERNEARKEYS;
	}

    public HashMap<String, ImageBox[]> getMyImages() {
    	return myImages;
	}
    
    public HashMap<String, SoundClip> getMySounds() {
    	return mySounds;
	}

	public HashMap<String, ImageBox[]> getMySmallImages() {
		return mySmallImages;
	}

	public HashMap<String, ImageBox[]> getMySmallerImages() {
		return mySmallerImages;
	}
	
	public HashMap<String, ImageBox> getMyImgKeyboard() {
		return myImgKeyboard;
	}
	public HashMap<String, ImageBox> getMyImgKeys() {
		return myImgKeys;
	}

	public HashMap<String, ImageBox> getMyImgHands() {
		return myImgHands;
	}	
    public static String getEnvironnement() {
		return ENVIRONNEMENT;
	}
    
    public Presentation getMyGUI() {
		return myGUI;
	}
	public void setMyGUI(Presentation myGUI) {
		this.myGUI = myGUI;
	}

	
	
	public ImageBox[] getBlackSquareSmall() {
		return blackSquareSmall;
	}
	public void setBlackSquareSmall(ImageBox[] blackSquareSmall) {
		this.blackSquareSmall = blackSquareSmall;
	}

	public ImageBox[] getBlackSquareSmaller() {
		return blackSquareSmaller;
	}
	public void setBlackSquareSmaller(ImageBox[] blackSquareSmaller) {
		this.blackSquareSmaller = blackSquareSmaller;
	}

	public ImageBox[] getBlackSquare() {
		return blackSquare;
	}
	public void setBlackSquare(ImageBox[] blackSquare) {
		this.blackSquare = blackSquare;
	}

	public ImageBox getBackground() {
		return background;
	}

	public void setBackground(ImageBox background) {
		this.background = background;
	}

	public ImageBox getHelloString() {
		return helloString;
	}
	public void setHelloString(ImageBox helloString) {
		this.helloString = helloString;
	}

	public ImageBox getOffsetString() {
		return offsetString;
	}
	public void setOffsetString(ImageBox offsetString) {
		this.offsetString = offsetString;
	}

	public ImageBox getOrString() {
		return orString;
	}
	
	public void setOrString(ImageBox orString) {
		this.orString = orString;
	}

	public ImageBox getAndString() {
		return andString;
	}

	public void setAndString(ImageBox andString) {
		this.andString = andString;
	}

	public int getSujetID() {
		return sujetID;
	}
	
	public int getSession() {
		return session;
	}
	
	public int getVersion() {
		return version;
	}
	/*
	public  ImageBox getSpeed() {
		return speed;
	}*/

	public ImageBox getGood() {
		return good;
	}
	
	public String getLangue() {
		return langue;
	}

	/*public  ImageBox getFeedback() {
		return feedback;
	}*/
	
	public  ImageBox getCross() {
		return cross;
	}
	
	public  ImageBox[] getEqual() {
		return equal;
	}
	
	public GraphicEngine getGraphicEngine() {
		return ge;
	}
	public void setGraphicEngine(GraphicEngine ge) {
		this.ge = ge;
	}
	public boolean isTimeUnlock() {
		return isTimeUnlock;
	}

	public int getISI() {
		return ISI;
	}

	public int getStimT() {
		return stimT;
	}

	public int getAnswerT() {
		return answerT;
	}
	
	public String getFormat() {
		return format;
	}

	
	
	public ImageBox getBackProgressBar() {
		return backProgressBar;
	}
	public void setBackProgressBar(ImageBox backProgressBar) {
		this.backProgressBar = backProgressBar;
	}

	public ImageBox getProgressBar() {
		return progressBar;
	}
	public void setProgressBar(ImageBox progressBar) {
		this.progressBar = progressBar;
	}

	public ImageBox getFrontProgressBar() {
		return frontProgressBar;
	}
	public void setFrontProgressBar(ImageBox frontProgressBar) {
		this.frontProgressBar = frontProgressBar;
	}

	public ImageBox getCircleProgressBar() {
		return circleProgressBar;
	}
	public void setCircleProgressBar(ImageBox circleProgressBar) {
		this.circleProgressBar = circleProgressBar;
	}
	
	public int getQte() {
		return qte;
	}
	public void setQte(int qte) {
		this.qte = qte;
	}

	public ImageBox getLeftReminderString() {
		return leftReminderString;
	}
	public void setLeftReminderString(ImageBox leftReminderString) {
		this.leftReminderString = leftReminderString;
	}

	public ImageBox getRightReminderString() {
		return rightReminderString;
	}
	public void setRightReminderString(ImageBox rightReminderString) {
		this.rightReminderString = rightReminderString;
	}
	
	public int getnBack() {
		return nBack;
	}

	public String getTypeNback() {
		return this.typeNback;
	}
	
	public String getImagerie() {
		return imagerie;
	}
	
    public ImageBox getOverview() {
		return overview;
	}
	
	public ImageBox getPause() {
		return pause;
	}

	public ImageBox getBrain() {
		return brain;
	}
	
	public boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	public int getPauseT() {
		return pauseT;
	}
	
    public SoundClip getErrorSound() {
		return errorSound;
	}  
    public SoundClip getBadSound() {
		return badSound;
	}
	public SoundClip getGoodSound() {
		return goodSound;
	}

	public ImageBox getReminderExplanation1() {
		return reminderExplanation1;
	}

	public void setReminderExplanation1(ImageBox reminderExplanation1) {
		this.reminderExplanation1 = reminderExplanation1;
	}

	public ImageBox getReminderExplanation2() {
		return reminderExplanation2;
	}

	public void setReminderExplanation2(ImageBox reminderExplanation2) {
		this.reminderExplanation2 = reminderExplanation2;
	}
	
	public ImageBox getReminderExplanation3() {
		return reminderExplanation3;
	}

	public void setReminderExplanation3(ImageBox reminderExplanation3) {
		this.reminderExplanation3 = reminderExplanation3;
	}

	public ImageBox getDoNotAnswer() {
		return doNotAnswer;
	}	
	public ImageBox getUrgency() {
		return urgency;
	}
	
	public String[] getLeftKeys() {
		return leftKeys;
	}
	public String[] getRightKeys() {
		return rightKeys;
	}

	public boolean isStimsAreSounds() {
		return stimsAreSounds;
	}
	public void setStimsAreSounds(boolean stimsAreSounds) {
		this.stimsAreSounds = stimsAreSounds;
	}

	public int getnBackKey() {
		return nBackKey;
	}
	public void setnBackKey(int nBackKey) {
		this.nBackKey = nBackKey;
	}
  }


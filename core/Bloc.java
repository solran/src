package core;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;



public class Bloc {
	private static int nbBloc = 0;
	private static int SPLength;		
	private static int SMLength;		
	private static int DMLength;		
	private static int blocActuel=0;
	private Stimulus[] stimulusComplet;
	
	//Phil
	private int nbSmDmStimuli = 0;
	
	private int blocID;
	private int nBack;
	//private String typeNback = "";

	//constructeur de bloc SM
	public Bloc (Stimulus[] myStimulus, Stimulus[] myOtherStimulus, String sPG_SPD_SM_DM, int nBack, String typeNback, int version){
		Random rgen = new Random();
		//this.typeNback = typeNback;
		int compteur=0;
		int hasard;
		stimulusComplet = new Stimulus[SMLength];
		this.nBack = nBack;
		markSPG_SPD_SM_DM(myStimulus, sPG_SPD_SM_DM);			markSPG_SPD_SM_DM(myOtherStimulus, sPG_SPD_SM_DM);
		
		if (!typeNback.equals ("matching"))
		{
		     for (int i = 0; i<SMLength ; i+=myOtherStimulus.length )
		     {
		    	 for (int j = 0 ; j <myStimulus.length & (i+j)<stimulusComplet.length; j ++)
		    	 {
		    		 //System.out.println("ScL: " +stimulusComplet.length + " ij: " + (i+j));
		    		 stimulusComplet[i+j] = CopieSansPointeur(myStimulus[j]);
		    	 }
		    	 i+=myStimulus.length;
		    	 for (int j = 0 ; j <myOtherStimulus.length & (i+j)<stimulusComplet.length; j ++)
		    	 {
		    		// System.out.println("ScL: " +stimulusComplet.length + " ij: " + (i+j));
		    		 stimulusComplet[i+j] = CopieSansPointeur(myOtherStimulus[j]);
		    	 }
		     }
		     stimulusComplet = randomization(stimulusComplet);
		     stimulusComplet = Arrays.copyOfRange(stimulusComplet, 0, SMLength);
		}
		else
		{		
			//on place un nombre d'essai équivalent au nback en question
			for (;compteur + nBack < 0; compteur ++)
			{
				if (compteur < myStimulus.length)
					stimulusComplet[compteur] = CopieSansPointeur(myStimulus[compteur]);
				else 
					stimulusComplet[compteur] = CopieSansPointeur(myStimulus[0]);
			}
			//ensuite on complet avec des stimuli équivalent dans 40% des cas sinon on choisi un différent parmi les choix restants 
		    for (;compteur <stimulusComplet.length; compteur ++ )
		    {

		    	 //ici le matching arrive 40% du temps
		    	 if (rgen.nextInt(100) <40)
		    	 {
		    		 //Exception pour la tâche de Phillips où le stim est hardcodé 3
		    		 if(version == 5 && nBack == 0){
		    			 stimulusComplet[compteur] = CopieSansPointeur(myStimulus[2]); 
		    		 }else{
		    			stimulusComplet[compteur] = CopieSansPointeur(stimulusComplet[compteur+nBack]);
		    		 }
		    		 stimulusComplet[compteur].setMatch("isMatching") ;
		    	 }
		    	 else{
			    	 
			    	 //***//Phil
			    	 boolean first = true;
			    	 String lastStimulusName = "";
			    	 if(version == 5 && nBack == 0){
		    			 if(first == true)
		    				 first = false;
		    			 else
		    				 lastStimulusName = stimulusComplet[compteur - 1].getName();
		    				 
		    			 
	    				 do { hasard = randomStim(myStimulus.length); }
			    		 while (lastStimulusName == myStimulus[hasard].getName() || hasard == 2);
	    			 
		    			 first = false;
		    		 }else{
			    		 do {
			    			 rgen = new Random();
			    			 hasard = rgen.nextInt(myStimulus.length);
			    		 }
			    		 while (stimulusComplet[compteur+nBack].getName() == myStimulus[hasard].getName());
		    	 	 }
		    		 
		    		 if(version == 5 && nBack == 0){
		    			 stimulusComplet[compteur] = CopieSansPointeur(myStimulus[hasard]);
				 		 stimulusComplet[compteur].setMatch("notMatching") ;
		    			 stimulusComplet[compteur].setKey("f");
		    		 }else{
				 		 stimulusComplet[compteur] = CopieSansPointeur(myStimulus[hasard]);
			 			 stimulusComplet[compteur].setMatch("notMatching") ;
		    		 }
		    		 //***//
		    	 }
		    }
		}
	     setNBack(stimulusComplet, nBack, typeNback, "na");
	 }
	

	//constructeur de bloc SP et DM
	

	public Bloc (Stimulus[] myStimulus, String sPG_SPD_SM_DM, int nBack, String typeNback, String main, int version){
		
		Random rgen = new Random();
		int compteur=0;
		int hasard;
		this.nBack = nBack;
		stimulusComplet = new Stimulus[SPLength];
		if (sPG_SPD_SM_DM== "DM")
			stimulusComplet = new Stimulus[DMLength];

		markSPG_SPD_SM_DM(myStimulus, sPG_SPD_SM_DM);	
		
		//hardcodé - en test
		boolean fixedList = true;
		
		
		
		
		if(version == 5){
			String myList_0back = "1,7,3,4,2,3,5,3,3,6,3,7,3,9,4,2,3,3,6,3,5,8,3,5,8,3,3,2,4,3,6,1,9,3,3,8,6,3,8,7,3,4,3,1,3,5,3,3,8,1,3,6,4,3,2,3,7,3,9,3,8,7,3,1,2,9,3,5,3,4,3,6,3,7,9,8,2,4,5,3,1,3,3,9,7,6,4,3,3,2,3,1,5,3,8,3,9,3,6,5,3";
			String myList_1back = "2,6,5,5,3,2,7,6,6,9,9,1,8,8,8,9,2,2,2,7,8,8,5,8,8,5,5,1,1,4,9,9,6,3,3,5,5,3,4,9,8,8,9,9,1,4,8,5,5,5,7,7,7,8,2,2,9,9,1,1,4,4,3,3,7,7,2,1,1,4,4,5,9,3,3,3,2,2,5,5,3,3,7,2,4,4,3,6,6,2,7,6,6,7,7,6,6,6,8,1,6";
			String myList_2back = "3,8,3,8,7,2,7,5,7,9,7,6,7,2,8,4,8,2,7,2,9,2,8,3,9,3,9,6,9,2,9,6,9,7,5,1,5,1,7,1,6,5,9,5,7,5,2,5,2,3,5,2,4,2,8,3,4,3,2,4,8,4,7,4,1,6,1,6,7,3,7,3,4,1,4,8,5,8,9,8,4,8,5,1,5,6,1,6,8,6,2,9,6,8,6,3,6,1,3,4,1";
			String myList_3back = "3,7,8,3,7,4,2,6,2,8,9,5,8,2,5,7,2,3,7,4,9,6,4,1,5,4,6,5,6,2,4,6,2,7,6,7,7,4,7,2,3,9,2,8,1,2,1,5,6,1,3,6,7,3,6,2,8,5,7,8,5,8,4,3,8,9,3,1,2,3,1,9,6,1,9,4,1,9,2,6,9,4,6,5,4,9,1,4,5,7,4,8,7,8,5,3,9,5,1,9,8";
			String myList = "";
			
			if(nBack == 0){
				myList = myList_0back;
			}else if(nBack == 1){
				myList = myList_1back;
			}else if(nBack == 2){
				myList = myList_2back;
			}else{
				myList = myList_3back;
			}
			
			
			
			// 1 7 3 4
			String[] arrFixedList = myList.split(","); 
			
			stimulusComplet = new Stimulus[arrFixedList.length];
			
			
			 for (;compteur < stimulusComplet.length; compteur ++ )
			 {
				 stimulusComplet[compteur] = CopieSansPointeur(myStimulus[ (Integer.parseInt(arrFixedList[compteur]) - 1) ]);
				 
				 if(nBack == 0){
					 if(Integer.parseInt(arrFixedList[compteur]) - 1 == 2)
						 stimulusComplet[compteur].setMatch("isMatching") ;
					 else
						 stimulusComplet[compteur].setMatch("notMatching") ;
				 }else{
					 if(Math.abs(nBack) <= compteur){
						 if(stimulusComplet[compteur].getName() == stimulusComplet[compteur + nBack].getName())
							 stimulusComplet[compteur].setMatch("isMatching") ;
						 else
							 stimulusComplet[compteur].setMatch("notMatching") ;
					 }
				 }
		     }
		     
			
		}else if (!typeNback.equals ("matching"))
		{
		     for (int i = 0; i<stimulusComplet.length ; i+=myStimulus.length )
		     {
		    	// System.out.println("type : " + sPG_SPD_SM_DM + " StimC : " +stimulusComplet.length+ " i : " + i + " stimL: " + myStimulus.length);
			     if (stimulusComplet.length-i > myStimulus.length)
			    	 System.arraycopy(myStimulus, 0, stimulusComplet, i, myStimulus.length); 
			     else
			    	 System.arraycopy(myStimulus, 0, stimulusComplet, i, stimulusComplet.length-i); 

		     }
		     stimulusComplet = randomization(stimulusComplet);
		}
		else	// Matching
		{		
			//on place un nombre d'essai équivalent au nback en question
			for (;compteur + nBack < 0; compteur ++)
			{
				if (compteur<myStimulus.length)
					stimulusComplet[compteur] = CopieSansPointeur(myStimulus[compteur]); // Compteur ??? Erreur va faire un suite 1 2 3
				else 
					stimulusComplet[compteur] = CopieSansPointeur(myStimulus[0]);
			}
			

			//ensuite on complète avec des stimuli équivalent dans 40% des cas sinon on choisi un différent parmi les choix restants 
		    for (;compteur <stimulusComplet.length; compteur ++ )
		    {
		    	//ici le matching arrive 40% du temps
		    	 if (rgen.nextInt(100) <40)
		    	 {
		    		 //Exception pour la tâche de Phillips où le stim est hardcodé 3
		    		 //if(version == 5 && nBack == 0)
		    			 //stimulusComplet[compteur] = CopieSansPointeur(myStimulus[2]); 
		    		 //else
		    			stimulusComplet[compteur] = CopieSansPointeur(stimulusComplet[compteur+nBack]);
		    		 stimulusComplet[compteur].setMatch("isMatching") ;
		    	 }
		    	 else
		    	 {
		    		 
		    		 //***//Phil
		    		 boolean first = true;
			    	 String lastStimulusName = "";
			    	 
			    	 
			    	 /*if(version == 5 && nBack == 0){
		    			 if(first == true)
		    				 first = false;
		    			 else
		    				 lastStimulusName = stimulusComplet[compteur - 1].getName();
		    				 
		    			 
	    				 do { hasard = randomStim(myStimulus.length); }
			    		 while (lastStimulusName == myStimulus[hasard].getName() || hasard == 2);
	    			 
		    			 first = false;
		    		 }else{*/
			    		 do {
			    			 rgen = new Random();
			    			 hasard = rgen.nextInt(myStimulus.length);
			    		 }
			    		 while (stimulusComplet[compteur+nBack].getName() == myStimulus[hasard].getName());
		    	 	 //}
		    		 
		    		/* if(version == 5 && nBack == 0){
		    			 stimulusComplet[compteur] = CopieSansPointeur(myStimulus[hasard]);
				 		 stimulusComplet[compteur].setMatch("notMatching") ;
		    			 stimulusComplet[compteur].setKey("f");
		    		 }else{*/
				 		 stimulusComplet[compteur] = CopieSansPointeur(myStimulus[hasard]);
			 			 stimulusComplet[compteur].setMatch("notMatching") ;
		    		 //}
		    		 //***//
		    	 }
		    }
		}
	     setNBack(stimulusComplet, nBack, typeNback, main);
	     if (sPG_SPD_SM_DM== "DM")
	    	 stimulusComplet = Arrays.copyOfRange(stimulusComplet, 0, DMLength);
	     else
	    	 stimulusComplet = Arrays.copyOfRange(stimulusComplet, 0, SPLength);

	}
	
	//Phil
	private int randomStim(int upperLimit){
		Random rgen = new Random();
		rgen = new Random(); 
		int hasard = rgen.nextInt(upperLimit);
				
		return hasard;
	}
	
	

	public void markSPG_SPD_SM_DM (Stimulus[] input, String sPG_SPD_SM_DM){
		 for (int i = 0; i<input.length ; i++ )
	     {
			 input[i].setSPG_SPD_SM_DM(sPG_SPD_SM_DM);
	     }
	}
	
	public Stimulus[]  randomization (Stimulus[] input){
		
		 Random rgen = new Random();  // Random number generator
		 Stimulus[] output = new Stimulus [input.length];
		 Stimulus[] bufStimulusComplet = input;
		 int longueur = input.length;

		 
		for (int i=0; i<longueur; i++) {
			
			 //on choisit un place un stim aléatoire
		     int randomPosition = rgen.nextInt(bufStimulusComplet.length);
		     //System.out.println ("  i : " + i + "  rand: " + randomPosition + " pos: " + (bufStimulusComplet.length-1));
		     
	    	// System.out.println ("");
		    /* for (int j = 0 ; j< bufStimulusComplet.length; j++)
		     {	
			     System.out.println ("####" + bufStimulusComplet[j].getIDBidon());
		     }*/

		     output[i] = CopieSansPointeur(bufStimulusComplet[randomPosition]);
		     //input[i] = bufStimulusComplet[randomPosition];

		     //on retire ce stimuli de la s�lection
		     Stimulus[] bufTempo = new Stimulus[bufStimulusComplet.length-1];
		     //Avant le stim

		     
		     for (int j=0; j<randomPosition;j++)
			    {
				     //System.out.println ("  i : " + i + "  rand: " + randomPosition + " pos: " + (bufStimulusComplet.length-1));
			    	 bufTempo[j] = bufStimulusComplet[j];
			    	 //System.out.print ("pre" + j);
			    }
		     //apres le stim

		     for (int j=randomPosition+1; j<bufStimulusComplet.length;j++)
		     {
		    	 bufTempo[j-1] = bufStimulusComplet[j];
		    	 //System.out.print ("post" +j);

		     }

		     bufStimulusComplet=bufTempo;
		 }
		return output;
	}
	
	public void setNBack (Stimulus[] bloc, int nBack, String typeNback, String main){

		for (int i =bloc.length-1; i>=0; i--)
			
		{
			if (i+nBack >= 0)
			{
				if (typeNback == "matching")
				{
					//essai qui sont pareils
					if (bloc[i].getMatch().equals("isMatching"))
					{
						if (main == "left" )
							bloc[i].setKey("d");
						else if (main == "right" )
							bloc[i].setKey("k");
					}
					//essai pas pareils
					else if (bloc[i].getMatch().equals("notMatching"))
					{
						if (main == "left" )
							bloc[i].setKey("f");
						else if (main == "right" )
							bloc[i].setKey("l");
					}
				}
				else if (typeNback == "retrival")
				{
					bloc[i].setKey(bloc[i+nBack].getKey());
				}
			}
			else
			{
				bloc[i].setKey("!");
			}
		}
	}

	public Stimulus[] getStimulusComplet() {
		return stimulusComplet;
	}
	public Stimulus CopieSansPointeur (Stimulus input)
	{
		input.getName();
		input.getFullName();
		input.getKey();
		input.getVersion();
		input.getIsLeft();
		input.getCardinal();
		input.getStimulusLength(); 
		input.getnBack();
		Stimulus output = new Stimulus (input.getName(), input.getFullName(), input.getKey(), input.getVersion(), input.getIsLeft(), input.getCardinal(), input.getStimulusLength(), input.getnBack(), input.getnBackType());
		output.setSPG_SPD_SM_DM(input.getSPG_SPD_SM_DM());
		output.setBloc(this);
		return output;
	}

	public void setStimulusComplet(Stimulus[] stimulusComplet) {
		this.stimulusComplet = stimulusComplet;
	}

	public void setBlocID() {
		this.blocID = ++nbBloc;
		Stimulus.resetStimulusLocCpt();
	}
	
	public static void setBlocID (Bloc bloc, Bloc otherBloc)
	{
		int blocID  = ++nbBloc;
		bloc.blocID = blocID;		otherBloc.blocID = blocID;
		Stimulus.resetStimulusLocCpt();
	}
	
	public int getBlocID() {
		return blocID;
	}

	public int getnBack() {
		return nBack;
	}
	
	public static int getnbBloc() {
		return nbBloc;
	}
	
	public static int getBlocActuel() {
		return blocActuel;
	}

	public static void setBlocActuel(int blocActuel) {
		Bloc.blocActuel = blocActuel;
	}
	
	public static void setSPLength(int sPLength) {
		SPLength = sPLength;
	}

	public static void setSMLength(int sMLength) {
		SMLength = sMLength;
	}

	public static void setDMLength(int dMLength) {
		DMLength = dMLength;
	}
	/*public String getTypeNback() {
		return typeNback;
	}*/


	//Phil
	public int getNbSmDmStimuli() {
		return nbSmDmStimuli;
	}
	public void setNbSmDmStimuli(int nbSmDmStimuli) {
		this.nbSmDmStimuli = nbSmDmStimuli;
	}
	public void incrementNbSmDmStimuli() {
		nbSmDmStimuli++;
	}
}
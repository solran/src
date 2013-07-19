package core;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/*
 * --- Stimulus ---
 * Classe regroupant les propri�t�s des stimuli comme leurs images, leurs noms et leurs touches auxquels ils sont associ�s
 */
public class Stimulus {

	private static int compteurLoc = 1;
	private static int compteurAbs = 1;
	private static int compteurAbsUnique = 1;
	private static int compteurLocUnique = 1;
	
	// setter � la cr�ation du stim
	private String name;
	private String fullName;
	// pour les n-back qui match
	private String match = "na";
	private String key;
	private int version;
	private boolean isLeft;
	private int cardinal;
	private int stimulusLength;
	private int nBack;
	private String nBackType;
	private int barRang = 999;

	long rtt;

	//setter lorsque l'on cr�e le bloc (sauf le compteur ctp absolu qui est cr�� en m�me temps que la slide (ordre d'affichage)
	private int stimulusLocCpt;		private int stimulusAbsCpt;
	private int stimulusLocUniqueCpt;		private int stimulusAbsUniqueCpt;
	private String sPG_SPD_SM_DM;
	private Bloc bloc;

	//Setter � la r�ponse du stim
	private double rt;	//maj
	private double rtSync;
	private double stimDisplaySync = 0;
	private double stimRemovedSync;
	private boolean isAcc;
	private char keyPressed;
	private double[] percentiles = new double[]{0,0,0,0,0};
	private double weigth;
	//private double rtt;	
	private boolean isAnswered = false;
	
//Mettre folder (A et B) en commun avec isLeft ???
	public Stimulus(String myName, String myFullName, String myKey, int version, boolean isLeft, int cardinal, int stimulusLength, int nBack, String nBackType){
		this.nBackType= nBackType;
		this.name = myName;
		this.fullName = myFullName;
		this.key = myKey;
		this.version = version;
		this.isLeft= isLeft;
		this.cardinal = cardinal;
		this.stimulusLength = stimulusLength;
		this.nBack = nBack;
		this.match = "na";

	}
	
	public Stimulus(){
		this.name = "";
		this.fullName = "";
		this.key = "";
		this.version = 999;
		this.cardinal = 999;
		this.stimulusLength = 999;
		this.sPG_SPD_SM_DM = "";
		this.nBack = 999;
		this.match = "na";

	}


	//--- Getter and Setter ---	
	
	public Bloc getBloc() {
		return bloc;
	}

	public void setBloc(Bloc bloc) {
		this.bloc = bloc;
	}
	
	public double getRt() {
		return rt;
	}
	public void setRt(double rt) {
		this.rt = rt;
	}


	public boolean getIsAcc() {
		return isAcc;
	}
	public void setIsAcc(boolean isAcc) {
		this.isAcc = isAcc;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getMatch() {
		return match;
	}
	
	public void setMatch(String match) {
		this.match = match;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}


	public int getCardinal() {
		return cardinal;
	}
	public void setCardinal(int cardinal) {
		this.cardinal = cardinal;
	}


	public int getStimulusLength() {
		return stimulusLength;
	}
	public void setStimulusLength(int stimulusLength) {
		this.stimulusLength = stimulusLength;
	}

	public void setKeyPressed(char keyPressed) {
		this.keyPressed = keyPressed;
	}
	public long getRtt() {
		return rtt;
	}
	public void setRtt(long rtt) {
		this.rtt = rtt;
	}
	public void setAcc(boolean isAcc) {
		this.isAcc = isAcc;
	}
	public boolean getIsLeft() {
		return isLeft;
	}
	public void setIsLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}	
	public boolean getIsAnswered() {
		return isAnswered;
	}
	public void setIsAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	public char getKeyPressed() {
		return keyPressed;
	}

	public void setAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getSPG_SPD_SM_DM() {
		return sPG_SPD_SM_DM;
	}

	public void setSPG_SPD_SM_DM(String sPG_SPD_SM_DM) {
		this.sPG_SPD_SM_DM = sPG_SPD_SM_DM;
	}

	public int getStimulusLocCpt() {
		return stimulusLocCpt;
	}

	public int getStimulusAbsCpt() {
		return stimulusAbsCpt;
	}
	
	public int getStimulusLocUniqueCpt() {
		return stimulusLocUniqueCpt;
	}

	public int getStimulusAbsUniqueCpt() {
		return stimulusAbsUniqueCpt;
	}
	
	public int getnBack() {
		return this.nBack;
	}
	
	public double getRtSync() {
		return rtSync;
	}
	public void setRtSync(double rtSync) {
		this.rtSync = rtSync;
	}

	public double getStimDisplaySync() {
		return stimDisplaySync;
	}

	public void setStimDisplaySync(double stimDisplaySync) {
		this.stimDisplaySync = stimDisplaySync;
	}

	public double getStimRemovedSync() {
		return stimRemovedSync;
	}

	public void setStimRemovedSync(double stimRemovedSync) {
		this.stimRemovedSync = stimRemovedSync;
	}
	
	public int getBarRang() {
		
		return this.barRang;
	}
	
	public void setBarRang( int barRang) {
		this.barRang = barRang;
	}
	
	public static void resetStimulusLocCpt()
	{
		compteurLoc = 1;
		compteurLocUnique = 1;
	}
	public void setStimulusCpts()
	{
		this.stimulusLocCpt = compteurLoc++;
		this.stimulusAbsCpt = compteurAbs++;
		this.stimulusLocUniqueCpt = compteurLocUnique++;
		this.stimulusAbsUniqueCpt = compteurAbsUnique++;
	}
	
	public static void setStimulusCpts (Stimulus stim, Stimulus otherStim)
	{
		int loc = compteurLoc++;
		int abs = compteurAbs++;
		stim.stimulusLocCpt = loc;		otherStim.stimulusLocCpt = loc;
		stim.stimulusAbsCpt = abs;		otherStim.stimulusAbsCpt = abs;
		stim.stimulusLocUniqueCpt = compteurLocUnique++;		
		stim.stimulusAbsUniqueCpt = compteurAbsUnique++;		
		otherStim.stimulusLocUniqueCpt = compteurLocUnique++;
		otherStim.stimulusAbsUniqueCpt = compteurAbsUnique++;
	}
	
	public double[] getPercentiles() {
		return percentiles;
	}

	public void setPercentiles(double[] percentiles) {
		this.percentiles = percentiles;
	}

	public double getWeight() {
		return weigth;
	}

	public void setWeight(double weigth) {
		this.weigth = weigth;
	}
	public String getnBackType() {
		return nBackType;
	}
}
	
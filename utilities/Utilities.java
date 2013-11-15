package utilities;

import graphic.AnimateBar;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.Bloc;
import core.Stimulus;



public class Utilities {

	
	
	public static String implode(String[] ary, String delim) {
	    String out = "";
	    for(int i=0; i<ary.length; i++) {
	        if(i!=0) { out += delim; }
	        out += ary[i];
	    }
	    return out;
	}
	
	public static void msgErreur (String s){
		JOptionPane.showMessageDialog(null, s, "Erreur !", JOptionPane.ERROR_MESSAGE);
	}
	
	public static boolean in_array(DefaultListModel haystack, String needle) {
	    for(int i=0;i<haystack.size();i++) {
	        if(haystack.get(i).toString().equals(needle)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public static Stimulus[] getallStim(Bloc[] blocs) {
		if (blocs!= null)
		{
			Stimulus[] allStimulus = new Stimulus[getStimLength(blocs)];
			int i = 0;
			for (int i2 = 0; i2 < blocs.length; i2++)
			{
			     System.arraycopy(blocs[i2].getStimulusComplet(), 0, allStimulus, i, blocs[i2].getStimulusComplet().length); 
			     i+=blocs[i2].getStimulusComplet().length;
			}
			return allStimulus;
		}
		else return null;
	}
	
	
	public static Stimulus[] getallStim(Bloc[] blocs, String type) {
		if (blocs!= null)
		{
			Stimulus[] allStimulus = new Stimulus[getStimLength(blocs)];
			int i = 0;
			//todo
			// for each bloc
			for (int i2 = 0; i2 < blocs.length; i2++)
			{
				if(type.equals("SP")){
					System.arraycopy(blocs[i2].getStimulusComplet(), 0, allStimulus, i, blocs[i2].getStimulusComplet().length); 
					i+=blocs[i2].getStimulusComplet().length;
				}else{
					
					// for each simulus in the bloc
					for(int i3 = 0; i3 < blocs[i2].getStimulusComplet().length; i3++){
						if(type.equals("SM")){
							if(blocs[i2].getStimulusComplet()[i3].getSPG_SPD_SM_DM().equals("SM")){
								allStimulus[i] = blocs[i2].getStimulusComplet()[i3];
								i++;
							}
						}else if(type.equals("DM")){
							if(blocs[i2].getStimulusComplet()[i3].getSPG_SPD_SM_DM().equals("DM")){
								allStimulus[i] = blocs[i2].getStimulusComplet()[i3];
								i++;
							}
						}
					} 
				}
			    
			    
			}
			return allStimulus;
		}
		else return null;
	}
	
	
	
	
	
	public static int getStimLength(Bloc[] bloc)
	{
		int grandLength = 0;
		for (int i = 0; i<bloc.length; i++)
		{
			grandLength += bloc[i].getStimulusComplet().length;
		}
		return grandLength;
	}
	
	public static void wait (int n){
        long t0,t1;
        t0=System.currentTimeMillis();
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0<1000);
	}
	
	public static void  pushInArray (Stimulus [] objects, Stimulus object){
		//Stimulus[] buff = new Stimulus[objects.length];
		for (int i = 0 ; i< objects.length-1; i ++)
		{
			objects[i] = objects[i + 1];
		}
	    //System.arraycopy(objects, 1, buff, 0, buff.length-1); 
		objects[objects.length-1] = object;
	}
	
	public static void  iniHashMap (Object hash, String tag1, String[] tag2, Object[] values){
		HashMap temp = new HashMap();
		for (int i=0; i<tag2.length; i++)
		{
			temp.put(tag2[i], values[i]);
		}
		((HashMap) hash).put(tag1, temp);
	}
	
	public static HashMap  iniHashMap  (String[] tag, String[] values){
		HashMap temp = new HashMap();
		for (int i=0; i<tag.length; i++)
		{
			temp.put(tag[i], values[i]);
		}
		return temp;
	}
	
	public static HashMap  iniHashMap  (String tag, String values){
		HashMap temp = new HashMap();
			temp.put(tag, values);
		return temp;
	}

	public static ArrayList<Double> getAllStims (Bloc[] allBlocs, boolean isLeft)
	{
		Stimulus[] allStim;
		ArrayList<Double> allRts = new ArrayList<Double>();
		for (int x = 0; x < allBlocs.length; x++)
		{
			allStim = allBlocs[x].getStimulusComplet();
			for (int i = 0; i < allStim.length; i++)
			{
				if (allStim[i]!= null && allStim[i].getIsAcc()== true && 5000>allStim[i].getRt() && allStim[i].getRt()>200 & allStim[i].getIsLeft()==isLeft)
				{
					allRts.add(allStim[i].getRt());
				}
			}
			
		}
		return allRts;
	}

	// Return all the reaction time (for each trial) separated left/right for the bloc sended in parameter
	public static ArrayList<ArrayList<Double>> getAllRtOfBloc (Bloc currentBloc)
	{
		
		Stimulus[] allStim;
		ArrayList<Double> allLeftRts_sp = new ArrayList<Double>();
		ArrayList<Double> allRightRts_sp = new ArrayList<Double>();
		ArrayList<Double> allLeftRts_sm = new ArrayList<Double>();
		ArrayList<Double> allRightRts_sm = new ArrayList<Double>();
		ArrayList<Double> allLeftRts_dm = new ArrayList<Double>();
		ArrayList<Double> allRightRts_dm = new ArrayList<Double>();
		
		ArrayList<ArrayList<Double>> blocLeftAndRight = new ArrayList<ArrayList<Double>>();
		
		allStim = currentBloc.getStimulusComplet();
		for (int i = 0; i < allStim.length; i++)
		{
			if (allStim[i]!= null && allStim[i].getIsAcc()== true && 5000>allStim[i].getRt() && allStim[i].getRt()>200 && allStim[i].getIsLeft()==true)
			{
				if(allStim[i].getSPG_SPD_SM_DM() == "SPG"){
					allLeftRts_sp.add(allStim[i].getRt());
				}else if(allStim[i].getSPG_SPD_SM_DM() == "SM"){
					allLeftRts_sm.add(allStim[i].getRt());
				}else if(allStim[i].getSPG_SPD_SM_DM() == "DM"){
					allLeftRts_dm.add(allStim[i].getRt());
				}
	
			}
			if (allStim[i]!= null && allStim[i].getIsAcc()== true && 5000>allStim[i].getRt() && allStim[i].getRt()>200 && allStim[i].getIsLeft()==false)
			{
				if(allStim[i].getSPG_SPD_SM_DM() == "SPD"){
					allRightRts_sp.add(allStim[i].getRt());
				}else if(allStim[i].getSPG_SPD_SM_DM() == "SM"){
					allRightRts_sm.add(allStim[i].getRt());
				}else if(allStim[i].getSPG_SPD_SM_DM() == "DM"){
					allRightRts_dm.add(allStim[i].getRt());
				}
			}
		}
		
	
		blocLeftAndRight.add(allLeftRts_sp);
		blocLeftAndRight.add(allRightRts_sp);
		blocLeftAndRight.add(allLeftRts_sm);
		blocLeftAndRight.add(allRightRts_sm);
		blocLeftAndRight.add(allLeftRts_dm);
		blocLeftAndRight.add(allRightRts_dm);
	
		return blocLeftAndRight;
	}

	// Return all the accuracy (for each trial) separated left/right for the bloc sended in parameter
	public static ArrayList<ArrayList<Double>> getAllAccOfBloc (Bloc currentBloc)
	{
		Stimulus[] allStim;
		ArrayList<Double> allLeftRts_sp = new ArrayList<Double>();
		ArrayList<Double> allRightRts_sp = new ArrayList<Double>();
		ArrayList<Double> allLeftRts_sm = new ArrayList<Double>();
		ArrayList<Double> allRightRts_sm = new ArrayList<Double>();
		ArrayList<Double> allLeftRts_dm = new ArrayList<Double>();
		ArrayList<Double> allRightRts_dm = new ArrayList<Double>();
		
		ArrayList<ArrayList<Double>> blocLeftAndRight = new ArrayList<ArrayList<Double>>();
		
		allStim = currentBloc.getStimulusComplet();
		for (int i = 0; i < allStim.length; i++)
		{
			if (allStim[i]!= null && 5000>allStim[i].getRt() && allStim[i].getRt()>200 && allStim[i].getIsLeft()==true)
			{
				if(allStim[i].getSPG_SPD_SM_DM() == "SPG"){
					if(allStim[i].getIsAcc() == true)
						allLeftRts_sp.add(1.0);
					else
						allLeftRts_sp.add(0.0);
				}else if(allStim[i].getSPG_SPD_SM_DM() == "SM"){
					if(allStim[i].getIsAcc() == true)
						allLeftRts_sm.add(1.0);
					else
						allLeftRts_sm.add(0.0);
				}else if(allStim[i].getSPG_SPD_SM_DM() == "DM"){
					if(allStim[i].getIsAcc() == true)
						allLeftRts_dm.add(1.0);
					else
						allLeftRts_dm.add(0.0);
				}
			}
			if (allStim[i]!= null && 5000>allStim[i].getRt() && allStim[i].getRt()>200 && allStim[i].getIsLeft()==false)
			{
				
				if(allStim[i].getSPG_SPD_SM_DM() == "SPD"){
					if(allStim[i].getIsAcc() == true)
						allRightRts_sp.add(1.0);
					else
						allRightRts_sp.add(0.0);
				}else if(allStim[i].getSPG_SPD_SM_DM() == "SM"){
					if(allStim[i].getIsAcc() == true)
						allRightRts_sm.add(1.0);
					else
						allRightRts_sm.add(0.0);
				}else if(allStim[i].getSPG_SPD_SM_DM() == "DM"){
					if(allStim[i].getIsAcc() == true)
						allRightRts_dm.add(1.0);
					else
						allRightRts_dm.add(0.0);
				}
			}
		}
		
		blocLeftAndRight.add(allLeftRts_sp);
		blocLeftAndRight.add(allRightRts_sp);
		blocLeftAndRight.add(allLeftRts_sm);
		blocLeftAndRight.add(allRightRts_sm);
		blocLeftAndRight.add(allLeftRts_dm);
		blocLeftAndRight.add(allRightRts_dm);
		
		return blocLeftAndRight;
	}

	// Return the standard deviation for the bloc sended in parameter
	public static double getStdOfBlock(ArrayList<Double> allStim){ return Utilities.getStdOfBlock(allStim, 0); } //overload

	public static double getStdOfBlock(ArrayList<Double> allStim, double mean)
	{
		int cpt = 0;
		double sum = 0;
		
		if (mean == 0){
			for (int i = 0; i < allStim.size(); i++)
			{
				sum += allStim.get(i);
				cpt++;
			}
			mean = sum/cpt;
		}
		sum = 0; cpt = 0;
		
		for (int i = 0; i < allStim.size(); i++)
		{
			sum += Math.pow(allStim.get(i) - mean, 2);
			cpt++;
		}
		
		return Math.sqrt(sum/cpt);
	}

	// Return the mean of an array of reaction time (to be use after getAllRtOfBloc)
	public static double getMeanOfBlock(ArrayList<Double> allStim)
	{
		double cpt = 0;
		double sum = 0;
		
		if (allStim != null)
		{
			for (int i = 0; i < allStim.size(); i++)
			{
				sum += allStim.get(i);
				cpt++;
			}
		
			return sum/cpt;
		}
		else
		{return -1;}
	}

	//recoit une array de stim, si isMean = true donne la moyenne sinon donne la quantité, if left doit être "true", "false" ou "both"
	public static double getStimQteOrMean(Stimulus[] allStim, boolean isMeanOrQte, String leftRightOrBoth, String seekGoodErrorLateOrAll)
	{
		int cpt = 0;
		double sum = 0;
		
		if (allStim!= null)
		{
			for (int i = 0; i < allStim.length; i++)
			{
				if (allStim[i]!= null && allStim[i].getKeyPressed() != '!' && (leftRightOrBoth=="both" || ((leftRightOrBoth=="right" && !allStim[i].getIsLeft()) || (allStim[i].getIsLeft())&&leftRightOrBoth=="left")))
				{
					
					if ((seekGoodErrorLateOrAll=="all" && allStim[i].getIsAnswered())  || ((seekGoodErrorLateOrAll=="late" && allStim[i].getKeyPressed() == '&')|| (seekGoodErrorLateOrAll!="late" && allStim[i].getRt()>AnimateBar.MINMS && AnimateBar.MAXMS>=allStim[i].getRt()))) 
	
					//if (seekGoodErrorLateOrAll=="late" && allStim[i].getKeyPressed() == '&')
						{
						//System.out.println("coucou    -" + i + allStim[i].getKeyPressed());
						if (seekGoodErrorLateOrAll=="late" ||(seekGoodErrorLateOrAll=="all" && allStim[i].getIsAnswered()) || ((seekGoodErrorLateOrAll == "good" && allStim[i].getIsAcc())|| (seekGoodErrorLateOrAll == "error" && !allStim[i].getIsAcc())))
						{
							if (isMeanOrQte){sum += allStim[i].getRt();}
							else
							{sum ++;}
							cpt++;
						}
					}
				}
			}
			if (isMeanOrQte){return sum/cpt;}
			else
			{
				//les premiers essais du bloc sont "late"
				return sum;
			}							
		}
		else
		{return -2;}
	}

	//recoit une array de stim, si isMean = true donne la moyenne sinon donne la quantité, if left doit être "true", "false" ou "both"
	public static double getStdDeviation(Stimulus[] allStim, String leftRightOrBoth, String seekGoodErrorLateOrAll, double mean)
		{
			int n = 0;
			double sum = 0;
			double std = 0;
			
			if (allStim!= null)
			{
				for (int i = 0; i < allStim.length; i++)
				{
					if (allStim[i]!= null && allStim[i].getKeyPressed() != '!' && (leftRightOrBoth=="both" || ((leftRightOrBoth=="right" && !allStim[i].getIsLeft()) || (allStim[i].getIsLeft())&&leftRightOrBoth=="left")))
					{
						
						if ((seekGoodErrorLateOrAll=="all" && allStim[i].getIsAnswered())  || ((seekGoodErrorLateOrAll=="late" && allStim[i].getKeyPressed() == '&')|| (seekGoodErrorLateOrAll!="late" && allStim[i].getRt()>AnimateBar.MINMS && AnimateBar.MAXMS>=allStim[i].getRt()))){ 
	
							if (seekGoodErrorLateOrAll=="late" ||(seekGoodErrorLateOrAll=="all" && allStim[i].getIsAnswered()) || ((seekGoodErrorLateOrAll == "good" && allStim[i].getIsAcc())|| (seekGoodErrorLateOrAll == "error" && !allStim[i].getIsAcc())))
							{
								//sum += allStim[i].getRt();
								n++;
								
								std += Math.pow((allStim[i].getRt() - mean), 2);
							}
						}
					}
				}
				
				
				std = Math.sqrt(std/n);
	
				return std;	
			}
			else
			{return -2;}
		}
}

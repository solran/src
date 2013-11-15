package utilities;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
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
}

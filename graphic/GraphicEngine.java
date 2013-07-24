package graphic;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import javax.swing.JPanel;

import core.Task;


public class GraphicEngine {

	private Graphics2D mainImage;
	private JPanel jPanel;
	private Timer timer;

	private static boolean modifying, reset;
	
	private AnimateBar myBar1, myBar2;
	private static int  newHeight1 = 0, newHeight2 = 0;
	private static boolean isVisible1 = false, isVisible2 = false;
	
	
	public GraphicEngine(Graphics2D mainImage, JPanel jPanel, int period){
		this.mainImage = mainImage;
		this.jPanel = jPanel;
        
		// Create the animate bars
    	setMyBar1(new AnimateBar(jPanel.getParent().getWidth()/2 , jPanel.getParent().getHeight() - 150, 0, 0, 0, 0, 100, mainImage, jPanel, true));			//Mettre des vraies valeurs....
    	setMyBar2(new AnimateBar(jPanel.getParent().getWidth()/2, jPanel.getParent().getHeight() - 150, 0, 0, 0, 0, 100, mainImage, jPanel, true, false));		//Mettre des vraies valeurs....    	
		
		timer = new Timer();
		timer.schedule(new GraphicManager(), 0, period);
	}
	

	public static void clearAll(){
		ImageBox myImg;
		ListIterator<ImageBox> litr = ImageBox.getAllImage().listIterator();
		
	    while (litr.hasNext()) {
	    	myImg = litr.next();
	    	
	    	if(! myImg.getName().equals("leftReminder") && ! myImg.getName().equals("rightReminder"))
				myImg.setVisible(false);
	    } 
	}
	

	class GraphicManager extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			jPanel.repaint();
		}
	}
	
	private ImageBox myImg;

	//@Override
	public void draw() {

		if(! modifying){
			
	        //Clear the bufferedImage
			ImageBox.clear(mainImage, jPanel, jPanel.getWidth(), jPanel.getHeight());

			//iteration of the ImageBox Arraylist containing all the images
			ArrayList<ImageBox> liste = ImageBox.getAllImage();
		    for(int i = 0; i < liste.size(); i++){
		    	myImg = liste.get(i);		//java.util.ConcurrentModificationException
			      
			    if( myImg.isVisible())
			    {
			    	mainImage.drawImage(myImg.getMyImage(), myImg.getX(), myImg.getY(), myImg.getWidth(), myImg.getHeight(), null);
			    	if (myImg.getStimulus() != null && myImg.getStimulus().getStimDisplaySync() ==0)
			    	{
			    		myImg.getStimulus().setStimDisplaySync(System.currentTimeMillis() - Task.syncTime);
			    	}
			    }
		    }
		    
		    myBar1.update(newHeight1, isVisible1);
		    myBar2.update(newHeight2, isVisible2);

		    
		    //iteration of the animation Arraylist
			ArrayList<Animate> animation = Animate.AnimationStack;

		
		    for(int i = 0; i < animation.size(); i++)
		    	animation.get(i).refresh(mainImage);
		    
		    Animate.refreshArrayList();
		}
	}
	
	
	// ----- GETTER AND SETTER ----- //
	public static boolean isReset() {
		return reset;
	}
	public static void setReset(boolean reset1) {	
		reset = reset1;	
	}
	public static boolean isModifying() {		
		return modifying;
	}
	public static void setModifying(boolean modifying1) {		
		modifying = modifying1;
	}

	public AnimateBar getMyBar1() {
		return myBar1;
	}
	public void setMyBar1(AnimateBar myBar1) {
		this.myBar1 = myBar1;
	}

	public AnimateBar getMyBar2() {
		return myBar2;
	}
	public void setMyBar2(AnimateBar myBar2) {
		this.myBar2 = myBar2;
	}

	public static void updateBar1(int newHeight){
		newHeight1 = newHeight;
	}
	public static void updateBar2(int newHeight){		
		newHeight2 = newHeight;
	}
	
	public static void setVisibleBar1(boolean visible){
		isVisible1 = visible;
	}
	public static void setVisibleBar2(boolean visible){		
		isVisible2 = visible;
	}
}	

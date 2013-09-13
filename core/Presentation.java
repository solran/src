package core;
import graphic.Animate;
import graphic.AnimateBar;
import graphic.GraphicEngine;
import graphic.ImageBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

import overview.Overview;
import overview.SimplifiedTask;

import utilities.Langue;
import utilities.ReadLog;
import utilities.Signal;
import utilities.SoundClip;
import utilities.Utilities;
import utilities.WriteLog;


import java.util.concurrent.Callable;


/*
 * --- Presentation ---
 * Classe qui gère l'affichage tant des slides que des stimuli (par l'intermédiaire d'une bufferedImage)
 */
public class Presentation extends JPanel{

	private boolean finished = false;
	private Task myTask;
	private Stimulus[] ActualStimulus, ActualOtherStimulus;
	
	private BufferedImage buffImage;
	private Graphics tempGraphic;
	private Graphics2D graphic;
	
	
	private Timer isi = new Timer();
	private Timer removeStimsTimer = new Timer();
	public static RemoveStims removeStims;
	public static Pause pause;

	
	private Timer nbackTimer = new Timer();
	private int timerCall = 0;
	private int index = 0;
	private int x, y;
	private int xOrigin, yOrigin;
	
	private int nbStimulus = 0;
	
	private KeyListener taskListener, taskListener2;

	private ImageBox feedback1, feedback2;

	
	private ArrayList<Stimulus> stim = new ArrayList<Stimulus>();
	private ArrayList<Stimulus> otherStim = new ArrayList<Stimulus>();

	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
	private Stimulus[] lastDmLeft = new Stimulus[5],  lastDmRight = new Stimulus[5];
	private double barValueLeft = 0, 	barValueRight = 0; 
	
	private int count = 0;
	private boolean isOffset = false;
	private String lastState = "";
	
	private Overview graph;
	private int xSize, ySize;
	private Toolkit tk;
	private ArrayList<ArrayList<SimplifiedTask>> tasks ;
	
	
	public Presentation(Task myTask){
		//hide cursor only if desktop version
		/*if(! Main.isApplet)
			Main.getInstance().hideCursor();
		*/
		this.myTask = myTask;
		
		//Initialize buffImage and graphic
		buffImage = new BufferedImage( Main.getInstance().getBigPanel().getWidth(), Main.getInstance().getBigPanel().getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		tempGraphic = buffImage.getGraphics();
		graphic = (Graphics2D)tempGraphic;
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		graphic.setFont(new Font("Verdana", Font.BOLD, 46));
		graphic.setColor(Color.BLACK);

		//Load single image
		feedback1 = new ImageBox(0, 0,  764, 314, Task.ENVIRONNEMENT + "feedback1.png", "feedback1");
		feedback2 = new ImageBox(0, 0,  488, 108, Task.ENVIRONNEMENT + "feedback2.png", "feedback2");
		
		//Add this JPanel to the "center" jpanel
        this.setOpaque(false);
        
        //center   to   mainPanel
        Main.getInstance().getBigPanel().setLayout(new BorderLayout());
        Main.getInstance().getBigPanel().add(this, BorderLayout.CENTER);      
	}
	
	
	private Animate reminderExplanation1, reminderExplanation2, reminderExplanation3;


	public void paintSlide(){
		float progressRatio;
		ImageBox tempImageBox, tempImageBox2;

        //dimension de l'écran
		xOrigin = Main.getInstance().getBigPanel().getWidth()/2 - myTask.getBackground().getWidth()/2;
		if (Fenetre.ySize < 800)
			yOrigin = Main.getInstance().getBigPanel().getHeight()/2 - myTask.getBackground().getHeight()/2 - myTask.getProgressBar().getHeight();
		else 
			yOrigin = Main.getInstance().getBigPanel().getHeight()/2 - myTask.getBackground().getHeight()/2 - 30;

		
		ActualStimulus = new Stimulus[myTask.getStimulus()[0].getStimulusLength()];
		ActualOtherStimulus = new Stimulus[myTask.getStimulus()[0].getStimulusLength()];
		
		GraphicEngine.setModifying(true);
		GraphicEngine.clearAll();
		
		
		if(!myTask.getMySlide().isStimulus() && myTask.getMySlide().getSlideName() != "reminderExplanation" && myTask.getMySlide().getSlideName() != "countdown" && myTask.getMySlide().getSlideName() != "pause"){

			System.out.println("New Block");
			
			if(! finished)
				WriteLog.writeMeans( myTask, "data/log_");
			
			if (myTask.getImagerie() == "IO"){Signal.sendSignal("instruction", myTask.getImagerie());}
			
			myTask.getBackground().setProperties(xOrigin, yOrigin, true);
			
			Main.getInstance().addKeyListener(new keyMenuListener (myTask.getMySlide().getSync()));
			// Erase the bars
			GraphicEngine.setVisibleBar1(false);	
			GraphicEngine.setVisibleBar2(false);	

			if (myTask.getMySlide().getMyStimulus() != null)
			{
				ActualStimulus = myTask.getMySlide().getMyStimulus();
				ActualOtherStimulus = myTask.getMySlide().getMyOtherStimulus();
				nbStimulus = ActualStimulus[0].getStimulusLength();
			}
			
			// Progress Bar
			x = Main.getInstance().getBigPanel().getWidth()/2 - myTask.getFrontProgressBar().getWidth()/2;
			y = Main.getInstance().getBigPanel().getHeight() - myTask.getFrontProgressBar().getHeight() + 15;
			
			
			progressRatio = (myTask.getBackProgressBar().getWidth() - 15) * ((float)Bloc.getBlocActuel() / (float)Bloc.getnbBloc());

			int widthBackspace = 96;
			int yAdjustement = 19;
			myTask.getBackProgressBar().setProperties(x + widthBackspace, y + yAdjustement, true);	// 170 = width of backspace
		

			myTask.getProgressBar().setWidth((int)progressRatio);            ///(int)progressRatio );        //Animation ?        y toujours la m�me chose
            myTask.getProgressBar().setProperties(x + widthBackspace + 5, y + yAdjustement + 2, true);
            
            myTask.getFrontProgressBar().setProperties(x, y, true);
			myTask.getCircleProgressBar().setProperties(x + widthBackspace + (int)progressRatio, y + yAdjustement + 10, true);
		
			
			myTask.getLeftReminderString().setVisible(false);
			myTask.getRightReminderString().setVisible(false);
		}
		
		
		if(lastState.equals("graphic")){
			
			Main.getInstance().remove(graph);
			
			Main.getInstance().windowPanel.setBackground(Color.BLACK);
			Main.getInstance().windowPanel.setLayout(new GridBagLayout());
			Main.getInstance().add(Main.getInstance().getBigPanel());
			
			Main.getInstance().windowPanel.setSize(new Dimension(Main.getInstance().getX() - 100, Main.getInstance().getY() - 100));
	      	
			lastState = "";
		}
		
		
		if (myTask.getMySlide().getSlideName() == "intro"){

				x = (int)(Main.getInstance().getBigPanel().getWidth()/2 - myTask.getHelloString().getWidth()/2);
				y = (int)(Main.getInstance().getBigPanel().getHeight()/2 - myTask.getHelloString().getHeight()/2);
				myTask.getHelloString().setProperties(x, y, true);				
		}
		else if (myTask.getMySlide().getSlideName() == "keyGeneralExplanation" || myTask.getMySlide().getSlideName() == "keyGeneralExplanation2"){
				//  --	--	--	--
				//	--	--	--	--
				
				y = yOrigin + 250;
				
				for(int i=0; i < nbStimulus; i++){
					
					tempImageBox = myTask.getMySmallImages().get(ActualStimulus[i].getName())[0];
					x =(int)((Main.getInstance().getMainPanel().getWidth())/(nbStimulus + 1)) * (i + 1) - tempImageBox.getWidth()/2 + xOrigin;
					
					myTask.getBlackSquareSmall()[i].setProperties(x, y, true);
					tempImageBox.setProperties(x, y, true);
					
					
					tempImageBox = myTask.getMyImgKeys().get(ActualStimulus[i].getKey());
					tempImageBox.setProperties(x, y + 125, true);
				}
		}
		
		
		
		else if (myTask.getMySlide().getSlideName() == "keyDetailedExplanation"){
				//		--
				//	----------
				//	----------
				//	----------
				
				index = myTask.getMySlide().getMyCardinal();
				
				tempImageBox = myTask.getMySmallImages().get(ActualStimulus[index].getName())[0];
				x =(int)(Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
				y = yOrigin + 120;
				myTask.getBlackSquareSmall()[index].setProperties(x, y, true);
				tempImageBox.setProperties(x, y, true);
				
				//Play sounds for audio task
				if(myTask.isStimsAreSounds()){
					myTask.getMySounds().get(ActualStimulus[index].getName()).playMe();
					
					Main.getInstance().addMouseListener(new ClickListener(tempImageBox));
				}
				
				
				tempImageBox =  myTask.getMyImgKeyboard().get(ActualStimulus[index].getKey());
				x =(int)(Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
				y = yOrigin + 220;
				tempImageBox.setProperties(x, y, true);
				
				if(ActualStimulus[index].getIsLeft()){
					tempImageBox =  myTask.getMyImgHands().get(ActualStimulus[index].getKey());
					x = xOrigin + 95 + 25;
					y = yOrigin + 405;
				}else{
					tempImageBox =  myTask.getMyImgHands().get(ActualStimulus[index].getKey());
					x = xOrigin + 605 + 25;
					y = yOrigin + 370;
					if (myTask.getFormat() == "noNumpad")
					{
						x =(int)(Main.getInstance().getBigPanel().getWidth()/2 - (tempImageBox.getWidth()/2+30));
						y = yOrigin + 405;
					}
				}
				tempImageBox.setProperties(x, y, true);
				
				
			
		}
			
		else if (myTask.getMySlide().getSlideName() == "keySimpleMixte" || myTask.getMySlide().getSlideName() == "keyDoubleTask" ){
				//	--	--	--	-	--	--	--
				//	--	--	--		--	--	--
								
				
				
				
				for(int i=0; i < nbStimulus; i++){
					y = yOrigin + 220;
					tempImageBox = myTask.getMySmallImages().get(ActualStimulus[i].getName())[0];
					tempImageBox2 = myTask.getMySmallImages().get(ActualStimulus[i].getName())[0];
					
					
					if(nbStimulus == 4){
						x = xOrigin + (int)((Main.getInstance().getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + 1) - tempImageBox2.getWidth()/2;
						myTask.getBlackSquareSmall()[i].setProperties(x, y, true);
						tempImageBox2.setProperties(x, y, true);				
					}else{
						x = xOrigin +(int)((Main.getInstance().getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + 1) - tempImageBox.getWidth()/2;
						myTask.getBlackSquareSmall()[i].setProperties(x, y, true);
						tempImageBox.setProperties(x, y, true);
					}
					
					
					tempImageBox = myTask.getMyImgKeys().get(ActualStimulus[i].getKey());
					x = xOrigin + (int)((Main.getInstance().getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + 1) - tempImageBox.getWidth()/2;
					y = yOrigin + 350;
					tempImageBox.setProperties(x, y, true);
					
				}

				x = xOrigin + (int)((Main.getInstance().getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (nbStimulus + 1) - 40;
				y = yOrigin + 220;
				
				if(myTask.getMySlide().getSlideName().equalsIgnoreCase("keySimpleMixte")){
					myTask.getOrString().setProperties(x, y, true);
				}else if(myTask.getMySlide().getSlideName().equalsIgnoreCase("keyDoubleTask")){
					myTask.getAndString().setProperties(x, y, true);
				}

				
				for(int i=0; i < nbStimulus; i++){
					y = yOrigin + 220;
					tempImageBox = myTask.getMySmallImages().get(ActualOtherStimulus[i].getName())[0];
					tempImageBox2 = myTask.getMySmallImages().get(ActualOtherStimulus[i].getName())[0];
					
					if(nbStimulus == 4){
						x = xOrigin + (int)((Main.getInstance().getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + nbStimulus + 2) - tempImageBox2.getWidth()/2;
						myTask.getBlackSquareSmall()[i + nbStimulus].setProperties(x, y, true);
						tempImageBox2.setProperties(x, y, true);				
					}else{
						x = xOrigin +  (int)((Main.getInstance().getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + nbStimulus + 2) - tempImageBox2.getWidth()/2;
						myTask.getBlackSquareSmall()[i + nbStimulus].setProperties(x, y, true);
						tempImageBox.setProperties(x, y, true);
					}
					
					tempImageBox = myTask.getMyImgKeys().get(ActualOtherStimulus[i].getKey());
					x = xOrigin + (int)((Main.getInstance().getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + nbStimulus + 2) - tempImageBox.getWidth()/2;
					y = yOrigin + 350;
					tempImageBox.setProperties(x, y, true);
				}
				
			}
			else if (myTask.getMySlide().getSlideName() == "nBackExplanation"){

				// N-Back		
				graphic.setFont(new Font("Verdana", Font.PLAIN, 22));
				ActualStimulus = myTask.getStimulus();
				ActualOtherStimulus = myTask.getOtherStimulus();
				
				nbackTimer = new Timer();
				nbackTimer.schedule(new addNbackImage(), 0, 1500);
			}
		
			else if (myTask.getMySlide().getSlideName() == "nBackAsterisk"){
				tempImageBox = myTask.getCross();
				x = Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
				y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2 -100;
				tempImageBox.setProperties(x, y, true);
				
			}
		
			else if (myTask.getMySlide().getSlideName() == "pauseInstruction"){
					tempImageBox = myTask.getBrain();
					x = Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true);
			}
		
			else if (myTask.getMySlide().getSlideName() == "goodbye"){
				
					tempImageBox = myTask.getBrain();
					x = Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true);
					
				if(! finished){
					sessionFinished();
					finished = true;
				}
			}
		
			else if (myTask.getMySlide().getSlideName() == "allStimuli"){
				// hGap and vGap
				for(int i=0; i < nbStimulus; i++){
					for(int i2 = 0; i2 < 3; i2++){  //2

						tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[i].getName())[i2];

						x = (int)((Main.getInstance().getMainPanel().getWidth())/(nbStimulus + 1)) * (i + 1) - tempImageBox.getWidth()/2 + xOrigin;
						y = yOrigin + 150 + i2 * 100;
						myTask.getBlackSquareSmall()[i2 + i * 3].setProperties(x, y, true);
						tempImageBox.setProperties(x, y, true);
					}
				}
			
			}
			else if (myTask.getMySlide().getSlideName() == "allStimuliNback"){
				// hGap and vGap
				y = yOrigin + 1 * 200;
						
				
				
				if(myTask.getVersion() == 5 && myTask.getnBack() == 0){
					
				}else{
					tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[0].getName())[0];
						x = (int)((Main.getInstance().getMainPanel().getWidth())/(4)) * (1 ) - tempImageBox.getWidth()/2 + xOrigin;
						myTask.getBlackSquareSmall()[0].setProperties(x, y, true);
						tempImageBox.setProperties(x, y, true);
				}
				
				
				if(myTask.getVersion() == 5 && myTask.getnBack() == 0)
					tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[2].getName())[1];
				else
					tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[0].getName())[1];
						x = (int)((Main.getInstance().getMainPanel().getWidth())/(4)) * (2 ) - tempImageBox.getWidth()/2 + xOrigin;
						myTask.getBlackSquareSmall()[1].setProperties(x, y, true);
						tempImageBox.setProperties(x, y, true);
						
				
						
						tempImageBox = myTask.getEqual()[0];
						x = (x + ((int)((Main.getInstance().getMainPanel().getWidth())/(4)) * (3 ) - tempImageBox.getWidth()/2 + xOrigin))/2 ;
						tempImageBox.setProperties(x+ 25, y+20, true);
						
						x = (int)((Main.getInstance().getMainPanel().getWidth())/(4)) * (3 ) - tempImageBox.getWidth()/2 + xOrigin;
						if (ActualStimulus[1].getIsLeft())
						{
								tempImageBox = myTask.getMyImgKeys().get(Task.mainTask.getLeftKeys()[0]);
						}else
						{
							tempImageBox = myTask.getMyImgKeys().get(Task.mainTask.getRightKeys()[0]);
						}
						tempImageBox.setProperties(x, y + 15, true);
						
					y = yOrigin+ 2 * 200;
					
				if(myTask.getVersion() == 5 && myTask.getnBack() == 0){
					
				}else{	
					tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[0].getName())[2];
					x = (int)((Main.getInstance().getMainPanel().getWidth())/(4)) * (1 ) - tempImageBox.getWidth()/2 + xOrigin;
					myTask.getBlackSquareSmall()[2].setProperties(x, y, true);
					tempImageBox.setProperties(x, y, true);
				}	
						
				if(myTask.getVersion() == 5 && myTask.getnBack() == 0)
					tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[6].getName())[2];
				else
					tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[1].getName())[2];
						
						x = (int)((Main.getInstance().getMainPanel().getWidth())/(4)) * (2 ) - tempImageBox.getWidth()/2 + xOrigin;
						myTask.getBlackSquareSmall()[3].setProperties(x, y, true);
						tempImageBox.setProperties(x, y, true);
						
						tempImageBox = myTask.getEqual()[1];
						x = (x + ((int)((Main.getInstance().getMainPanel().getWidth())/(4)) * (3 ) - tempImageBox.getWidth()/2 + xOrigin))/2 ;
						tempImageBox.setProperties(x+ 25, y+20, true);
						
						x = (int)((Main.getInstance().getMainPanel().getWidth())/(4)) * (3 ) - tempImageBox.getWidth()/2 + xOrigin;
						if (ActualStimulus[1].getIsLeft())
						{
								tempImageBox = myTask.getMyImgKeys().get(Task.mainTask.getLeftKeys()[1]);
						}else
						{
							tempImageBox = myTask.getMyImgKeys().get(Task.mainTask.getRightKeys()[1]);
						}
						tempImageBox.setProperties(x, y + 15, true);
			
			}
			else if (myTask.getMySlide().getSlideName() == "feedback"){
				// Feedback & Priorite
				/*tempImageBox =  myTask.getFeedback();
				x =(int)(Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
				y = yOrigin + 180;
				tempImageBox.setProperties(x, y, true);
				*/
				
			}
			else if (myTask.getMySlide().getSlideName() == "priorite"){
			
				double d = AnimateBar.getStimQteOrMean(Utilities.getallStim(myTask.blocsSM), true, "left", "good");
				double e = AnimateBar.getStimQteOrMean(Utilities.getallStim(myTask.blocsSM), true, "right", "good");
				double f = AnimateBar.getStimQteOrMean(myTask.getMySlide().getMyStimulus(), true, "both", "good");
				double g = AnimateBar.getStimQteOrMean(myTask.getMySlide().getMyOtherStimulus(), true, "both", "good");
				double i = (d/e)/(f/g);		
				
				if (i > 1.20)	//Philippe
				{
					
					myTask.getBadSound().playMe();
					
					Langue.setPriority("left");
					/*tempImageBox = myTask.getSpeed();
					x = (int)(Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
					y = yOrigin + 150;
					
					
					tempImageBox.setProperties(x, y, true);
					
					
					new Animate(new String[]{"translation", (xOrigin + 400) + "," + (yOrigin + 150)}, 2000, tempImageBox, Main.getInstance().getMainPanel());
					 */
				}
				else if (i < 0.80)	//Philippe
				{
					/*
					SoundClip.play(myTask.getBadSound());
					Langue.setPriority("right");
					tempImageBox = myTask.getSpeed();
					x = (int)(Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
					y = yOrigin + 150;
					tempImageBox.setProperties(x, y, true);
					
					new Animate(new String[]{"translation", (xOrigin + 200) + "," + (yOrigin + 150)}, 2000, tempImageBox, Main.getInstance().getMainPanel());
					 */
				}
				else
				{
					myTask.getGoodSound().playMe();
					
					Langue.setPriority("equal");
					tempImageBox =  myTask.getGood();
					x = (int)(Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
					y = yOrigin + 150;
					tempImageBox.setProperties(x, y, true);
					
					//philippe
					//new Animate(new String[]{"translation", (xOrigin + 200) + "," + (yOrigin + 150)}, 2000, tempImageBox, Main.getInstance().getMainPanel());

				}
				
				myTask.getMySlide().setTxtHeader(Langue.translate("prioriteHeader"));		
				myTask.getMySlide().setTxtFooter(Langue.translate("prioriteFooter"));					
				Main.getInstance().labelHeader.setText(myTask.getMySlide().getTxtHeader());
				Main.getInstance().labelFooter.setText(myTask.getMySlide().getTxtFooter());				
				
				tempImageBox =  myTask.getMyImgHands().get("gauche");
				x = xOrigin + 150;
				y = yOrigin + 300;
				tempImageBox.setProperties(x, y, true);
				
				tempImageBox =  myTask.getMyImgHands().get("droite");
				x = xOrigin + 550;
				y = yOrigin + 300;
				tempImageBox.setProperties(x, y, true);
				
			}
			else if (myTask.getMySlide().getSlideName() == "countdown"){
				
				
				if (myTask.getImagerie() == "EEG")
				{
					if (myTask.getnBack()== 0)
						{Signal.sendSignal("bloc0back", myTask.getImagerie());}
					if (myTask.getnBack()== -1)
						{Signal.sendSignal("bloc1back", myTask.getImagerie());}
					if (myTask.getnBack()== -2)
						{Signal.sendSignal("bloc2back", myTask.getImagerie());}
					if (myTask.getnBack()== -3)
						{Signal.sendSignal("bloc3back", myTask.getImagerie());}
				}
				

				// Removes the reminderExplanation. If the animation isn't finished, it stops it!
					if(reminderExplanation1.isActive())
						reminderExplanation1.stop();
					myTask.getReminderExplanation1().setVisible(false);
					
					if(reminderExplanation2.isActive())
						reminderExplanation2.stop();
					myTask.getReminderExplanation2().setVisible(false);
					
					if(reminderExplanation3.isActive())
						reminderExplanation3.stop();
					myTask.getReminderExplanation3().setVisible(false);
				
	
				
				new Animate(new String[]{"fadein",""}, 1500, 100, Main.getInstance().getBigPanel().getWidth()/2 - 85, Main.getInstance().getBigPanel().getHeight()/2 - 25, 100, 100, "III", new Callable<Integer>(){ 
					public Integer call() {
						return callback(); 
					} 
				});
			}
			else if (myTask.getMySlide().getSlideName() == "stimulus"){
				stim.add(myTask.getMySlide().getSoloStimulus());
				otherStim.add( myTask.getMySlide().getSoloOtherStimulus());
				graphic.setColor(Color.white);
				graphic.setFont(new Font("Verdana", Font.BOLD, 20));
				Random rgen = new Random();
				
				isOffset = false;
				
				if (myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "SPG")
				{
					// Imagerie
					if (myTask.getImagerie() == "IO"){Signal.sendSignal("SPG", myTask.getImagerie());}
					if (myTask.getImagerie() == "EEG")
					{
						if (myTask.getMySlide().getSoloStimulus().getMatch()== "isMatching")
						{
								if (myTask.getnBack()== 0)
									{Signal.sendSignal("0backMatch", myTask.getImagerie());}
								else if (myTask.getnBack()== -1)
									{Signal.sendSignal("1backMatch", myTask.getImagerie());}
								else if (myTask.getnBack()== -2)
									{Signal.sendSignal("2backMatch", myTask.getImagerie());}
								else if (myTask.getnBack()== -3)
									{Signal.sendSignal("3backMatch", myTask.getImagerie());}
						}
						else if (myTask.getMySlide().getSoloStimulus().getMatch()== "notMatching")
						{
								if (myTask.getnBack()== 0)
									{Signal.sendSignal("0backNotMatch", myTask.getImagerie());}
								else if (myTask.getnBack()== -1)
									{Signal.sendSignal("1backNotMatch", myTask.getImagerie());}
								else if (myTask.getnBack()== -2)
									{Signal.sendSignal("2backNotMatch", myTask.getImagerie());}
								else if (myTask.getnBack()== -3)
									{Signal.sendSignal("3backNotMatch", myTask.getImagerie());}
						}
					}

					// Déclenchement du stim
					tempImageBox = null;
					if(! myTask.isStimsAreSounds()){
						tempImageBox =  myTask.getMyImages().get(stim.get(count).getName())[rgen.nextInt(3)];
					}else if(myTask.isStimsAreSounds()){
						System.out.println(stim.get(count).getName());
						myTask.getMySounds().get(stim.get(count).getName()).playMe();
						tempImageBox = myTask.getBlackSquare()[0];
					}
					x =  Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true, myTask.getMySlide().getSoloStimulus());
					
					Main.getInstance().addKeyListener(taskListener = new TaskListener (myTask.getMySlide().getExpectedKey(0), myTask.getMyExpectedKeys(), myTask.getMYNEARKEYS(), myTask.getMySlide().getSoloStimulus(), myTask.getLeftReminderString(), System.currentTimeMillis()));
					
				}
				else if (myTask.getMySlide().getSoloOtherStimulus().getSPG_SPD_SM_DM() == "SPD")
				{
					if (myTask.getImagerie() == "IO"){Signal.sendSignal("SPD", myTask.getImagerie());}
					
					tempImageBox = null;
					if(! myTask.isStimsAreSounds()){
						tempImageBox =  myTask.getMyImages().get(otherStim.get(count).getName())[rgen.nextInt(3)];
					}else if(myTask.isStimsAreSounds()){
						myTask.getMySounds().get(otherStim.get(count).getName()).playMe();
						tempImageBox = myTask.getBlackSquare()[0];
					}
					x =  Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true, myTask.getMySlide().getSoloOtherStimulus());
					
					
					Main.getInstance().addKeyListener(taskListener = new TaskListener (myTask.getMySlide().getExpectedKey(1), myTask.getMyOtherExpectedKeys(), myTask.getMYOTHERNEARKEYS(), myTask.getMySlide().getSoloOtherStimulus(), myTask.getRightReminderString(), System.currentTimeMillis()));
				}
				else if  (myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "SM" )
				{
					if (myTask.getImagerie() == "IO"){Signal.sendSignal("SM", myTask.getImagerie());}
					
					tempImageBox = null;
					if(! myTask.isStimsAreSounds()){
						tempImageBox =  myTask.getMyImages().get(stim.get(count).getName())[rgen.nextInt(3)];
					}else if(myTask.isStimsAreSounds()){
						myTask.getMySounds().get(stim.get(count).getName()).playMe();
						tempImageBox = myTask.getBlackSquare()[0];
					}
					x = Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true, myTask.getMySlide().getSoloStimulus());
					
					Main.getInstance().addKeyListener(taskListener = new TaskListener (myTask.getMySlide().getExpectedKey(0), myTask.getMyExpectedKeys()+ myTask.getMyOtherExpectedKeys(), myTask.getMYNEARKEYS()+ myTask.getMYOTHERNEARKEYS(), myTask.getMySlide().getSoloStimulus(), myTask.getLeftReminderString(), myTask.getRightReminderString(), myTask.getMYOTHERNEARKEYS(), System.currentTimeMillis()));
				}				
				else if  (myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "DM" )
				{
					if (myTask.getImagerie() == "IO"){Signal.sendSignal("DM", myTask.getImagerie());}
					tempImageBox = null;
					
					if(! myTask.isStimsAreSounds()){
						tempImageBox = myTask.getMyImages().get(stim.get(count).getName())[rgen.nextInt(3)];
					}else if(myTask.isStimsAreSounds()){
						myTask.getMySounds().get(stim.get(count).getName()).playMe();
						tempImageBox = myTask.getBlackSquare()[0];
					}
					x = Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true, myTask.getMySlide().getSoloStimulus());
					
					
					if(! myTask.isStimsAreSounds()){
						tempImageBox = myTask.getMyImages().get(otherStim.get(count).getName())[rgen.nextInt(3)];
					}else if(myTask.isStimsAreSounds()){
						myTask.getMySounds().get(otherStim.get(count).getName()).playMe();
						tempImageBox = myTask.getBlackSquare()[0];
					}
					x = Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true, myTask.getMySlide().getSoloOtherStimulus());	
					
					
					
					Main.getInstance().addKeyListener(taskListener = new TaskListener (myTask.getMySlide().getExpectedKey(0), myTask.getMyExpectedKeys(), myTask.getMYNEARKEYS(), myTask.getMySlide().getSoloStimulus(), myTask.getLeftReminderString(),  myTask.getMyOtherExpectedKeys(), myTask.getMySlide().getSoloOtherStimulus(), myTask.getRightReminderString(), System.currentTimeMillis()));
					Main.getInstance().addKeyListener(taskListener2 = new TaskListener (myTask.getMySlide().getExpectedKey(1), myTask.getMyOtherExpectedKeys(), myTask.getMYOTHERNEARKEYS(), myTask.getMySlide().getSoloOtherStimulus(), myTask.getRightReminderString(), myTask.getMyExpectedKeys(), myTask.getMySlide().getSoloStimulus(), myTask.getLeftReminderString(), System.currentTimeMillis()));
				}
				
				if (myTask.getMySlide().getSoloStimulus() != null && myTask.getMySlide().getSoloStimulus().getKey() == "!")
				{   
					writeStimInfo(true, 9999.0, 9999.0, '!', System.currentTimeMillis(), myTask.getMySlide().getSoloStimulus());
					x = (int)(Main.getInstance().getBigPanel().getWidth()/2 - myTask.getDoNotAnswer().getWidth()/2);
					y = (int)(Main.getInstance().getBigPanel().getHeight()/2 - myTask.getDoNotAnswer().getHeight()/2) - 100;
					new Animate(new String[]{"fade",""}, 1500, x ,y, myTask.getDoNotAnswer(), Main.getInstance().getMainPanel());
				}
				if (myTask.getMySlide().getSoloOtherStimulus() != null && myTask.getMySlide().getSoloOtherStimulus().getKey() == "!")	
				{
					writeStimInfo(true, 9999.0, 9999.0, '!', System.currentTimeMillis(), myTask.getMySlide().getSoloOtherStimulus());
					x = (int)(Main.getInstance().getBigPanel().getWidth()/2 - myTask.getDoNotAnswer().getWidth()/2);
					y = (int)(Main.getInstance().getBigPanel().getHeight()/2 - myTask.getDoNotAnswer().getHeight()/2) - 100;
					new Animate(new String[]{"fade",""}, 1500, x ,y, myTask.getDoNotAnswer(), Main.getInstance().getMainPanel());
				}
				if (myTask.getStimT() != 0)
				{
					removeStimsTimer.schedule(removeStims = new RemoveStims(myTask.getLeftReminderString(), myTask.getRightReminderString()), 0, 100);
				}

				count++;
				
			}
		
			else if (myTask.getMySlide().getSlideName() == "pause"){
				removeStimsTimer.schedule(pause = new Pause(myTask.getPauseT()), 0, 15000);
			}
		
		
			else if (myTask.getMySlide().getSlideName() == "reminderExplanation"){
				if (myTask.getImagerie() == "IO"){Signal.sendSignal("instruction", myTask.getImagerie());}
				Main.getInstance().addKeyListener(new keyMenuListener (myTask.getMySlide().getSync()));

				tempImageBox = myTask.getCross();
				x = Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
				y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
				tempImageBox.setProperties(x, y, true);
				
				reminderExplanation1 = new Animate(new String[]{"fadein",""}, 500, 50, (int)(Main.getInstance().getBigPanel().getHeight()-150),  myTask.getReminderExplanation1(), Main.getInstance().getMainPanel());
				reminderExplanation2 = new Animate(new String[]{"fadein",""}, 500, Main.getInstance().getBigPanel().getWidth()/2 - myTask.getReminderExplanation2().getWidth()/2+10, Main.getInstance().getBigPanel().getHeight()/2 - 120,  myTask.getReminderExplanation2(), Main.getInstance().getMainPanel());
				reminderExplanation3 = new Animate(new String[]{"fadein",""}, 500, Main.getInstance().getBigPanel().getWidth() - (myTask.getReminderExplanation3().getWidth()), Main.getInstance().getBigPanel().getHeight()- 140,  myTask.getReminderExplanation3(), Main.getInstance().getMainPanel());

				prepareForNextStim(myTask);
			}
		
			else if (myTask.getMySlide().getSlideName() == "preSpeedOverviewInstruction" || myTask.getMySlide().getSlideName() == "accOverviewInstruction" || myTask.getMySlide().getSlideName() == "postSpeedOverviewInstruction"){
				
				tempImageBox = myTask.getOverview();
				x = Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
				y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
				tempImageBox.setProperties(x, y, true);			
			}

			
			else if (myTask.getMySlide().getSlideName() == "speedOverview" ){

				Main.getInstance().windowPanel.setSize(new Dimension(Main.getInstance().getX() - 100, Main.getInstance().getY() - 100));
				Main.getInstance().windowPanel.setBackground(Color.BLACK);
				Main.getInstance().windowPanel.setLayout(new BorderLayout());
			  	    try
					{
				      	//*/	Affichage du graphique Overview
					    tk = Toolkit.getDefaultToolkit();  
						xSize = ((int) tk.getScreenSize().getWidth());  
						ySize = ((int) tk.getScreenSize().getHeight());
					    
					    tasks = new ArrayList<ArrayList<SimplifiedTask>>();
					    
					    tasks= ReadLog.forSpeedOverview(myTask.getSujetID(), "data/log_", "dtCost");
						
				      	graph = new Overview(tasks, (xSize-50), (ySize-50), "Speed");
				      	graph.setInversed(true);
			
				      	//graph.setCheckbox(true);
				      	
				      	graph.drawOverview();
				      	Main.getInstance().windowPanel.add(graph, BorderLayout.CENTER);
						Main.getInstance().remove(Main.getInstance().getBigPanel());

				      	Main.getInstance().showCursor(graph);
				      	lastState = "graphic";

					} catch (Throwable t) {
						myTask.getMySlide().setTxtHeader(Langue.translate(new String[] {"overviewError", "header"}));
						myTask.getMySlide().setTxtFooter(Langue.translate(new String[] {"overviewError", "footer"}));
			            System.out.println("graphic problem: " );
			            for (StackTraceElement element : t.getStackTrace() ){
			                System.out.println(  element );
			              }
			        }
			      	Main.getInstance().revalidate();
			      	
				
			}
			else if (myTask.getMySlide().getSlideName() == "accOverview" ){
				
				Main.getInstance().windowPanel.setSize(new Dimension(Main.getInstance().getX() - 100, Main.getInstance().getY() - 100));
				Main.getInstance().windowPanel.setBackground(Color.BLACK);
				Main.getInstance().windowPanel.setLayout(new BorderLayout());
	  	    
		  	    try
				{
			      	//*/	Affichage du graphique Overview
				    tk = Toolkit.getDefaultToolkit();  
					xSize = ((int) tk.getScreenSize().getWidth());  
					ySize = ((int) tk.getScreenSize().getHeight());
				    
				    tasks = new ArrayList<ArrayList<SimplifiedTask>>();
		
				    
				    tasks= ReadLog.forAccOverview(myTask.getSujetID(), "data/log_");
				    
			      	graph = new Overview(tasks, (xSize-50), (ySize-50), "Acc");
			      	graph.drawOverview();
			      	Main.getInstance().windowPanel.add(graph, BorderLayout.CENTER);
					
			      	Main.getInstance().remove(Main.getInstance().getBigPanel());
			      	Main.getInstance().showCursor(graph);
			      	lastState = "graphic";		

				} catch (Throwable t) {
					myTask.getMySlide().setTxtHeader(Langue.translate(new String[] {"overviewError", "header"}));
					myTask.getMySlide().setTxtFooter(Langue.translate(new String[] {"overviewError", "footer"}));
		            System.out.println("graphic problem: ");
		            
		            for (StackTraceElement element : t.getStackTrace() ){
		                System.out.println(  element );
		              }
		            
		        }
		      	Main.getInstance().revalidate();
		      	
		}
		else
		{
			System.out.println ("Slide inexistante : " + myTask.getMySlide().getSlideName());
		}
		
		
			
		
		
		
GraphicEngine.setModifying(false);	
		Main.getInstance().labelHeader.setText(myTask.getMySlide().getTxtHeader());
		Main.getInstance().labelFooter.setText(myTask.getMySlide().getTxtFooter());
	}
	
	
	//	Callbacks of Countdown
	public Integer callback(){
		new Animate(new String[]{"fadein",""}, 1500, 100, Main.getInstance().getBigPanel().getWidth()/2 - 85, Main.getInstance().getBigPanel().getHeight()/2-25, 100, 100, " II", new Callable<Integer>(){ 
			public Integer call() {
				
				
				return callback2(); 
			} 
		});
		return 0;
	}
	public Integer callback2(){
		new Animate(new String[]{"fadein",""}, 1500, 100, Main.getInstance().getBigPanel().getWidth()/2 - 85, Main.getInstance().getBigPanel().getHeight()/2-25, 100, 100, "  I", new Callable<Integer>(){ 
			public Integer call() {
				return callback3(); 
			} 
		});
		return 0;
	}
	public Integer callback3(){
		myTask.getNextSlide('#');
		paintSlide();
		return 0;
	}	
	
	public void paintComponent(Graphics g) {
		//Draw the bufferedImage in the JPanel
		super.paintComponent(g);
		
		myTask.getGraphicEngine().draw();
		
        g.drawImage(buffImage, 0, 0, null);
	}
	
	public static void removeFromScreen (ImageBox[] imageBoxes)
	{
		for (int i = 0; i<imageBoxes.length; i++)
		{
			if (imageBoxes[i]!= null)	
				imageBoxes[i].setVisible(false);
		}
	}
	
	

	class addNbackImage extends TimerTask{
		
		//hardcoded - Doit recevoir nBack et Typetask
		private int nback = myTask.getnBack();
		private ImageBox[] answer = new ImageBox[5];
		private int x, y, x2, y2;
		private ImageBox[] tempImageBoxes = new ImageBox[9];

		
		public void dispatchNback (int x, int task1, int task2, int task3, int task4, Stimulus[] taskA, Stimulus[] taskB)
		{
			String[] word;
			if (myTask.getTypeNback() == "matching")
				word = new String[]{Langue.translate(new String[] {"exempleNback", "SP1"}), Langue.translate(new String[] {"exempleNback", "SP2"}), Langue.translate(new String[] {"exempleNback", "SP3"})};
			else
				word = new String[]{Langue.translate(new String[] {"exempleNback", "retrieval"}) + taskB[task2].getName(), Langue.translate(new String[] {"exempleNback", "retrieval"})+ taskA[task3].getName(),Langue.translate(new String[] {"exempleNback", "retrieval"})+ taskB[task4].getName()};

					switch(x){
					
						case 1:
							tempImageBoxes[x] =  ImageBox.copy(myTask.getMyImages().get(taskA[task1].getName())[0]);
							answer[x] = new ImageBox(new Color(0, 0, 0, 255), new Font("Arial", Font.BOLD, 16), 0, 0, 500, 500, word[0], "sentence1");
						break;
						case 2:
							tempImageBoxes[x] =  ImageBox.copy(myTask.getMyImages().get(taskB[task2].getName())[0]);
							answer[x] = new ImageBox(new Color(0, 0, 0, 255), new Font("Arial", Font.BOLD, 16), 0, 0, 500, 500, word[1], "sentence2");
					
						break;
						case 3:
							tempImageBoxes[x] =  ImageBox.copy(myTask.getMyImages().get(taskA[task3].getName())[0]);
							answer[x] = new ImageBox(new Color(0, 0, 0, 255), new Font("taskA", Font.BOLD, 16), 0, 0, 500, 500, word[2], "sentence3");
					
						break;
						case 4:
							tempImageBoxes[x] =  ImageBox.copy(myTask.getMyImages().get(taskB[task4].getName())[0]);
							//answer[x] = new ImageBox(new Color(0, 0, 0, 255), new Font("Arial", Font.BOLD, 16), 0, 0, 500, 500, "PATATE: " + taskB[task4].getName(), "sentence4");
						break;
					}
		}
		
		public void dispatchDTNback (int x, int task1, int task2, int task3, int task4)
		{
			
			String[] word;
			if (myTask.getTypeNback() == "matching")
				word = new String[]{Langue.translate(new String[] {"exempleNback", "DM1"}), Langue.translate(new String[] {"exempleNback", "DM2"}), Langue.translate(new String[] {"exempleNback", "DM3"})};
			else
				word = new String[]{Langue.translate(new String[] {"exempleNback", "retrieval"}) + ActualStimulus[task1].getName() + " + "  + ActualOtherStimulus[task1].getName(), Langue.translate(new String[] {"exempleNback", "SP2"}) + ActualStimulus[task2].getName()+ " + "  + ActualOtherStimulus[task2].getName(),Langue.translate(new String[] {"exempleNback", "SP3"})+ ActualStimulus[task3].getName()+ " + "  + ActualOtherStimulus[task3].getName()};

			
				switch(x){
			
				case 1:
					tempImageBoxes[x] =  ImageBox.copy(myTask.getMyImages().get(ActualStimulus[task1].getName())[0]);
					tempImageBoxes[x+4] =  ImageBox.copy(myTask.getMyImages().get(ActualOtherStimulus[task1].getName())[0]);
					answer[x] = new ImageBox(new Color(0, 0, 0, 255), new Font("Arial", Font.BOLD, 16), 0, 0, 500, 500, word[0] , "sentence1");
				break;
				case 2:
					tempImageBoxes[x] =  ImageBox.copy(myTask.getMyImages().get(ActualStimulus[task2].getName())[0]);
					tempImageBoxes[x+4] =  ImageBox.copy(myTask.getMyImages().get(ActualOtherStimulus[task2].getName())[0]);
					answer[x] = new ImageBox(new Color(0, 0, 0, 255), new Font("Arial", Font.BOLD, 16), 0, 0, 500, 500, word[1] , "sentence2");
			
				break;
				case 3:
					tempImageBoxes[x] =  ImageBox.copy(myTask.getMyImages().get(ActualStimulus[task3].getName())[0]);
					tempImageBoxes[x+4] =  ImageBox.copy(myTask.getMyImages().get(ActualOtherStimulus[task3].getName())[0]);
					answer[x] = new ImageBox(new Color(0, 0, 0, 255), new Font("taskA", Font.BOLD, 16), 0, 0, 500, 500, word[2] , "sentence3");
			
				break;
				case 4:
					tempImageBoxes[x] =  ImageBox.copy(myTask.getMyImages().get(ActualStimulus[task4].getName())[0]);
					tempImageBoxes[x+4] =  ImageBox.copy(myTask.getMyImages().get(ActualOtherStimulus[task4].getName())[0]);
					//answer[x] = new ImageBox(new Color(0, 0, 0, 255), new Font("Arial", Font.BOLD, 16), 0, 0, 500, 500, "Réponse: " + ActualStimulus[task4].getName()+ " + "  + ActualOtherStimulus[task4].getName(), "sentence4");
				break;
			}
		}
		
		public void run(){

			if(timerCall == 5){
				timerCall = 0;
				removeFromScreen(answer);
				removeFromScreen(tempImageBoxes);
				removeFromScreen(myTask.getBlackSquare());				
			}else{
				
				if(timerCall > 0)
				{
					x = timerCall * 70 + Main.getInstance().getBigPanel().getWidth()/6 + 20;		
					y = timerCall * 70 + Main.getInstance().getBigPanel().getHeight()/6 + 20;
					x2 = 180 + timerCall * 70 + Main.getInstance().getBigPanel().getWidth()/6 + 30;
					y2 = 35 + timerCall * 70 + Main.getInstance().getBigPanel().getHeight()/6 + 30;
					myTask.getBlackSquare()[timerCall-1].setProperties(x, y, true);
				}
				if(nbStimulus == 2){
					if(myTask.lookNextStim().getSoloStimulus().getSPG_SPD_SM_DM() == "SPG"){ 
						dispatchNback(timerCall, 0,1,1,0, ActualStimulus, ActualStimulus);
					}
					if( myTask.lookNextStim().getSoloOtherStimulus().getSPG_SPD_SM_DM() == "SPD"){ 
						dispatchNback(timerCall, 0,1,1,0, ActualOtherStimulus, ActualOtherStimulus);
					}
					if(myTask.lookNextStim().getSoloStimulus().getSPG_SPD_SM_DM() == "SM"){
						dispatchNback(timerCall, 0,1,1,0, ActualStimulus, ActualOtherStimulus);
					}
					if(myTask.lookNextStim().getSoloStimulus().getSPG_SPD_SM_DM() == "DM"){
						dispatchDTNback(timerCall, 0,1,1,0);
					}
				}
				else if(nbStimulus == 3 || nbStimulus == 4){
					if(myTask.lookNextStim().getSoloStimulus().getSPG_SPD_SM_DM() == "SPG"){ 
						dispatchNback(timerCall, 0,1,2,0, ActualStimulus, ActualStimulus);
					}
					if( myTask.lookNextStim().getSoloOtherStimulus().getSPG_SPD_SM_DM() == "SPD"){ 
						dispatchNback(timerCall, 0,1,2,0, ActualOtherStimulus, ActualOtherStimulus);
					}
					if(myTask.lookNextStim().getSoloStimulus().getSPG_SPD_SM_DM() == "SM"){
						dispatchNback(timerCall, 0,1,2,0, ActualStimulus, ActualOtherStimulus);
					}
					if(myTask.lookNextStim().getSoloStimulus().getSPG_SPD_SM_DM() == "DM"){
						dispatchDTNback(timerCall, 0,1,2,0);
					}
				}
				if(timerCall  > 0)
				{
					tempImageBoxes[timerCall].setProperties(x, y, true);	
					if(myTask.lookNextStim().getSoloStimulus().getSPG_SPD_SM_DM() == "DM")
						tempImageBoxes[timerCall+4].setProperties(x, y, true);	
				}
				if(timerCall + nback > 0)
					answer[timerCall + nback].setProperties(x2, y2, true);
				//repaint();	//here
				timerCall++;
			}
		}
	}
	
	/*
	public KeyListener keyMenuListener = new KeyListener(){

		@Override
		public void keyReleased(KeyEvent e) {
			char key = Character.toLowerCase(e.getKeyChar()) ;
			//les bonnes r�ponses
			if(key == myTask.getMySlide().getExpectedKey(0)){
				Main.getInstance().removeKeyListener(this);
				//instructions
				try{			//Changer cela de place??? ou mettre condition
					nbackTimer.cancel();
					nbackTimer.purge();
				}catch(IllegalStateException err){}
				timerCall = 0;
				myTask.getNextSlide(key);
				paintSlide();
			}
			// les retours dans l'instructions
			else if(key == '\b'){
				Main.getInstance().removeKeyListener(this);

				myTask.getPreviousSlide(key);
				
				try{			//Changer cela de place??? ou mettre condition
					nbackTimer.cancel();
					nbackTimer.purge();
				}catch(IllegalStateException err){
					
				}
				timerCall = 0;
				
				paintSlide();
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}
	};*/
	
	class keyMenuListener implements KeyListener {
		private String sync;
		public keyMenuListener (String sync)
		{
			this.sync = sync;
		}		

		@Override
		public void keyReleased(KeyEvent e) {
			char key = Character.toLowerCase(e.getKeyChar()) ;
			//les bonnes r�ponses
			if(key == myTask.getMySlide().getExpectedKey(0)){
				if (this.sync != "")
				{
					Task.syncTime = System.currentTimeMillis();
				}
				Main.getInstance().removeKeyListener(this);
				//instructions
				try{			//Changer cela de place??? ou mettre condition
					nbackTimer.cancel();
					nbackTimer.purge();
				}catch(IllegalStateException err){}
				timerCall = 0;
				myTask.getNextSlide(key);
				paintSlide();
			}
			
			
			// les retours dans l'instructions
			else if(key == '\b'){
				Main.getInstance().removeKeyListener(this);

				myTask.getPreviousSlide(key);
				
				try{			//Changer cela de place??? ou mettre condition
					nbackTimer.cancel();
					nbackTimer.purge();
				}catch(IllegalStateException err){
					
				}
				timerCall = 0;
				
				paintSlide();
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}
	};
	
	
	class ClickListener implements MouseListener {
		private ImageBox box;
		
		public ClickListener(ImageBox myBox){
			box = myBox;
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			// TODO Auto-generated method stub

			if(myTask.getMySlide().getSlideName() == "keyDetailedExplanation"){

				if((box.getX() < event.getX()) && ((box.getX() + box.getWidth()) > event.getX()) && (box.getY() < event.getY()) && ((box.getY() + box.getHeight()) > event.getY())){
					//Play sounds for audio task
					if(myTask.isStimsAreSounds())
						myTask.getMySounds().get(ActualStimulus[index].getName()).playMe();

				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	
	class TaskListener implements KeyListener {
		private char expectedKey ;
		private String expectedKeys,nearKeys ,otherNearKeys = "";
		private String ignore = "";
		private Stimulus stimulus, otherStim = null;

		
		private ImageBox label, otherLabel;

		private long time = 0;
		
		public long getTime() {
			return time;
		}

		//DM
		public TaskListener (char expectedKey, String expectedKeys, String nearKeys, Stimulus stimulus, ImageBox label, String ignore, Stimulus otherStim, ImageBox otherLabel, long time){
			this.expectedKey =expectedKey;
			this.expectedKeys=expectedKeys;
			this.nearKeys=nearKeys;
			this.ignore=ignore ;
			this.stimulus = stimulus;
			this.label = label;
			this.otherStim = otherStim;
			this.time = time;
			this.otherLabel = otherLabel;
		}
		
		//SM
		public TaskListener (char expectedKey, String expectedKeys, String nearKeys, Stimulus stimulus, ImageBox label, ImageBox otherLabel, String otherNearKeys, long time){
			this.expectedKey =expectedKey;
			this.expectedKeys=expectedKeys;
			this.nearKeys=nearKeys;
			this.stimulus = stimulus;
			this.label = label;
			this.otherLabel = otherLabel;
			this.otherNearKeys = otherNearKeys;
			this.time = time;
		}
		
		//SP
		public TaskListener (char expectedKey, String expectedKeys, String nearKeys, Stimulus stimulus, ImageBox label, long time){
			this.expectedKey =expectedKey;
			this.expectedKeys=expectedKeys;
			this.nearKeys=nearKeys;
			this.stimulus = stimulus;
			this.label = label;
			this.time = time;
		}
		
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub	
			char key = Character.toLowerCase(e.getKeyChar()) ;
			//les  r�ponses qui sont pres des attendus

			if (nearKeys.contains(""+key) || key == expectedKey)
            {
				Main.getInstance().removeKeyListener(this);
				// bonne r�ponse
				if(key == expectedKey)
				{
					if (myTask.getImagerie() == "EEG" & stimulus.getMatch() == "isMatching"){Signal.sendSignal("goodAnsMatch", myTask.getImagerie());}
					if (myTask.getImagerie() == "EEG" & stimulus.getMatch() == "notMatching"){Signal.sendSignal("goodAnsNotMatch", myTask.getImagerie());}	
					writeStimInfo(true, System.currentTimeMillis() -time, System.currentTimeMillis() -Task.syncTime, key, System.currentTimeMillis(), stimulus);
					
					if (otherNearKeys.contains("" + key)){

						new Animate(new String[]{"color", "100,100,100, 255"}, 100, 100,  otherLabel, myTask.getMyGUI(), new Callable<Integer>(){ //0, 0
							public Integer call() { 
								return callbackLabelAnim(1000, otherLabel); 
							} 
						});
						
					}else{

						new Animate(new String[]{"color", "100,100,100, 255"}, 100, 100, label, myTask.getMyGUI(), new Callable<Integer>(){ 
							public Integer call() { 
								return callbackLabelAnim(1000, label); 
							} 
						});
						
					}
					
					if(this.stimulus.getIsLeft()){						
						if (this.stimulus.getSPG_SPD_SM_DM() == "DM" && myTask.getType() == "training")
						{

							Utilities.pushInArray(lastDmLeft, this.stimulus);
							barValueLeft = AnimateBar.getWeightValue(lastDmLeft);
							int barLeft = AnimateBar.getRang(AnimateBar.percentilesLeft, barValueLeft);
							this.stimulus.setBarRang(barLeft);
							GraphicEngine.updateBar1(barLeft * 20);
						}
					}else if(!this.stimulus.getIsLeft()){
						
						if (this.stimulus.getSPG_SPD_SM_DM() == "DM"  && myTask.getType() == "training" )
						{

							Utilities.pushInArray(lastDmRight, this.stimulus);
							barValueRight = AnimateBar.getWeightValue(lastDmRight);
							int barRight = AnimateBar.getRang(AnimateBar.percentilesRight, barValueRight);
							this.stimulus.setBarRang(barRight);
							GraphicEngine.updateBar2(barRight * 20);
						}
					}

				}
				//erreur
				else if (!ignore.contains(""+key))
				{
					if(! myTask.isStimsAreSounds())
						myTask.getErrorSound().playMe();
					
					if (myTask.getImagerie() == "EEG" & stimulus.getMatch() == "isMatching"){Signal.sendSignal("badAnsMatch", myTask.getImagerie());}
					if (myTask.getImagerie() == "EEG" & stimulus.getMatch() == "notMatching"){Signal.sendSignal("badAnsNotMatch", myTask.getImagerie());}

					
					writeStimInfo(false, System.currentTimeMillis() -time, System.currentTimeMillis() -Task.syncTime, key, System.currentTimeMillis(), stimulus);
					if (otherNearKeys.contains("" + key)){

						new Animate(new String[]{"color", "255,0,0, 255"}, 100, 100,  otherLabel, myTask.getMyGUI(), new Callable<Integer>(){ //0, 0
							public Integer call() { 
								return callbackLabelAnim(1000, otherLabel); 
							} 
						});
						
					}else{

						new Animate(new String[]{"color", "255,0,0, 255"}, 100, 100, label, myTask.getMyGUI(), new Callable<Integer>(){ 
							public Integer call() { 
								return callbackLabelAnim(1000, label); 
							} 
						});
						
					}
					if (!expectedKeys.contains(""+key)){
					
					//tout d�calage (erreur compris)
						isOffset = true;
						x = (int)(Main.getInstance().getBigPanel().getWidth()/2 - myTask.getOffsetString().getWidth()/2);
						y = (int)(Main.getInstance().getBigPanel().getHeight()/2 - myTask.getOffsetString().getHeight()/2) - 100;
						new Animate(new String[]{"fade",""}, 1500, x ,y, myTask.getOffsetString(), Main.getInstance().getMainPanel());
					}
				}
				writeGraphInfo(stimulus);

				if (otherStim == null || otherStim.getIsAnswered())
				{

					
					if (myTask.getStimT() == 0)
					{
						if (isOffset == true)
						{
							isi = new Timer();			
							isi.schedule(new RemoveStims (label, otherLabel), 1500, 100);
						}
						else
						{
							isi = new Timer();			
							isi.schedule(new RemoveStims (label, otherLabel), 0, 100);
						}
					}
				}
            }
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub	
		}

		/*
		public int callbackLabelAnim(){
			new Animate(new String[]{"color", "255,255,255, 255"}, 1000,0,0, label.getWidth(), label.getHeight(),  label.getMyImage(), myTask.getMyGUI());
				
			return 0;
		}

		public int callbackOtherLabelAnim(){
			new Animate(new String[]{"color", "255,255,255, 255"}, 1000,0,0, otherLabel.getWidth(), otherLabel.getHeight(),  otherLabel.getMyImage(), myTask.getMyGUI());
				
			return 0;
		}
		*/
		
	}
	
	
	public int callbackLabelAnim(int duration, ImageBox myLabel){
		new Animate(new String[]{"color", "255,255,255, 255"}, duration, myLabel, myTask.getMyGUI());
			
		return 0;
	}
	
	
	public void writeStimInfo (boolean isAcc, double rt, double rtSync, char keyPressed, long rtt, Stimulus stimulus)
	{
		if (!stimulus.getIsAnswered())
		{
			stimulus.setIsAcc(isAcc);
			stimulus.setRt(rt);
			stimulus.setKeyPressed(keyPressed);
			stimulus.setRtt(rtt);
			stimulus.setIsAnswered(true);
			stimulus.setRtSync(rtSync);	

			Bloc.setBlocActuel(stimulus.getBloc().getBlocID());
			
			//System.out.println("acc " + isAcc +" rt " + rt + " key " + keyPressed + " rtt " + rtt);<		
			//WriteLog.writing (stimulus, myTask, "data/donnees_");
		}
	}
	
	
	public void writeGraphInfo ( Stimulus stimulus)
	{
		if (stimulus.getIsLeft())
		{
			stimulus.setWeight(barValueLeft);
			stimulus.setPercentiles(AnimateBar.getPercentilesLeft());
		}
		else
		{
			stimulus.setWeight(barValueRight);
			stimulus.setPercentiles(AnimateBar.getPercentilesRight());
		}
	}

	

	
	/*class ISI extends  TimerTask {
		private int tic = 0;	
		public ISI () 
		{}
		public void run() {
			if (tic == 0){
				 GraphicEngine.clearAll();					 
			}else if (this.tic > myTask.getISI() ){
				//ici on met x juste parce ' ' = une slide s'instruction et x n'est pas ' '.... that's it
				myTask.getNextSlide('x');
				paintSlide();
				this.cancel();
			}
			this.tic++;
		}
			
	}*/
	
	class RemoveStims extends  TimerTask {
		//un tic = 100 ms
		private int tic = 0;	

		private ImageBox label, otherLabel;
		
		public RemoveStims (){}
		
		public RemoveStims (ImageBox label, ImageBox otherLabel)
		{	
			this.label = label;
			this.otherLabel = otherLabel;			
		}
		public void run() {
			if (this.tic == myTask.getStimT()){
				
				GraphicEngine.clearAll();
				if (myTask.getMySlide().getSoloStimulus() != null)
					myTask.getMySlide().getSoloStimulus().setStimRemovedSync(System.currentTimeMillis()- Task.syncTime);
				if (myTask.getMySlide().getSoloOtherStimulus() != null)
					myTask.getMySlide().getSoloOtherStimulus().setStimRemovedSync(System.currentTimeMillis()- Task.syncTime);
			
			}
			
			/*
			//test raccourci d'affichage pour Phillips
			if(myTask.getVersion() == 5 && this.tic < myTask.getAnswerT() && this.tic > myTask.getStimT()){
				if(myTask.getMySlide().getSoloStimulus().getIsAnswered()){
					this.tic = myTask.getAnswerT();
				}
			}
			*/
			
			
			if (this.tic == myTask.getAnswerT())
			{

				if ( (myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "SPG" || (myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "SM" && myTask.getMySlide().getSoloStimulus().getIsLeft())  || myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "DM") && !myTask.getMySlide().getSoloStimulus().getIsAnswered())
				{
					//label.setVisible(false);
					writeStimInfo(true, 9999.0, 9999.0, '&', System.currentTimeMillis(), myTask.getMySlide().getSoloStimulus());
					if(! myTask.isStimsAreSounds())
						myTask.getErrorSound().playMe();
					new Animate(new String[]{"color", "255,0,0, 255"}, 100, label, myTask.getMyGUI());					
					writeStimInfo(true, 9999.0, 9999.0, '&', System.currentTimeMillis(), myTask.getMySlide().getSoloStimulus());
				}
				if ((myTask.getMySlide().getSoloOtherStimulus().getSPG_SPD_SM_DM() == "SPD" || myTask.getMySlide().getSoloOtherStimulus().getSPG_SPD_SM_DM() == "DM")  && !myTask.getMySlide().getSoloOtherStimulus().getIsAnswered()) 
				{
					if(! myTask.isStimsAreSounds())
						myTask.getErrorSound().playMe();
					new Animate(new String[]{"color", "255,0,0, 255"}, 100, otherLabel, myTask.getMyGUI());
					writeStimInfo(true, 9999.0, 9999.0, '&', System.currentTimeMillis(), myTask.getMySlide().getSoloOtherStimulus());
				}
				if (myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "SM" && !myTask.getMySlide().getSoloStimulus().getIsLeft() && !myTask.getMySlide().getSoloStimulus().getIsAnswered())
				{
					if(! myTask.isStimsAreSounds())
						myTask.getErrorSound().playMe();
					new Animate(new String[]{"color", "255,0,0, 255"}, 100, otherLabel, myTask.getMyGUI());
					writeStimInfo(true, 9999.0, 9999.0, '&', System.currentTimeMillis(), myTask.getMySlide().getSoloStimulus());
				}

				Main.getInstance().removeKeyListener(taskListener);
				Main.getInstance().removeKeyListener(taskListener2);
				ImageBox tempImageBox = myTask.getCross();
				x = Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
				y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
				tempImageBox.setProperties(x, y, true);
			}
			if (this.tic == myTask.getISI())
			{				
				if (myTask.getMySlide().getSoloStimulus().getName() != "")
				{
					WriteLog.writing (myTask.getMySlide().getSoloStimulus(), myTask, "data/donnees_");
				}
				if (myTask.getMySlide().getSoloOtherStimulus().getName() != "")
				{
					//System.out.print("version" + myTask.getMySlide().getSoloOtherStimulus().getVersion());
					WriteLog.writing (myTask.getMySlide().getSoloOtherStimulus(), myTask, "data/donnees_");
				}
				GraphicEngine.clearAll();
				//ici on met x juste parce ' ' = une slide s'instruction et x n'est pas ' '.... that's it
				myTask.getNextSlide('x');
				paintSlide();
				this.cancel();

			}
			tic = tic + Task.onOff;
		}
	}
	class Pause extends  TimerTask {
		//un tic = 15000 ms
		private int tic = 0;
		private int pauseT;
		public Pause (int pauseT)
		{	
			this.pauseT = pauseT;
		}
		public void run() {
			if (this.tic == 0){
				GraphicEngine.clearAll();
				ImageBox tempImageBox = myTask.getPause();
				x = Main.getInstance().getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
				y = Main.getInstance().getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
				tempImageBox.setProperties(x, y, true);
				if (myTask.getImagerie() == "IO"){Signal.sendSignal("pause", myTask.getImagerie());}
				if (myTask.getImagerie() == "EEG"){Signal.sendSignal("pause", myTask.getImagerie());}

			}
			if (this.tic >= this.pauseT)
			{
				GraphicEngine.clearAll();
				//ici on met x juste parce ' ' = une slide s'instruction et x n'est pas ' '.... that's it
				myTask.getNextSlide('x');
				paintSlide();
				this.cancel();
			}
			tic ++;
		}
	}
	
	
	private static void prepareForNextStim(Task task)
	{
	
		if (task.lookNextStim().getSlideName() == "pause")
		{
			task.getLeftReminderString().setVisible(false);
			task.getRightReminderString().setVisible(false);
		}
		else if (task.lookNextStim().getSoloStimulus().getSPG_SPD_SM_DM() == "SPG")
		{
			task.getLeftReminderString().setVisible(true);
			task.getRightReminderString().setVisible(false);
		}
		else if (task.lookNextStim().getSoloOtherStimulus().getSPG_SPD_SM_DM() == "SPD")
		{
			task.getLeftReminderString().setVisible(false);
			task.getRightReminderString().setVisible(true);
		}
		else if  ((task.lookNextStim().getSoloStimulus().getSPG_SPD_SM_DM() == "SM" ) || (task.lookNextStim().getSoloStimulus().getSPG_SPD_SM_DM() == "DM"))
		{				
			task.getLeftReminderString().setVisible(true);
			task.getRightReminderString().setVisible(true);			
			
			if (task.lookNextStim().getSoloStimulus().getSPG_SPD_SM_DM() == "DM" && task.getType() == "training"&& task.getnBack() == 0)
			{
				AnimateBar.getAllStims(task.blocsSM, true);
				AnimateBar.setPercentiles(AnimateBar.getAllStims(task.blocsSM, true), true);
				AnimateBar.setPercentiles(AnimateBar.getAllStims(task.blocsSM, false), false);
				//set Visible Bar
				if (task.lookNextStim().getSoloStimulus().getnBack() == 0)
				{
					GraphicEngine.setVisibleBar1(true);	
					GraphicEngine.setVisibleBar2(true);
				}
			}
			if (task.getType() == "eval"){
				AnimateBar.isVisible = false;
			}
			
		}
	}
	
	private void sessionFinished(){
		String myFirstColumns;
		
		//Session terminée
		myTask.setIsCompleted(true);
		myFirstColumns = WriteLog.writeLogFirstColumn(myTask, "data/log_");
		WriteLog.writeMeans(myTask, "data/log_", myFirstColumns);
		myTask.setIsCompleted(false);
		
		System.out.println("SessionFinished");
	}

   
//--- Getter and Setter ---    
	public Graphics2D getGraphic() {
		return graphic;
	}
	public void setGraphic(Graphics2D graphic) {
		this.graphic = graphic;
	}
}




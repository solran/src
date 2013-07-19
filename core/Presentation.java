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
 * Classe qui g�re l'affichage tant des slides que des stimuli (par l'interm�diaire d'une bufferedImage)
 */
public class Presentation extends JPanel{

	private Fenetre myWindow;
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
	
	private int dimensionBlackBox;
	private int nbStimulus = 0;
	
	private KeyListener taskListener, taskListener2;

	private ImageBox feedback1, feedback2;

	
	private ArrayList<Stimulus> stim = new ArrayList<Stimulus>();	//test
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
	
	
	public Presentation(Fenetre myWindow, Task myTask){

		myWindow.hideCursor();
		
		//Initialize buffImage and graphic
		buffImage = new BufferedImage( myWindow.getBigPanel().getWidth(), myWindow.getBigPanel().getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		tempGraphic = buffImage.getGraphics();
		graphic = (Graphics2D)tempGraphic;
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		graphic.setFont(new Font("Verdana", Font.BOLD, 46));
		graphic.setColor(Color.BLACK);

	
		//Load single image
		feedback1 = new ImageBox(0, 0,  764, 314, Task.ENVIRONNEMENT + "feedback1.png", "feedback1");		//test
		feedback2 = new ImageBox(0, 0,  488, 108, Task.ENVIRONNEMENT + "feedback2.png", "feedback2");		//test	
		
		
		
		this.myWindow = myWindow;
		this.myTask = myTask;
		//myWindow.addKeyListener(keyMenuListener);
		
		//Add this JPanel to the "center" jpanel
        this.setOpaque(false);
        
        //center   to   mainPanel
        myWindow.getBigPanel().setLayout(new BorderLayout());
        myWindow.getBigPanel().add(this, BorderLayout.CENTER);      
	}
	


	public void paintSlide(){
		float progressRatio;
		ImageBox tempImageBox, tempImageBox2;

        //am�liorer plus tard
		xOrigin = myWindow.getBigPanel().getWidth()/2 - myTask.getBackground().getWidth()/2;
		if (Fenetre.ySize < 800)
			yOrigin = myWindow.getBigPanel().getHeight()/2 - myTask.getBackground().getHeight()/2 - myTask.getProgressBar().getHeight();
		else 
			yOrigin = myWindow.getBigPanel().getHeight()/2 - myTask.getBackground().getHeight()/2 - 30;

		
		ActualStimulus = new Stimulus[myTask.getStimulus()[0].getStimulusLength()];
		ActualOtherStimulus = new Stimulus[myTask.getStimulus()[0].getStimulusLength()];
		
		GraphicEngine.setModifying(true);
		GraphicEngine.clearAll();
		if(!myTask.getMySlide().isStimulus() && myTask.getMySlide().getSlideName() != "reminderExplanation" && myTask.getMySlide().getSlideName() != "countdown" && myTask.getMySlide().getSlideName() != "pause"){

			WriteLog.writeMeans( myTask, "data/log_");
			
			if (myTask.getImagerie() == "IO"){Signal.sendSignal("instruction", myTask.getImagerie());}
			
			myTask.getBackground().setProperties(xOrigin, yOrigin, true);
			
			myWindow.addKeyListener(new keyMenuListener (myTask.getMySlide().getSync()));
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
			x = myWindow.getBigPanel().getWidth()/2 - myTask.getFrontProgressBar().getWidth()/2;
			y = myWindow.getBigPanel().getHeight() - myTask.getFrontProgressBar().getHeight();
			
			
			progressRatio = (myTask.getBackProgressBar().getWidth() - 15) * ((float)Bloc.getBlocActuel() / (float)Bloc.getnbBloc());

			myTask.getBackProgressBar().setProperties(x + 170, y, true);	// 170 = width of backspace
		

			myTask.getProgressBar().setWidth((int)progressRatio);            ///(int)progressRatio );        //Animation ?        y toujours la m�me chose
            myTask.getProgressBar().setProperties(x + 175, y + 2, true);
            
            myTask.getFrontProgressBar().setProperties(x, y, true);
			myTask.getCircleProgressBar().setProperties(x + 170 + (int)progressRatio, y + 10, true);
		
			
			myTask.getLeftReminderString().setVisible(false);
			myTask.getRightReminderString().setVisible(false);
			
			//System.out.println(Bloc.getBlocActuel() + " " + Bloc.getnbBloc() + " " + progressRatio);	
		}
		
		
		if(lastState.equals("graphic")){
			
			myWindow.remove(graph);
			
			myWindow.windowPanel.setBackground(Color.BLACK);
			myWindow.windowPanel.setLayout(new GridBagLayout());
			myWindow.add(myWindow.getBigPanel());
			
			myWindow.windowPanel.setSize(new Dimension(myWindow.getX() - 100, myWindow.getY() - 100));
	      	
			lastState = "";
		}
		
		
		if (myTask.getMySlide().getSlideName() == "intro"){

				x = (int)(myWindow.getBigPanel().getWidth()/2 - myTask.getHelloString().getWidth()/2);
				y = (int)(myWindow.getBigPanel().getHeight()/2 - myTask.getHelloString().getHeight()/2);
				myTask.getHelloString().setProperties(x, y, true);				
		}
		else if (myTask.getMySlide().getSlideName() == "keyGeneralExplanation"){
				//  --	--	--	--
				//	--	--	--	--
				
				y = yOrigin + 250;
				
				for(int i=0; i < nbStimulus; i++){
					
					tempImageBox = myTask.getMySmallImages().get(ActualStimulus[i].getName())[0];
					x =(int)((myWindow.getMainPanel().getWidth())/(nbStimulus + 1)) * (i + 1) - tempImageBox.getWidth()/2 + xOrigin;
					
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
				x =(int)(myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
				y = yOrigin + 120;
				myTask.getBlackSquareSmall()[index].setProperties(x, y, true);
				tempImageBox.setProperties(x, y, true);
				
				tempImageBox =  myTask.getMyImgKeyboard().get(ActualStimulus[index].getKey());
				x =(int)(myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
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
						x =(int)(myWindow.getBigPanel().getWidth()/2 - (tempImageBox.getWidth()/2+30));
						y = yOrigin + 405;
					}
				}
				tempImageBox.setProperties(x, y, true);
		}
			
		else if (myTask.getMySlide().getSlideName() == "keySimpleMixte" || myTask.getMySlide().getSlideName() == "keyDoubleTask" ){
				//	--	--	--	-	--	--	--
				//	--	--	--		--	--	--
								
				dimensionBlackBox = 90;
				
				
				if(nbStimulus == 4)
					dimensionBlackBox = 70;
				
				
				
				for(int i=0; i < nbStimulus; i++){
					y = yOrigin + 220;
					tempImageBox = myTask.getMySmallImages().get(ActualStimulus[i].getName())[0];
					tempImageBox2 = myTask.getMySmallImages().get(ActualStimulus[i].getName())[0];
					
					
					if(nbStimulus == 4){
						x = xOrigin + (int)((myWindow.getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + 1) - tempImageBox2.getWidth()/2;
						myTask.getBlackSquareSmall()[i].setProperties(x, y, true);
						tempImageBox2.setProperties(x, y, true);				
					}else{
						x = xOrigin +(int)((myWindow.getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + 1) - tempImageBox.getWidth()/2;
						myTask.getBlackSquareSmall()[i].setProperties(x, y, true);
						tempImageBox.setProperties(x, y, true);
					}
					
					
					tempImageBox = myTask.getMyImgKeys().get(ActualStimulus[i].getKey());
					x = xOrigin + (int)((myWindow.getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + 1) - tempImageBox.getWidth()/2;
					y = yOrigin + 350;
					tempImageBox.setProperties(x, y, true);
					
				}

				x = xOrigin + (int)((myWindow.getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (nbStimulus + 1) - 40;
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
						x = xOrigin + (int)((myWindow.getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + nbStimulus + 2) - tempImageBox2.getWidth()/2;
						myTask.getBlackSquareSmall()[i + nbStimulus].setProperties(x, y, true);
						tempImageBox2.setProperties(x, y, true);				
					}else{
						x = xOrigin +  (int)((myWindow.getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + nbStimulus + 2) - tempImageBox2.getWidth()/2;
						myTask.getBlackSquareSmall()[i + nbStimulus].setProperties(x, y, true);
						tempImageBox.setProperties(x, y, true);
					}
					
					tempImageBox = myTask.getMyImgKeys().get(ActualOtherStimulus[i].getKey());
					x = xOrigin + (int)((myWindow.getMainPanel().getWidth())/(nbStimulus * 2 + 2)) * (i + nbStimulus + 2) - tempImageBox.getWidth()/2;
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
				x = myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
				y = myWindow.getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2 -100;
				tempImageBox.setProperties(x, y, true);
				
			}
		
			else if (myTask.getMySlide().getSlideName() == "pauseInstruction" || myTask.getMySlide().getSlideName() == "goodbye"){
				tempImageBox = myTask.getBrain();
				x = myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
				y = myWindow.getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
				tempImageBox.setProperties(x, y, true);
			}
		
			else if (myTask.getMySlide().getSlideName() == "allStimuli"){
				// hGap and vGap
				for(int i=0; i < nbStimulus; i++){
					for(int i2 = 0; i2 < 3; i2++){  //2

						tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[i].getName())[i2];

						x = (int)((myWindow.getMainPanel().getWidth())/(nbStimulus + 1)) * (i + 1) - tempImageBox.getWidth()/2 + xOrigin;
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
						x = (int)((myWindow.getMainPanel().getWidth())/(4)) * (1 ) - tempImageBox.getWidth()/2 + xOrigin;
						myTask.getBlackSquareSmall()[0].setProperties(x, y, true);
						tempImageBox.setProperties(x, y, true);
				}
				
				
				if(myTask.getVersion() == 5 && myTask.getnBack() == 0)
					tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[2].getName())[1];
				else
					tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[0].getName())[1];
						x = (int)((myWindow.getMainPanel().getWidth())/(4)) * (2 ) - tempImageBox.getWidth()/2 + xOrigin;
						myTask.getBlackSquareSmall()[1].setProperties(x, y, true);
						tempImageBox.setProperties(x, y, true);
						
				
						
						tempImageBox = myTask.getEqual()[0];
						x = (x + ((int)((myWindow.getMainPanel().getWidth())/(4)) * (3 ) - tempImageBox.getWidth()/2 + xOrigin))/2 ;
						tempImageBox.setProperties(x+ 25, y+20, true);
						
						x = (int)((myWindow.getMainPanel().getWidth())/(4)) * (3 ) - tempImageBox.getWidth()/2 + xOrigin;
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
					x = (int)((myWindow.getMainPanel().getWidth())/(4)) * (1 ) - tempImageBox.getWidth()/2 + xOrigin;
					myTask.getBlackSquareSmall()[2].setProperties(x, y, true);
					tempImageBox.setProperties(x, y, true);
				}	
						
				if(myTask.getVersion() == 5 && myTask.getnBack() == 0)
					tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[6].getName())[2];
				else
					tempImageBox =  myTask.getMySmallImages().get(ActualStimulus[1].getName())[2];
						x = (int)((myWindow.getMainPanel().getWidth())/(4)) * (2 ) - tempImageBox.getWidth()/2 + xOrigin;
						myTask.getBlackSquareSmall()[3].setProperties(x, y, true);
						tempImageBox.setProperties(x, y, true);
						
						tempImageBox = myTask.getEqual()[1];
						x = (x + ((int)((myWindow.getMainPanel().getWidth())/(4)) * (3 ) - tempImageBox.getWidth()/2 + xOrigin))/2 ;
						tempImageBox.setProperties(x+ 25, y+20, true);
						
						x = (int)((myWindow.getMainPanel().getWidth())/(4)) * (3 ) - tempImageBox.getWidth()/2 + xOrigin;
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
				tempImageBox =  myTask.getFeedback();
				x =(int)(myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
				y = yOrigin + 180;
				tempImageBox.setProperties(x, y, true);
				
			}
			else if (myTask.getMySlide().getSlideName() == "priorite"){
			
				double d = AnimateBar.getStimQteOrMean(Utilities.getallStim(myTask.blocsSM), true, "left", "good");
				double e = AnimateBar.getStimQteOrMean(Utilities.getallStim(myTask.blocsSM), true, "right", "good");
				double f = AnimateBar.getStimQteOrMean(myTask.getMySlide().getMyStimulus(), true, "both", "good");
				double g = AnimateBar.getStimQteOrMean(myTask.getMySlide().getMyOtherStimulus(), true, "both", "good");
				double i = (d/e)/(f/g);		
				
				if (i > 1.20)	//Philippe
				{
					SoundClip.play(myTask.getBadSound());
					Langue.setPriority("left");
					tempImageBox = myTask.getSpeed();
					x = (int)(myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
					y = yOrigin + 150;
					
					/*x = xOrigin + 400;
					y = yOrigin + 150;*/
					tempImageBox.setProperties(x, y, true);
					
					
					new Animate(new String[]{"translation", (xOrigin + 400) + "," + (yOrigin + 150)}, 2000, tempImageBox, myWindow.getMainPanel());

				}
				else if (i < 0.80)	//Philippe
				{
					SoundClip.play(myTask.getBadSound());
					Langue.setPriority("right");
					tempImageBox = myTask.getSpeed();
					x = (int)(myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
					y = yOrigin + 150;
					tempImageBox.setProperties(x, y, true);
					
					new Animate(new String[]{"translation", (xOrigin + 200) + "," + (yOrigin + 150)}, 2000, tempImageBox, myWindow.getMainPanel());

				}
				else
				{
					SoundClip.play(myTask.getGoodSound());
					Langue.setPriority("equal");
					tempImageBox =  myTask.getGood();
					x = (int)(myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2);
					y = yOrigin + 150;
					tempImageBox.setProperties(x, y, true);
					
					//philippe
					//new Animate(new String[]{"translation", (xOrigin + 200) + "," + (yOrigin + 150)}, 2000, tempImageBox, myWindow.getMainPanel());

				}
				
				myTask.getMySlide().setTxtHeader(Langue.translate("prioriteHeader"));		
				myTask.getMySlide().setTxtFooter(Langue.translate("prioriteFooter"));					
				myWindow.labelHeader.setText(myTask.getMySlide().getTxtHeader());
				myWindow.labelFooter.setText(myTask.getMySlide().getTxtFooter());				
				
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

				new Animate(new String[]{"fadein",""}, 1500, 100, myWindow.getBigPanel().getWidth()/2 - 85, myWindow.getBigPanel().getHeight()/2 - 25, 100, 100, "III", new Callable<Integer>(){ 
					public Integer call() { 
						return callback(); 
					} 
				});
				//prepareForNextSlide(myTask);
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
					if (myTask.getImagerie() == "IO"){Signal.sendSignal("SPG", myTask.getImagerie());}
			
					if (myTask.getImagerie() == "EEG")
					{
						if (myTask.getMySlide().getSoloStimulus().getMatch()== "isMatching")
						{
								if (myTask.getnBack()== 0)
									{Signal.sendSignal("0backMatch", myTask.getImagerie());}
								if (myTask.getnBack()== 1)
									{Signal.sendSignal("1backMatch", myTask.getImagerie());}
								if (myTask.getnBack()== 2)
									{Signal.sendSignal("2backMatch", myTask.getImagerie());}
								if (myTask.getnBack()== 3)
									{Signal.sendSignal("3backMatch", myTask.getImagerie());}
						}
						if (myTask.getMySlide().getSoloStimulus().getMatch()== "notMatching")
						{
								if (myTask.getnBack()== 0)
									{Signal.sendSignal("0backNotMatch", myTask.getImagerie());}
								if (myTask.getnBack()== 1)
									{Signal.sendSignal("1backNotMatch", myTask.getImagerie());}
								if (myTask.getnBack()== 2)
									{Signal.sendSignal("2backNotMatch", myTask.getImagerie());}
								if (myTask.getnBack()== 3)
									{Signal.sendSignal("3backNotMatch", myTask.getImagerie());}
						}
					}

					tempImageBox =  myTask.getMyImages().get(stim.get(count).getName())[rgen.nextInt(3)];
					x =  myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = myWindow.getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true, myTask.getMySlide().getSoloStimulus());
					
					myWindow.addKeyListener(taskListener = new TaskListener (myTask.getMySlide().getExpectedKey(0), myTask.getMyExpectedKeys(), myTask.getMYNEARKEYS(), myTask.getMySlide().getSoloStimulus(), myTask.getLeftReminderString(), System.currentTimeMillis()));
					
				}
				else if (myTask.getMySlide().getSoloOtherStimulus().getSPG_SPD_SM_DM() == "SPD")
				{
					if (myTask.getImagerie() == "IO"){Signal.sendSignal("SPD", myTask.getImagerie());}
					tempImageBox =  myTask.getMyImages().get(otherStim.get(count).getName())[rgen.nextInt(3)];
					x =  myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = myWindow.getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true, myTask.getMySlide().getSoloOtherStimulus());
					
					myWindow.addKeyListener(taskListener = new TaskListener (myTask.getMySlide().getExpectedKey(1), myTask.getMyOtherExpectedKeys(), myTask.getMYOTHERNEARKEYS(), myTask.getMySlide().getSoloOtherStimulus(), myTask.getRightReminderString(), System.currentTimeMillis()));
				}
				else if  (myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "SM" )
				{
					if (myTask.getImagerie() == "IO"){Signal.sendSignal("SM", myTask.getImagerie());}
					tempImageBox =  myTask.getMyImages().get(stim.get(count).getName())[rgen.nextInt(3)];
					x = myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = myWindow.getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true, myTask.getMySlide().getSoloStimulus());
					
					myWindow.addKeyListener(taskListener = new TaskListener (myTask.getMySlide().getExpectedKey(0), myTask.getMyExpectedKeys()+ myTask.getMyOtherExpectedKeys(), myTask.getMYNEARKEYS()+ myTask.getMYOTHERNEARKEYS(), myTask.getMySlide().getSoloStimulus(), myTask.getLeftReminderString(), myTask.getRightReminderString(), myTask.getMYOTHERNEARKEYS(), System.currentTimeMillis()));
				}				
				else if  (myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "DM" )
				{
					if (myTask.getImagerie() == "IO"){Signal.sendSignal("DM", myTask.getImagerie());}
					tempImageBox = myTask.getMyImages().get(stim.get(count).getName())[rgen.nextInt(3)];
					x = myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = myWindow.getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true, myTask.getMySlide().getSoloStimulus());
					
					tempImageBox = myTask.getMyImages().get(otherStim.get(count).getName())[rgen.nextInt(3)];
					x = myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
					y = myWindow.getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
					tempImageBox.setProperties(x, y, true, myTask.getMySlide().getSoloOtherStimulus());		
					
					myWindow.addKeyListener(taskListener = new TaskListener (myTask.getMySlide().getExpectedKey(0), myTask.getMyExpectedKeys(), myTask.getMYNEARKEYS(), myTask.getMySlide().getSoloStimulus(), myTask.getLeftReminderString(),  myTask.getMyOtherExpectedKeys(), myTask.getMySlide().getSoloOtherStimulus(), myTask.getRightReminderString(), System.currentTimeMillis()));
					myWindow.addKeyListener(taskListener2 = new TaskListener (myTask.getMySlide().getExpectedKey(1), myTask.getMyOtherExpectedKeys(), myTask.getMYOTHERNEARKEYS(), myTask.getMySlide().getSoloOtherStimulus(), myTask.getRightReminderString(), myTask.getMyExpectedKeys(), myTask.getMySlide().getSoloStimulus(), myTask.getLeftReminderString(), System.currentTimeMillis()));
				}
				
				if (myTask.getMySlide().getSoloStimulus() != null && myTask.getMySlide().getSoloStimulus().getKey() == "!")
				{   
					writeStimInfo(true, 9999.0, 9999.0, '!', System.currentTimeMillis(), myTask.getMySlide().getSoloStimulus());
					x = (int)(myWindow.getBigPanel().getWidth()/2 - myTask.getDoNotAnswer().getWidth()/2);
					y = (int)(myWindow.getBigPanel().getHeight()/2 - myTask.getDoNotAnswer().getHeight()/2) - 100;
					new Animate(new String[]{"fade",""}, 1500, x ,y, myTask.getDoNotAnswer(), myWindow.getMainPanel());
				}
				if (myTask.getMySlide().getSoloOtherStimulus() != null && myTask.getMySlide().getSoloOtherStimulus().getKey() == "!")	
				{
					writeStimInfo(true, 9999.0, 9999.0, '!', System.currentTimeMillis(), myTask.getMySlide().getSoloOtherStimulus());
					x = (int)(myWindow.getBigPanel().getWidth()/2 - myTask.getDoNotAnswer().getWidth()/2);
					y = (int)(myWindow.getBigPanel().getHeight()/2 - myTask.getDoNotAnswer().getHeight()/2) - 100;
					new Animate(new String[]{"fade",""}, 1500, x ,y, myTask.getDoNotAnswer(), myWindow.getMainPanel());
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
				myWindow.addKeyListener(new keyMenuListener (myTask.getMySlide().getSync()));


				tempImageBox = myTask.getCross();
				x = myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
				y = myWindow.getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
				tempImageBox.setProperties(x, y, true);
				

				x = (int)(myWindow.getBigPanel().getWidth()/2 - myTask.getReminderExplanation1().getWidth()/2);
				y = (int)(myWindow.getBigPanel().getHeight()/2 - myTask.getReminderExplanation1().getHeight()/2) - 100;
				
				//new Animate(new String[]{"fadein",""}, 1500, 0, myWindow.getBigPanel().getHeight(),  myTask.getReminderExplanation1(), myWindow.getMainPanel());
				new Animate(new String[]{"fadein",""}, 500, 50, (int)(myWindow.getBigPanel().getHeight()-150),  myTask.getReminderExplanation1(), myWindow.getMainPanel());
				new Animate(new String[]{"fadein",""}, 500, myWindow.getBigPanel().getWidth()/2 - myTask.getReminderExplanation2().getWidth()/2+10, myWindow.getBigPanel().getHeight()/2 - 120,  myTask.getReminderExplanation2(), myWindow.getMainPanel());
				new Animate(new String[]{"fadein",""}, 500, myWindow.getBigPanel().getWidth() - (myTask.getReminderExplanation3().getWidth()), myWindow.getBigPanel().getHeight()- 140,  myTask.getReminderExplanation3(), myWindow.getMainPanel());

				prepareForNextStim(myTask);

			}
		
			else if (myTask.getMySlide().getSlideName() == "preSpeedOverviewInstruction" || myTask.getMySlide().getSlideName() == "accOverviewInstruction" || myTask.getMySlide().getSlideName() == "postSpeedOverviewInstruction"){
				
				tempImageBox = myTask.getOverview();
				x = myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
				y = myWindow.getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
				tempImageBox.setProperties(x, y, true);			
			}
			//Philippe	
			
			else if (myTask.getMySlide().getSlideName() == "speedOverview" ){

				myWindow.windowPanel.setSize(new Dimension(myWindow.getX() - 100, myWindow.getY() - 100));
				myWindow.windowPanel.setBackground(Color.BLACK);
				myWindow.windowPanel.setLayout(new BorderLayout());
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
				      	myWindow.windowPanel.add(graph, BorderLayout.CENTER);
						myWindow.remove(myWindow.getBigPanel());

				      	myWindow.showCursor(graph);
				      	lastState = "graphic";

					} catch (Throwable t) {
						myTask.getMySlide().setTxtHeader(Langue.translate(new String[] {"overviewError", "header"}));
						myTask.getMySlide().setTxtFooter(Langue.translate(new String[] {"overviewError", "footer"}));
			            System.out.println("graphic problem: " );
			            for (StackTraceElement element : t.getStackTrace() ){
			                System.out.println(  element );
			              }
			        }
			      	myWindow.revalidate();
			      	
				
			}
			else if (myTask.getMySlide().getSlideName() == "accOverview" ){
				
				myWindow.windowPanel.setSize(new Dimension(myWindow.getX() - 100, myWindow.getY() - 100));
				myWindow.windowPanel.setBackground(Color.BLACK);
				myWindow.windowPanel.setLayout(new BorderLayout());
	  	    
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
			      	myWindow.windowPanel.add(graph, BorderLayout.CENTER);
					
			      	myWindow.remove(myWindow.getBigPanel());
			      	myWindow.showCursor(graph);
			      	lastState = "graphic";		

				} catch (Throwable t) {
					myTask.getMySlide().setTxtHeader(Langue.translate(new String[] {"overviewError", "header"}));
					myTask.getMySlide().setTxtFooter(Langue.translate(new String[] {"overviewError", "footer"}));
		            System.out.println("graphic problem: ");
		            
		            for (StackTraceElement element : t.getStackTrace() ){
		                System.out.println(  element );
		              }
		            
		        }
		      	myWindow.revalidate();
		      	
		}
		else
		{
			System.out.println ("Slide inexistante : " + myTask.getMySlide().getSlideName());	

		}
		
		
			
		
		
		
GraphicEngine.setModifying(false);	
		myWindow.labelHeader.setText(myTask.getMySlide().getTxtHeader());
		myWindow.labelFooter.setText(myTask.getMySlide().getTxtFooter());
	}
	
	
	//	Callbacks of Coutdown
	public Integer callback(){
		new Animate(new String[]{"fadein",""}, 1500, 100, myWindow.getBigPanel().getWidth()/2 - 85, myWindow.getBigPanel().getHeight()/2-25, 100, 100, " II", new Callable<Integer>(){ 
			public Integer call() { 
				return callback2(); 
			} 
		});
		return 0;
	}
	public Integer callback2(){
		new Animate(new String[]{"fadein",""}, 1500, 100, myWindow.getBigPanel().getWidth()/2 - 85, myWindow.getBigPanel().getHeight()/2-25, 100, 100, "  I", new Callable<Integer>(){ 
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
					x = timerCall * 70 + myWindow.getBigPanel().getWidth()/6 + 20;		
					y = timerCall * 70 + myWindow.getBigPanel().getHeight()/6 + 20;
					x2 = 180 + timerCall * 70 + myWindow.getBigPanel().getWidth()/6 + 30;
					y2 = 35 + timerCall * 70 + myWindow.getBigPanel().getHeight()/6 + 30;
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
				myWindow.removeKeyListener(this);
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
				myWindow.removeKeyListener(this);

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
				myWindow.removeKeyListener(this);
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
				myWindow.removeKeyListener(this);

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
				myWindow.removeKeyListener(this);
				// bonne r�ponse
				if(key == expectedKey)
				{
					
					if (myTask.getImagerie() == "EEG" & stimulus.getMatch() == "isMatching"){Signal.sendSignal("goodAnsMatch", myTask.getImagerie());}
					if (myTask.getImagerie() == "EEG" & stimulus.getMatch() == "notMatching"){Signal.sendSignal("badAnsMatch", myTask.getImagerie());}	
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
					SoundClip.play(myTask.getErrorSound());
					if (myTask.getImagerie() == "EEG" & stimulus.getMatch() == "isMatching"){Signal.sendSignal("goodAnsMatch", myTask.getImagerie());}
					if (myTask.getImagerie() == "EEG" & stimulus.getMatch() == "notMatching"){Signal.sendSignal("badAnsMatch", myTask.getImagerie());}

					/*try
					{
						myTask.getErrorSound().run();

					}
					catch ( IllegalThreadStateException error)
					{
						myTask.getErrorSound().run();
					}*/
					
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
						x = (int)(myWindow.getBigPanel().getWidth()/2 - myTask.getOffsetString().getWidth()/2);
						y = (int)(myWindow.getBigPanel().getHeight()/2 - myTask.getOffsetString().getHeight()/2) - 100;
						new Animate(new String[]{"fade",""}, 1500, x ,y, myTask.getOffsetString(), myWindow.getMainPanel());
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
			
			if(myTask.getVersion() == 5 && this.tic < myTask.getAnswerT() && this.tic > myTask.getStimT()){
				if(myTask.getMySlide().getSoloStimulus().getIsAnswered()){
					this.tic = myTask.getAnswerT();
				}
			}
			
			
			
			if (this.tic == myTask.getAnswerT())
			{

				if ( (myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "SPG" || (myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "SM" && myTask.getMySlide().getSoloStimulus().getIsLeft())  || myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "DM") && !myTask.getMySlide().getSoloStimulus().getIsAnswered())
				{
					//label.setVisible(false);
					writeStimInfo(true, 9999.0, 9999.0, '&', System.currentTimeMillis(), myTask.getMySlide().getSoloStimulus());
					SoundClip.play(myTask.getErrorSound());
					new Animate(new String[]{"color", "255,0,0, 255"}, 100, label, myTask.getMyGUI());					
					writeStimInfo(true, 9999.0, 9999.0, '&', System.currentTimeMillis(), myTask.getMySlide().getSoloStimulus());
				}
				if ((myTask.getMySlide().getSoloOtherStimulus().getSPG_SPD_SM_DM() == "SPD" || myTask.getMySlide().getSoloOtherStimulus().getSPG_SPD_SM_DM() == "DM")  && !myTask.getMySlide().getSoloOtherStimulus().getIsAnswered()) 
				{
					SoundClip.play(myTask.getErrorSound());
					new Animate(new String[]{"color", "255,0,0, 255"}, 100, otherLabel, myTask.getMyGUI());
					writeStimInfo(true, 9999.0, 9999.0, '&', System.currentTimeMillis(), myTask.getMySlide().getSoloOtherStimulus());
				}
				if (myTask.getMySlide().getSoloStimulus().getSPG_SPD_SM_DM() == "SM" && !myTask.getMySlide().getSoloStimulus().getIsLeft() && !myTask.getMySlide().getSoloStimulus().getIsAnswered())
				{
					SoundClip.play(myTask.getErrorSound());
					new Animate(new String[]{"color", "255,0,0, 255"}, 100, otherLabel, myTask.getMyGUI());
					writeStimInfo(true, 9999.0, 9999.0, '&', System.currentTimeMillis(), myTask.getMySlide().getSoloStimulus());
				}

				myWindow.removeKeyListener(taskListener);
				myWindow.removeKeyListener(taskListener2);
				ImageBox tempImageBox = myTask.getCross();
				x = myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
				y = myWindow.getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
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
				x = myWindow.getBigPanel().getWidth()/2 - tempImageBox.getWidth()/2;
				y = myWindow.getBigPanel().getHeight()/2 - tempImageBox.getHeight()/2;
				tempImageBox.setProperties(x, y, true);
				if (myTask.getImagerie() == "IO"){Signal.sendSignal("pause", myTask.getImagerie());}
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
	


   
//--- Getter and Setter ---    
	public Graphics2D getGraphic() {
		return graphic;
	}
	public void setGraphic(Graphics2D graphic) {
		this.graphic = graphic;
	}
}




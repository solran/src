package graphic;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JComponent;
import javax.swing.JPanel;

import overview.SimplifiedTask;

import core.Bloc;
import core.Stimulus;
import core.Task;



public class AnimateBar {

	private static int MINMS = 100;
	private static int MAXMS = 5000;
	private JPanel panel;
	private Graphics2D graphic , tempGraphic;
	private BufferedImage buffImage;
	
	private int duration, easing;
	
	private int baseX, baseY;
	private int baseHeight = 7, baseWidth = 200;
	private int baseMargin = 20;
	private Color baseColor = new Color(255,255,255,255);
	private Color barColor1 = Color.GREEN;
	private GradientPaint gp;
	
	private boolean isLeft = true;
	
	public static boolean isVisible = true;		
	
	private int count = 0;
	private float step;
	private float stepHeight1;
	private float tempHeight1;
	private int lastHeight1 = 0, newHeight1;
		
	private int maxHeight = 120;
	private int barHeight1 = 0;
	private int barWidth = 60;
	private int barX1, barY1;

	public static double[] percentilesLeft = new double[]{0,0,0,0,0,0};
	public static double[] percentilesRight= new double[]{0,0,0,0,0,0};
	
	private boolean first = true;
	

	// ----- Constructor for first bar -----
	public AnimateBar(int baseX, int baseY, int lastHeight1, int lastHeight2, int newHeight1, int newHeight2, int duration, Graphics2D dstBuffer, JPanel panel, boolean isLeft){

		buffImage = new BufferedImage( 1000, 1000, BufferedImage.TYPE_INT_ARGB);
		tempGraphic = (Graphics2D) buffImage.getGraphics();
		tempGraphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.duration = duration;
		this.easing = 5;
		this.graphic = dstBuffer;
		this.panel = panel;
		this.isLeft = isLeft;
		
		
		
		this.baseX = baseX - baseWidth/2;
		this.baseY = baseY;

		barX1 = this.baseX + baseMargin;
		barY1 = this.baseY;

		this.newHeight1 = newHeight1;
		
		this.graphic = dstBuffer;
		this.panel = panel;
	}
		
	// for second bar
	public AnimateBar(int baseX, int baseY, int lastHeight1, int lastHeight2, int newHeight1, int newHeight2, int duration, Graphics2D dstBuffer, JPanel panel, boolean second, boolean isLeft){

		buffImage = new BufferedImage( baseWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
		tempGraphic = (Graphics2D) buffImage.getGraphics();
		tempGraphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

		this.duration = duration;
		this.easing = 5;
		this.graphic = dstBuffer;
		this.panel = panel;
		this.isLeft = isLeft;
		
		this.baseX = baseX  - baseWidth/2;
		this.baseY = baseY;

		// if second == true
		barX1 = this.baseX + baseWidth - (baseMargin + barWidth); 
		barY1 = this.baseY;
	
		this.newHeight1 = newHeight1;
		this.graphic = dstBuffer;
		this.panel = panel;
	}
	// ----- -----
	
	
	public void update(int newHeight, boolean isVisible){

		if(this.newHeight1 != newHeight){
			this.newHeight1  = newHeight;				
			count = 0;
			System.out.println("Count remis à 0 ------------------");
		}
		
		if(isVisible)
			this.refresh();
	}

	public void refresh(){
		
		if(count == 0){
			step = (float)duration/(float)easing;
			stepHeight1 = (newHeight1 - lastHeight1)/step;
			System.out.println("stepHeight: " + stepHeight1);
			System.out.println("newHeight1: " + newHeight1);
		}
		
		// count * easing > duration
		
		if(barHeight1 > newHeight1 - Math.abs(stepHeight1) && barHeight1 < newHeight1 + Math.abs(stepHeight1) ){		//end of the animation
			lastHeight1 = barHeight1 = newHeight1;

		}else{
			if(count == 0){
				tempHeight1 = barHeight1;
			}else{
				tempHeight1 += stepHeight1;
				barHeight1 =  (int)tempHeight1;
			}
		}
		
		// Clear
		tempGraphic.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, barWidth, maxHeight); 
		tempGraphic.fill(rect);
		tempGraphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		
		barColor1 = new Color(Color.HSBtoRGB((float)barHeight1/(float)maxHeight * 0.25f, 1.f, 1.f));
		gp = new GradientPaint(0 , barHeight1/2, barColor1 , 0, 0, barColor1.darker().darker(), true);                
		tempGraphic.setPaint(gp);
		tempGraphic.fillRect(0, 0, barWidth, barHeight1);

		graphic.drawImage(buffImage, barX1, barY1 - barHeight1, null);
		
		
		// ligne blanche horizontale
		if(isVisible){
			graphic.setColor(baseColor);
			graphic.fillRect(baseX, baseY, baseWidth, baseHeight);
		}
		
		count++;
	}
	

	// Static funtions for the calculation of the bars Height
	
	public static void setPercentiles (ArrayList<Double> rts, boolean isLeft)
	{
		double[] percentiles = new double[6] ;
		System.out.println ("rang : " +(int)(rts.size()*50/100));
		percentiles[5] = rts.get((int)(rts.size()*50/100))*.75;
		percentiles[4] = rts.get((int)(rts.size()*50/100))*1;
		percentiles[3] = rts.get((int)(rts.size()*50/100))*1.25;
		percentiles[2] = rts.get((int)(rts.size()*50/100))*1.50;
		percentiles[1] = rts.get((int)(rts.size()*50/100))*1.75;
		percentiles[0] = rts.get((int)(rts.size()*50/100))*2;
		
		
		//temp
		if (isLeft){
			percentilesLeft = percentiles;
			System.out.println("Percent: " + percentilesLeft);
		}else{
			percentilesRight = percentiles;
			System.out.println("Percent: " + percentilesRight);
		}
	}
	
	public static int getRang (double[] percentile, double moyenne)
	{
		int rang;
		
		if (moyenne >= percentile[0])
			rang = 1;
		else if (moyenne >= percentile[1])
			rang = 1;
		else if (moyenne >= percentile[2])
			rang = 2;
		else if (moyenne >= percentile[3])
			rang = 3;
		else if (moyenne >= percentile[4])
			rang = 4;
		else if (moyenne >= percentile[5])
			rang = 5;
		else if (moyenne < percentile[5])
			rang = 6;
		else 
			rang = 1;

		return rang;
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
		ArrayList<Double> allLeftRts = new ArrayList<Double>();
		ArrayList<Double> allRightRts = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> blocLeftAndRight = new ArrayList<ArrayList<Double>>();
		
		allStim = currentBloc.getStimulusComplet();
		for (int i = 0; i < allStim.length; i++)
		{
			if (allStim[i]!= null && allStim[i].getIsAcc()== true && 5000>allStim[i].getRt() && allStim[i].getRt()>200 && allStim[i].getIsLeft()==true)
			{
				allLeftRts.add(allStim[i].getRt());
			}
			if (allStim[i]!= null && allStim[i].getIsAcc()== true && 5000>allStim[i].getRt() && allStim[i].getRt()>200 && allStim[i].getIsLeft()==false)
			{
				allRightRts.add(allStim[i].getRt());
			}
		}
		
		blocLeftAndRight.add(allLeftRts);
		blocLeftAndRight.add(allRightRts);
	
		return blocLeftAndRight;
	}
	
	// Return all the accuracy (for each trial) separated left/right for the bloc sended in parameter
	public static ArrayList<ArrayList<Double>> getAllAccOfBloc (Bloc currentBloc)
	{
		Stimulus[] allStim;
		ArrayList<Double> allLeftRts = new ArrayList<Double>();
		ArrayList<Double> allRightRts = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> blocLeftAndRight = new ArrayList<ArrayList<Double>>();
		
		allStim = currentBloc.getStimulusComplet();
		for (int i = 0; i < allStim.length; i++)
		{
			if (allStim[i]!= null && 5000>allStim[i].getRt() && allStim[i].getRt()>200 && allStim[i].getIsLeft()==true)
			{
				if(allStim[i].getIsAcc() == true)
					allLeftRts.add(1.0);
				else
					allLeftRts.add(0.0);
			}
			if (allStim[i]!= null && 5000>allStim[i].getRt() && allStim[i].getRt()>200 && allStim[i].getIsLeft()==false)
			{
				if(allStim[i].getIsAcc() == true)
					allRightRts.add(1.0);
				else
					allRightRts.add(0.0);
			}
		}
		
		blocLeftAndRight.add(allLeftRts);
		blocLeftAndRight.add(allRightRts);
	
		return blocLeftAndRight;
	}
	
	// Return the standard deviation for the bloc sended in parameter
	public static double getStdOfBlock(ArrayList<Double> allStim){ return getStdOfBlock(allStim, 0); } //overload
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
					
					if ((seekGoodErrorLateOrAll=="all" && allStim[i].getIsAnswered())  || ((seekGoodErrorLateOrAll=="late" && allStim[i].getKeyPressed() == '&')|| (seekGoodErrorLateOrAll!="late" && allStim[i].getRt()>MINMS && MAXMS>=allStim[i].getRt()))) 

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
						
						if ((seekGoodErrorLateOrAll=="all" && allStim[i].getIsAnswered())  || ((seekGoodErrorLateOrAll=="late" && allStim[i].getKeyPressed() == '&')|| (seekGoodErrorLateOrAll!="late" && allStim[i].getRt()>MINMS && MAXMS>=allStim[i].getRt()))){ 

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
	
	
	
	
	
	
	
	public static double getWeightValue(Stimulus [] stimulus)
	{
		double values = 0;
		double weigth = 0;
		for (int i = 0; i < stimulus.length; i++)
		{
			//System.out.println ("i :" + i);

			if (stimulus[i]!= null && stimulus[i].getRt()!= 0)
			{
				//System.out.println ("i2 :" + i + "rt: " + stimulus[i].getRt() );

				weigth ++;
				values += stimulus[i].getRt()*weigth;
			}
		}
		return values/((weigth+1)*(weigth/2));
	}
	
	public static double[] getPercentilesLeft() {
		return percentilesLeft;
	}
	public static double[] getPercentilesRight() {
		return percentilesRight;
	}
}


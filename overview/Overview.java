package overview;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import utilities.Langue;


@SuppressWarnings("static-access")
public class Overview extends JPanel{
	private static final long serialVersionUID = 1L;
	
	// Buffered Image
	private BufferedImage bImage, bImage2;
	private Graphics tempGraphic, tempGraphic2;
	private Graphics2D graphic, graphic2;

	private HitBox[] hitbox;
	
	private Image imgTooltip;	
	private int firstLine = 0;
	
	private OverviewProperties myOverview;
	private String legend;
	

	
	public Overview(ArrayList<ArrayList<SimplifiedTask>> myLine, int width, int height, String legend){
		// Initialize the BufferedImage-s, load images and finally call 'drawOverview'
		this.legend = legend;

		
		//store all the overview informations in a OverviewProperties Object
		myOverview = new OverviewProperties(myLine, width, height, legend);
		
		try {
			imgTooltip = ImageIO.read(new File("images/Environnement/tooltip.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.setPreferredSize(new Dimension(myOverview.getWidth(), myOverview.getHeight()));
		this.setBackground(myOverview.getBackgroundColor());
		
		
		bImage = new BufferedImage(myOverview.getWidth(), myOverview.getHeight(), BufferedImage.TYPE_INT_ARGB);
		bImage2 = new BufferedImage(myOverview.getWidth(), myOverview.getHeight(), BufferedImage.TYPE_INT_ARGB);	
		
		tempGraphic = bImage.getGraphics();
		graphic = (Graphics2D)tempGraphic;
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		tempGraphic2 = bImage2.getGraphics();
		graphic2 = (Graphics2D)tempGraphic2;
		graphic2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphic.setFont(new Font("Verdana", Font.BOLD, 18));

		this.setLayout(null);
		
		
		//drawOverview();
	}
	
	public void drawOverview(){
		// Main function that call all the sub-functions	
		this.drawGrid();
		this.drawLegend();
		
		for(int i = 0; i < myOverview.getLine().size(); i++ )	
			this.drawGraphLine(i);

		this.repaint();
		graphic2.drawImage(bImage, 0, 0, null);
	}
	
	private void drawGrid(){
		int previous = 0;
		int xLine, yLine;
		BasicStroke smallStroke = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke bigStroke = new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

		
		//task
		for(int i = 0; i < myOverview.getTasks().length; i++){
			
	        if(i == 0){
	        	GradientPaint gp = new GradientPaint(myOverview.getX() , myOverview.getY() - 200, myOverview.getTaskColor()[myOverview.getTasks()[i] - 1],  myOverview.getX(), myOverview.getY() + myOverview.getInnerHeight()/2 + 50, Color.BLACK, true);                
				graphic.setPaint(gp);
	        	
	        	graphic.fillRect(myOverview.getX(), myOverview.getY(), myOverview.getInnerWidth()/myOverview.getNbXDivision(), myOverview.getInnerHeight());

	        	if(previous != myOverview.getTasks()[i]){
	        		graphic.setColor(Color.WHITE);
	        		graphic.drawString(Langue.translate("overviewTask") + myOverview.getTasks()[i], 10 + myOverview.getX(), myOverview.getY() + 30);
	        		HitBox hitbox = new HitBox(myOverview.getX(), myOverview.getY() + 10, 90, 30, this, false, true, myOverview.getTasks()[i] - 1, 0);
	        	}
	        }else{
	        	if(previous != myOverview.getTasks()[i]){
	        		graphic.setColor(Color.ORANGE);
	        		graphic.fillRect(10 + myOverview.getX() + i * myOverview.getInnerWidth()/myOverview.getNbXDivision(), myOverview.getY() + 30, 50, 50);
	        	}
	        	
	        	GradientPaint gp = new GradientPaint(myOverview.getX() , myOverview.getY() - 200, myOverview.getTaskColor()[myOverview.getTasks()[i] - 1],  myOverview.getX(), myOverview.getY() + myOverview.getInnerHeight()/2 + 50, Color.BLACK, true);                
				graphic.setPaint(gp);
				
	        	graphic.fillRect(myOverview.getX() + i * myOverview.getInnerWidth()/myOverview.getNbXDivision(), myOverview.getY(), myOverview.getInnerWidth()/myOverview.getNbXDivision() + 1, myOverview.getInnerHeight());
	        	
	        	if(previous != myOverview.getTasks()[i]){
	        		graphic.setColor(Color.WHITE);
	        		graphic.drawString(Langue.translate("overviewTask") + myOverview.getTasks()[i], 10 + myOverview.getX() + i * myOverview.getInnerWidth()/myOverview.getNbXDivision(), myOverview.getY() + 30);
	        		HitBox hitbox = new HitBox(myOverview.getX() + i * myOverview.getInnerWidth()/myOverview.getNbXDivision(), myOverview.getY() + 10, 90, 30, this, false, true, myOverview.getTasks()[i] - 1, 0);	
	        	}
	        }
	        previous = myOverview.getTasks()[i];
		}

		
		//Grid Lines
		graphic.setColor(myOverview.getGridColor());		
		
		
		//Y-axis
		graphic.setStroke(bigStroke);
		firstLine = myOverview.getInnerWidth()/(myOverview.getNbXDivision() * 2);
		graphic.drawLine(myOverview.getX(), myOverview.getY() - 15, myOverview.getX(), myOverview.getY() + myOverview.getInnerHeight());
		int x1[] = {myOverview.getX() - 5, myOverview.getX(), myOverview.getX() + 6};
        int y1[] = {myOverview.getY() - 15, myOverview.getY() - 8 - 15, myOverview.getY() - 15};
        graphic.fillPolygon(x1, y1, 3);

		
		graphic.setStroke(smallStroke);
		graphic.drawLine(myOverview.getX() + myOverview.getInnerWidth(), myOverview.getY(), myOverview.getX() + myOverview.getInnerWidth(), myOverview.getY() + myOverview.getInnerHeight());
		
		
		previous = 0;
		
		// Vertical Lines
		for(int i = 0; i < myOverview.getNbXDivision(); i++){	
			
			
			if(previous != myOverview.getTasks()[i] && myOverview.getNbXDivision() > 5){
        		xLine = myOverview.getX() + i * myOverview.getInnerWidth()/myOverview.getNbXDivision();			
    			graphic.drawLine(firstLine + xLine, myOverview.getY(), firstLine + xLine, myOverview.getY() + 12);   			
    			graphic.drawLine(firstLine + xLine, myOverview.getY() + 35, firstLine + xLine, myOverview.getY() + myOverview.getInnerHeight() + 10);
    			graphic.drawString(String.valueOf(i + 1), firstLine + xLine - 5, myOverview.getY() + myOverview.getInnerHeight() + 27);   			
			}else{
				xLine = myOverview.getX() + i * myOverview.getInnerWidth()/myOverview.getNbXDivision();			
				graphic.drawLine(firstLine + xLine, myOverview.getY(), firstLine + xLine, myOverview.getY() + myOverview.getInnerHeight() + 10);
				graphic.drawString(String.valueOf(i + 1), firstLine + xLine - 5, myOverview.getY() + myOverview.getInnerHeight() + 27);			
			}
			
			previous = myOverview.getTasks()[i];
		}
		
		// Horizontal Lines
		for(int i = 0; i < myOverview.getNbYDivision(); i++){	
			yLine = myOverview.getY() + i * myOverview.getInnerHeight()/myOverview.getNbYDivision();
			graphic.drawLine(myOverview.getX(), yLine, myOverview.getX() + myOverview.getInnerWidth(), yLine);
		}
		
		// X-axis
		graphic.setStroke(bigStroke);			
		firstLine = myOverview.getInnerWidth()/(myOverview.getNbXDivision() * 2);
		graphic.drawLine(myOverview.getX(), myOverview.getY() + myOverview.getInnerHeight(), myOverview.getX() + myOverview.getInnerWidth() + 15, myOverview.getY() + myOverview.getInnerHeight());
		int x2[] = {myOverview.getX() + myOverview.getInnerWidth() + 15, myOverview.getX() + myOverview.getInnerWidth() +8 +15, myOverview.getX() + myOverview.getInnerWidth() +15};
        int y2[] = {myOverview.getY() + myOverview.getInnerHeight() - 5, myOverview.getY() + myOverview.getInnerHeight(), myOverview.getY() + myOverview.getInnerHeight() + 5};
        graphic.fillPolygon(x2, y2, 3);
	}
	
	private void drawLegend(){
		graphic.setFont(new Font("Verdana", Font.BOLD, 32));
		graphic.setColor(myOverview.getTitleColor());
		graphic.drawString(Langue.translate("overview" + legend + "Title"), myOverview.getX() + myOverview.getInnerWidth()/2 - 190, myOverview.getY() - 50);
		
		graphic.setColor(myOverview.getGridColor());
		graphic.setFont(new Font("Verdana", Font.BOLD, 18));
		graphic.drawString(Langue.translate("overview" + legend + "Y"), myOverview.getX() - 40, myOverview.getY() - 25);		
		graphic.drawString(Langue.translate("overview" + legend + "X"), myOverview.getX() + myOverview.getInnerWidth() + 20, myOverview.getY() + myOverview.getInnerHeight() + 25);		
		
		// Hand Legend
		for(int i = 0; i < myOverview.getLine().size(); i++){
			graphic.setColor(myOverview.getLineColor()[i]);
			graphic.fillRect(myOverview.getX() + (int)((myOverview.getInnerWidth() - 170)/(myOverview.getLine().size() - 1) * i) , myOverview.getY() + myOverview.getInnerHeight() + 50, 50, 30);					
			graphic.drawString(myOverview.getLegend()[i], myOverview.getX() + (int)((myOverview.getInnerWidth() - 170)/(myOverview.getLine().size() - 1) * i) + 60 , myOverview.getY() + myOverview.getInnerHeight() + 75);		// Envoyer La légende en paramètre ??  Mettre ds une méthode
		}
		
		// Checkbox Legend
		if(myOverview.isCheckbox()){
			for(int i = 0; i < 5; i++){
				int x = myOverview.getX() + myOverview.getInnerWidth() + 30;
				int y = myOverview.getY() + (int)(((myOverview.getInnerHeight() - 250) * 0.875f)/4 * i) + 150;
				
				graphic.setColor(myOverview.getCheckboxColor()[i]);
				graphic.fillRect(x,y, 50, 30);					
				graphic.drawString(myOverview.getCheckboxName()[i], x + 70, y + 20);		// Envoyer La légende en paramètre ??  Mettre ds une méthode
			
				// link to hitBox to detect MouseOver and MouseClick
				HitBox hitbox = new HitBox(x, y, x + 100, y + 60, this, false, true, myOverview.getTasks()[i] - 1, 0);
	        	
			
			}
		}
	}
	
	private void drawGraphLine(int ii){
		
		int xLine, yLine;
		int lastY = 0;
		int widthPoint = 12, heightPoint = 12;
		float step;
		boolean isFirst = true;	
		
		BasicStroke s = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		graphic.setStroke(s);

		hitbox = new HitBox[myOverview.getLine().get(ii).size()];
		step = (float)(myOverview.getInnerHeight() * 0.75f)/ ((float)myOverview.getMax() - (float)myOverview.getMin());	//écart de 20% de part et d'autre	
		
		//lines //Philippe
		for(int i = 0; i < myOverview.getNbXDivision(); i++){	
			xLine = myOverview.getX() +  i * myOverview.getInnerWidth()/myOverview.getNbXDivision();
			if(myOverview.isInversed())
				yLine = (int)(myOverview.getY() + myOverview.getInnerHeight() * 0.875f) - (int)(( myOverview.getMax() - myOverview.getLine().get(ii).get(i).getMean()) * step) ;
			else
				yLine = (int)(myOverview.getY() + myOverview.getInnerHeight() * 0.875f) - (int)((myOverview.getLine().get(ii).get(i).getMean() - myOverview.getMin()) * step) ;

			//Philippe Test
			System.out.println("y: " + myOverview.getLine().get(ii).get(i).getMean());
			
			
			graphic.setColor(myOverview.getLineColor()[ii]);
			graphic.fillOval(firstLine + xLine - widthPoint/2, yLine - heightPoint/2, widthPoint, heightPoint);
			hitbox[i] = new HitBox(firstLine + xLine - widthPoint/2, yLine - heightPoint/2, widthPoint, heightPoint, this, true, false, i, ii);
			
			System.out.println("y: " + (yLine - heightPoint/2) + " score: " + myOverview.getLine().get(ii).get(i).getMean());
			
			
			if(isFirst)
				isFirst = false;
			else
				graphic.drawLine(firstLine + xLine - myOverview.getInnerWidth()/myOverview.getNbXDivision(), lastY, firstLine + xLine, yLine);

			lastY = yLine;
		}
		
	
		for(int i = 0; i < myOverview.getNbXDivision(); i++){	
			xLine = myOverview.getX() +  i * myOverview.getInnerWidth()/myOverview.getNbXDivision();
			if(myOverview.isInversed())
				yLine = (int)(myOverview.getY() + myOverview.getInnerHeight() * 0.875f) - (int)(( myOverview.getMax() - myOverview.getLine().get(ii).get(i).getMean()) * step) ;
			else
				yLine = (int)(myOverview.getY() + myOverview.getInnerHeight() * 0.875f) - (int)((myOverview.getLine().get(ii).get(i).getMean() - myOverview.getMin()) * step) ;

			graphic.setColor(Color.DARK_GRAY);
			graphic.fillOval(firstLine + xLine - (widthPoint - 6)/2, yLine - (heightPoint - 6)/2, widthPoint - 6, heightPoint - 6);
		}
	}
	
	
	public void paintComponent(Graphics g) {
		//Draw the bufferedImage in the JPanel
        super.paintComponent(g);
        g.drawImage(bImage, 0, 0, null);
	}
	
	private void clear(){
		// Clear the buffImage
		graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, 1200, 1000); 
		graphic.fill(rect);
		graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	}
	
	
	
	class HitBox extends JPanel{

		// HitBox to detect mouseOver (for each dot and Task Title)
		public HitBox(int x1, int y1, int x2, int y2, JPanel myContainer, boolean up, boolean isTask, int id, int indexOfLine){
			super();
			
			this.setOpaque(false);			
			this.setBounds(x1, y1, x2, y2);
			myContainer.add(this);
		
			this.addMouseListener(new MouseAdapter(x1, y1, up, isTask, id, indexOfLine));
		}
	}
	
	class MouseAdapter implements MouseListener{
			private int x, y, id, indexOfLine;
			private boolean up, isTask;
			private Image imgStimuli;
			
			
			public MouseAdapter(int x, int y, boolean up, boolean isTask, int id, int indexOfLine){
				super();
				this.x = x;
				this.y = y;
				this.id = id;
				this.up = up;
				this.isTask = isTask;
				this.indexOfLine = indexOfLine;
			}
			
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// draw the tooltip when hover dots or task tilte
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) );
				
				if(up){
					graphic.drawImage(imgTooltip, x - imgTooltip.getWidth(null)/2 + 5, y - imgTooltip.getHeight(null), null);
					
					graphic.setColor(Color.WHITE);
					
					if(! isTask){
						graphic.drawString(myOverview.getMsg().get(indexOfLine).get(id).get(0), x - imgTooltip.getWidth(null)/2 + 25, y - imgTooltip.getHeight(null)/6 * 4);
						graphic.drawString(myOverview.getMsg().get(indexOfLine).get(id).get(1), x - imgTooltip.getWidth(null)/2 + 25, y - imgTooltip.getHeight(null)/6 * 2);
					}
				}else{
					
					int w = imgTooltip.getWidth(null);
			        int h = imgTooltip.getHeight(null);  
			        BufferedImage dimg = new BufferedImage(w, h, ((BufferedImage) imgTooltip).getColorModel().getTransparency());  
			        Graphics2D g = dimg.createGraphics();  
			        g.drawImage(imgTooltip, 0, 0, w, h, 0, h, w, 0, null);
			        g.dispose();

			        
					graphic.drawImage(dimg, x - imgTooltip.getWidth(null)/2 + 45, y + 25, null);
					
					graphic.setColor(Color.WHITE);
					
					if(isTask){
						graphic.drawString(Langue.translate("overviewStim"), x - imgTooltip.getWidth(null)/2 + 70, y - 10 + imgTooltip.getHeight(null)/6 * 4);
						
						
						try {
							imgStimuli = ImageIO.read(new File(myOverview.getImgPath()[id][0]));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						

						graphic.setColor(Color.BLACK);
						graphic.fillRect( x - imgTooltip.getWidth(null)/2 + 70, y - 5 + imgTooltip.getHeight(null)/6 * 4, 50, 50);	//Black square   //myWindow.center.getHeight()/2 - 75 - ActualStimulus[i].getImgSmallStimulus().getMyImage().getHeight(null)/2
						graphic.drawImage(imgStimuli, x - imgTooltip.getWidth(null)/2 + 70, y - 5 + imgTooltip.getHeight(null)/6 * 4, 50, 50, null);
						
						
						try {
							imgStimuli = ImageIO.read(new File(myOverview.getImgPath()[id][1]));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						graphic.fillRect( x - imgTooltip.getWidth(null)/2 + 140, y - 5 + imgTooltip.getHeight(null)/6 * 4, 50, 50);	//Black square   //myWindow.center.getHeight()/2 - 75 - ActualStimulus[i].getImgSmallStimulus().getMyImage().getHeight(null)/2
						graphic.drawImage(imgStimuli, x - imgTooltip.getWidth(null)/2 + 140, y - 5 + imgTooltip.getHeight(null)/6 * 4, 50, 50, null);
						
					}
				}
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

				clear();
				graphic.drawImage(bImage2, 0, 0, null);
				setCursor(Cursor.getDefaultCursor());
				repaint();
			}

			
			@Override
			public void mouseClicked(MouseEvent arg0){
				
				//Checkbox pressed
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
	}
	
	
	public boolean isInversed() {
		return myOverview.isInversed();
	}
	public void setInversed(boolean inversed) {
		myOverview.setInversed(inversed);
	}

	public boolean isCheckbox() {
		return myOverview.isCheckbox();
	}
	public void setCheckbox(boolean checkbox) {
		myOverview.setCheckbox(checkbox);
	}
	
}

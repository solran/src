package graphic;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import core.Main;
import core.Stimulus;

/*
 * --- ImageBox ---
 * Classe dont les instantiations sont des JPanel dans lesquels sont dessin�s des images (stimuli, clavier, main, etc)  
 */


//Enlever extends JPanel et Cloneable
public class ImageBox{
	 
	private int width, height;
	private int width2 = 0, height2 = 0;
	private String imagePath;
	private String myText;
	private Image myImage;
	private String type;
	private Color myColor;
	private Stimulus stimulus;
	
	private Font myFont;
	
	private String name;
	private boolean visible = false;
	private int x, y;
	private float opacity = 1f;	
	
	//static private HashMap<String, ImageBox> allImage = new HashMap<String, ImageBox>();
	static private ArrayList<ImageBox> allImage = new ArrayList<ImageBox>();

	

	//Constructor for shape
	public ImageBox(Color myColor, int x, int y, int width, int height, String name){
		this.imagePath = null;
		this.myColor = myColor;
		
		this.width = width;
		this.height = height;
		
		this.name = name;
		this.x = x;
		this.y = y;

		
		allImage.add(this);		
		
		this.drawShape();
	}
	
	//Constructor for text
	public ImageBox(Color myColor, int myFont, String myText, int x, int y, int height, String name){
		this.imagePath = null;
		this.myText = myText;
		this.myColor = myColor;
		
		this.width = 0;
		this.height= height;
		this.name = name;
		this.x = x;
		this.y = y;
		this.myFont = new Font("Verdana", Font.BOLD, myFont);
		
		allImage.add(this);		
		
		this.drawText();
	}
	
	
	
	// Constructor for default image
	public ImageBox(int x, int y, int width, int height, String imagePath, String name){
		this.imagePath = imagePath;
		this.myText = "";
		
		this.width = width;
		this.height = height;
		
		this.name = name;
		this.x = x;
		this.y = y;

		allImage.add(this);		
		
		this.setImage();
	}
	
	// Constructor to resize image 
	public ImageBox(int x, int y, int width, int height, int width2, int height2, String imagePath, String name){
		this.imagePath = imagePath;
		this.myText = "";
		
		this.width = width;
		this.height = height;
		this.width2 = width2;
		this.height2 = height2;
		
		this.name = name;
		this.x = x;
		this.y = y;

		allImage.add(this);
		
		this.setImage();
	}
	public ImageBox(Color myColor, Font myFont, int x, int y, int width, int height, String myText, String name){
		
		this(myColor,  myFont,  x,  y,  width,  height, "",  myText,  name);
	}

	
	// Constructor for image with Text (key) and pop-up message
	public ImageBox(Color myColor, Font myFont, int x, int y, int width, int height, String imagePath, String myText, String name){
		this.myColor = myColor;
		this.myFont = myFont;
		this.imagePath = imagePath;
		this.myText = myText;
		
		this.width = width;
		this.height = height;
		
		this.name = name;
		this.x = x;
		this.y = y;

		allImage.add(this);
		
		this.setImage();
	}

	private void drawShape(){
		// Create empty BufferedImage
	    BufferedImage buffImage =  new BufferedImage(this.width,  this.height,  BufferedImage.TYPE_INT_ARGB);
	    Graphics g = buffImage.getGraphics();
	    Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    	                    RenderingHints.VALUE_ANTIALIAS_ON);
	    

    	
	    g2.setColor(this.myColor);
    	g2.fillRect(x, y, this.width, this.height);

		this.myImage = buffImage;	
	}
	
	
	private void drawText(){
		BufferedImage tempBuffImage =  new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	    Graphics tempG = tempBuffImage.getGraphics();
	    Graphics2D tempG2 = (Graphics2D)tempG;
    	tempG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    	                    RenderingHints.VALUE_ANTIALIAS_ON);
    	
    	
		tempG2.setFont(myFont);  //change here
				
		FontMetrics fm = tempG2.getFontMetrics(myFont); 
		int width = fm.stringWidth(this.myText);
		
		if(width == 0){
			width = 1;
		}
		
		// Create empty BufferedImage
	    BufferedImage buffImage =  new BufferedImage(width,  this.height,  BufferedImage.TYPE_INT_ARGB);
	    Graphics g = buffImage.getGraphics();
	    Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    	                    RenderingHints.VALUE_ANTIALIAS_ON);
	    
    	
    	//g2.setColor(Color.WHITE);
		g2.setColor(this.myColor);	//test
		g2.setFont(myFont);
		
		g2.drawString(this.myText, x, y + this.height/2);			// this.width/2, this.height/2


		this.width = width;
		this.myImage = buffImage;	
	}
	
	
	
	private void setImage(){
		if (this.imagePath!= "")
		{
			if(! Main.isApplet){
				System.out.println("../" + this.imagePath);
				try {
					this.myImage = ImageIO.read(new File(this.imagePath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(Main.isApplet){
			///* %!#Applet
				try {
					URL url = new URL(Main.getInstance().getDocumentBase(),"../" + this.imagePath);
					this.myImage = Main.getInstance().getImage(url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Start downloading the image and wait until it finishes loading. 
		        Main.getTracker().addImage(this.myImage, 0);
		        try { 
		        	Main.getTracker().waitForAll(); 
		        } 
		        catch(InterruptedException e) {}
		    //*/
			}
		}
		
		// Create empty BufferedImage
	    BufferedImage buffImage =  new BufferedImage(this.width,  this.height,  BufferedImage.TYPE_INT_ARGB);	//Faire un extend de Buffered image, plut�t que de cr�er une imageBox?

	    Graphics g = buffImage.getGraphics();
	    Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    	                    RenderingHints.VALUE_ANTIALIAS_ON);
	    

		if (this.imagePath!= "")
		{
			if(this.width2 != 0){  //this.stimulus || 
	    		g2.drawImage(this.myImage, 0, 0, this.width, this.height, 0, 0, this.width2, this.height2, null);
	    	}else{
	    		g2.drawImage(this.myImage, 0, 0, null);
	    	}
		}
		
        
        if(! myText.equals("")){
            g2.setFont(myFont);
            g2.setColor(this.myColor);

    	    for (String line : this.myText.split("/n")) {
    	        g.drawString(line, 0, y += g.getFontMetrics().getHeight());
    	    }
        }

		this.myImage = buffImage;
	}
	
	
	public static void clear(Graphics2D graphic, JPanel panel, double weight, double height){
		// Clear the buffImage
		graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, weight, height); 
		graphic.fill(rect);
		graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		//panel.repaint();
	}
	
	
	private void drawLineString(Graphics g, String text, int x, int y) {
	    for (String line : text.split("/n")) {
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	    }
	} 
	
	
	public static void clear(Graphics2D graphic, JPanel panel, double x, double y, double weight, double height){
		// Clear the buffImage
		graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		Rectangle2D.Double rect = new Rectangle2D.Double(x, y, weight, height); 
		graphic.fill(rect);
		graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		//panel.repaint();
	}
	
	
// ----- Getter and Setter -----
	
	public Image getMyImage() {
		return myImage;
	}
	public void setMyImage(Image myImage) {
		this.myImage = myImage;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}



	public float getOpacity() {
		return opacity;
	}
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public static ImageBox getImage(int index) {
		return allImage.get(index);
	}
	public static ArrayList<ImageBox> getAllImage() {
		return allImage;
	}
	public static void setAllImage(ArrayList<ImageBox> allImage) {
		ImageBox.allImage = allImage;
	}
	
	public String getImagePath ()
	{
		return this.imagePath;
	}

	public void setzOrder(int zOrder) {
		allImage.remove(allImage.indexOf(this));
		allImage.set(zOrder, this);			//Dangereux, peux acc�der � un �l�ment en dehors du tableau... Enlever compl�tement zOrder?
	}
	
	public void toBack(){
		allImage.remove(allImage.indexOf(this));
		allImage.set(0, this);
	}
	public void toFront(){
		allImage.remove(allImage.indexOf(this));
		allImage.add(this);
	}
	

	public String getText() {
		return myText;
	}
	public void setText(String myText) {
		this.myText = myText;
		//test
		this.drawText();
		
	}

	public void setProperties(int x, int y, boolean visible){
		this.setProperties(x,y,visible,null);
	}
	
	public void setProperties(int x, int y, boolean visible, Stimulus stimulus){
		this.x = x;
		this.y = y;
		this.visible = visible;
		this.stimulus = stimulus;
		this.toFront();
	}
	
	public Stimulus getStimulus() {
		return stimulus;
	}
	
	public static ImageBox copy(ImageBox image){
		return new ImageBox (image.getX(),image.getY(),image.getWidth(),image.getHeight(), image.getImagePath(),image.getName());
	}

}
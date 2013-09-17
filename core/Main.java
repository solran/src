package core;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


//Ctrl-H    Replace...
// pour passer à la version Desktop (sans l'espace avant la dernière lettre)
// Rechercher:     ///* %!#Apple t
// Remplacer par:  /* %!#Apple t
// Rechercher:     /* %!#Deskto p
// Remplacer par:  ///* %!#Deskto p

// pour passer à la version Applet
// Rechercher:     /* %!#Apple t
// Remplacer par:  ///* %!#Apple t
//Rechercher:     ///* %!#Deskto p
//Remplacer par:  /* %!#Deskto p


//Alternate
/* %!#Desktop
public class Main {
//*/
///* %!#Applet	
public class Main extends JApplet  {
//*/
	
	/* %!#Desktop
	public static Fenetre getInstance() {
		return Main.myWindow;
	}
	//*/
	///* %!#Applet
	public static Main getInstance() {
	    return instance;
    }
    //*/
   
	
    private static Main instance = null;
    private static MediaTracker tracker;
    public static boolean isApplet = false;
    
   
    
    
    
    public static MediaTracker getTracker() {
       return tracker;
    }
	   
    private Task myTask;
    
	public JPanel windowPanel = new JPanel();
	private Image img;
	private BufferedImage background;
	private Graphics2D g2;
	
	public static int xSize;
	public static int ySize;

	private JPanel bigPanel= new JPanel() ;
	private JPanel mainPanel = new JPanel();
	
	private JPanel header = new JPanel();
	public JPanel center = new JPanel();
	private JPanel footer = new JPanel();
	
	// Public or setter/getter ?
	public JLabel labelHeader = new JLabel();
	public JLabel labelFooter = new JLabel();

	private JLabel leftLabel = new JLabel();
	private JLabel rightLabel = new JLabel();
	private JLabel middleLabel = new JLabel();
	
	private GridBagConstraints gbc;
	private Font myFont = new Font("Verdana", Font.PLAIN, 18);
    
    
    
    // Variable pour Desktop
    private static Task myTask2;  //? une seule?
	private static Menu myMenu;
	public static Fenetre myWindow;
	
	
	// ----- DESKTOP VERSION ----- //
	public static void main(String[] args) {
		myWindow = new Fenetre();
		myMenu = new Menu();			
	}
	@SuppressWarnings("deprecation")
	public static void startDesktop(HashMap<String,Integer> myParameters){
		myMenu.getIntroSong().getClip().stop();
		myMenu.clearMenu();
		myWindow.setTask();
		myWindow.revalidate();
		myWindow.repaint();
		myWindow.requestFocus();
		
		System.out.println(myParameters);
		myTask2 = new Task(myParameters);
	}
	
	// ----- APPLET VERSION ----- //
	public void init(){
		instance = this;
		tracker = new MediaTracker(this);
		isApplet = true;


		
		
		this.setBackground(Color.BLACK);
	       
        Toolkit tk = Toolkit.getDefaultToolkit(); 
        //1000  test 900
        //800   test 740
        
		xSize = 950; //((int) tk.getScreenSize().getWidth() - 10);  
		ySize = 780; //((int) tk.getScreenSize().getHeight() - 100);  
		
		
		
		this.setSize(xSize,ySize);

		// Load Background image
		try {
			URL url = new URL(this.getDocumentBase(), "../images/Environnement/background.png");  //test ../
			img = getImage(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		bigPanel.setPreferredSize(new Dimension(xSize - 10, ySize - 30));
        bigPanel.setOpaque(false);
      
        // --- Header ---
	        labelHeader.setForeground(Color.BLACK);
	        labelHeader.setFont(myFont);
	        labelHeader.setHorizontalAlignment(SwingConstants.CENTER);
	        labelHeader.setHorizontalTextPosition(SwingConstants.RIGHT);
	        
	        header.setLayout(new BorderLayout());
	        header.setOpaque(false);
	        header.add(labelHeader, BorderLayout.CENTER);
        
        // --- Center ---
	        center.setLayout(new GridBagLayout());
	        center.setOpaque(false);

        // --- Footer ---
	        labelFooter.setForeground(Color.BLACK);
	        labelFooter.setFont(myFont);
	        labelFooter.setHorizontalAlignment(SwingConstants.CENTER);
	        labelFooter.setHorizontalTextPosition(SwingConstants.RIGHT);
	        
	        footer.setLayout(new BorderLayout());
	        footer.setOpaque(false);
	        footer.add(labelFooter, BorderLayout.CENTER);


        // Set Main Panel
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout());
        
        //améliorer plus tard
        /*if (this.ySize < 800)
        	mainPanel.setPreferredSize(new Dimension(900, 700 - 35));
        else
        	mainPanel.setPreferredSize(new Dimension(900, 700 - 35));*/
        
        mainPanel.setPreferredSize(new Dimension(850, 700-35));	//test 850

        mainPanel.setBackground(Color.RED);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(center, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(35, 25, 45, 25));
        
        bigPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,0,40,0);
        
        
        bigPanel.add(mainPanel, gbc);

        this.windowPanel.setSize(new Dimension(this.getX(), this.getY()));
        this.windowPanel.setBackground(Color.BLACK);
        this.windowPanel.setLayout(new GridBagLayout());
        
        //Set positionning
        gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 0.99;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		 this.windowPanel.add(bigPanel, gbc);
		 
    
		gbc.weighty = 0.01;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		 this.windowPanel.add(leftLabel, gbc);
	    
  		gbc.anchor = GridBagConstraints.CENTER;
  		 this.windowPanel.add(middleLabel, gbc);
	
  		gbc.anchor = GridBagConstraints.LINE_END;
  		 this.windowPanel.add(rightLabel, gbc);

  		 
   		this.setContentPane(windowPanel);
 		this.setFocusable(true);
 		
 		
 		//this.requestFocus();
 		
 		this.requestFocusInWindow();
	}
	
	
	// Get the parameters from the html param tag
	private HashMap<String, Integer> myParameters = new HashMap<String, Integer>();
	public void start() {
		
		myParameters.put("sujetID", Integer.parseInt(Main.getInstance().getParameter("sujetID")));
		myParameters.put("duree", Integer.parseInt(Main.getInstance().getParameter("duree")));
		myParameters.put("type", Integer.parseInt(Main.getInstance().getParameter("type")));
		myParameters.put("essai", Integer.parseInt(Main.getInstance().getParameter("essai")));
		
		// New
		myParameters.put("%mixed", Integer.parseInt(Main.getInstance().getParameter("%mixed")));
		
		myParameters.put("withSP", Integer.parseInt(Main.getInstance().getParameter("withSP")));
		myParameters.put("withSM", Integer.parseInt(Main.getInstance().getParameter("withSM")));
		myParameters.put("withDM", Integer.parseInt(Main.getInstance().getParameter("withDM")));

		
		myParameters.put("qte", Integer.parseInt(Main.getInstance().getParameter("qte")));
		
		myParameters.put("isTimeUnlock", Integer.parseInt(Main.getInstance().getParameter("isTimeUnlock")));
		myParameters.put("stimT", Integer.parseInt(Main.getInstance().getParameter("stimT")));
		myParameters.put("answerT", Integer.parseInt(Main.getInstance().getParameter("answerT")));
		myParameters.put("ISI", Integer.parseInt(Main.getInstance().getParameter("ISI")));
		
		myParameters.put("langue", Integer.parseInt(Main.getInstance().getParameter("langue")));
		
		myParameters.put("nBack", Integer.parseInt(Main.getInstance().getParameter("nBack")));
		myParameters.put("boxNback", Integer.parseInt(Main.getInstance().getParameter("boxNback")));
		
		
		// À enlever ???
		myParameters.put("isDT", Integer.parseInt(Main.getInstance().getParameter("isDT")));
		
		
		
		myParameters.put("format", Integer.parseInt(Main.getInstance().getParameter("format")));
		
		myParameters.put("isIO", Integer.parseInt(Main.getInstance().getParameter("isIO")));
		myParameters.put("pauseT", Integer.parseInt(Main.getInstance().getParameter("pauseT")));
		
		myParameters.put("version", Integer.parseInt(Main.getInstance().getParameter("version")));   //Détermine le set de stimuli
		
		System.out.println(myParameters);
		
		myTask = new Task(myParameters);
	}

	
	// ----- GETTER AND SETTER ----- //
	public Graphics2D getG2() {
		return g2;
	}
	public void setG2(Graphics2D g2) {
		this.g2 = g2;
	}
	
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}

	public JPanel getBigPanel() {
		return bigPanel;
	}
	public void setBigPanel(JPanel bigPanel) {
		this.bigPanel = bigPanel;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JLabel getLeftLabel() {
		return leftLabel;
	}
	public void setLeftLabel(JLabel leftLabel) {
		this.leftLabel = leftLabel;
	}

	public JLabel getRightLabel() {
		return rightLabel;
	}
	public void setRightLabel(JLabel rightLabel) {
		this.rightLabel = rightLabel;
	}

	public JLabel getMiddleLabel() {
		return middleLabel;
	}
	public void setMiddleLabel(JLabel middleLabel) {
		this.middleLabel = middleLabel;
	}
	

	public void setTask() {
		this.setContentPane(this.windowPanel);
	}

	public void hideCursor(){
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		getContentPane().setCursor(blankCursor);
	}
	public void showCursor(JPanel panel){
		//setCursor(Cursor.getDefaultCursor());
		panel.setCursor(Cursor.getDefaultCursor());
	}
	
	public void destroy(){}
	
	
}

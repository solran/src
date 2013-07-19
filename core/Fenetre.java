package core;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Point;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import overview.Overview;
import overview.SimplifiedTask;
import utilities.Langue;
import utilities.ReadLog;


/*
 * --- Fenetre ---
 * Classe contenant la fen�tre ainsi que la pr�sentation des slides...
 */

public class Fenetre extends JFrame{
	
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
	

	//hierarchie des JPanel
    //windowPanel			(toute la fen�tre)
    //---bigPanel			(le background)
    //------mainPanel		(l'espace du milieu contenant le header, center, footer)
    //---------center ()
	
	public Fenetre(){

		
		try {
			img = ImageIO.read(new File("images/Environnement/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Create empty BufferedImage
		background = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    Graphics g = background.getGraphics();
	    g2 = (Graphics2D)g;
	    g2.drawImage(img, 0, 0, null);
	    
		
		
		this.setTitle("Test Images");
        this.setBackground(Color.BLACK);
       
        Toolkit tk = Toolkit.getDefaultToolkit();  
		xSize = ((int) tk.getScreenSize().getWidth());  
		ySize = ((int) tk.getScreenSize().getHeight());  
		this.setSize(xSize,ySize);
		
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        

        bigPanel.setPreferredSize(new Dimension(this.getWidth() - 10, this.getHeight() - 30));
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
        
        ///internPanel1.setOpaque(false);
		///internPanel2.setOpaque(false);
		
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


        
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout());
        
        //am�liorer plus tard
        if (this.ySize < 800)
        	mainPanel.setPreferredSize(new Dimension(900, 700 - 35));
        else
        	mainPanel.setPreferredSize(new Dimension(900, 700 - 35));

        mainPanel.setBackground(Color.RED);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(center, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(35, 25, 45, 25));
        

        //Philippe
        bigPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,0,40,0);
        //Philippe
        
        bigPanel.add(mainPanel, gbc);

        this.windowPanel.setSize(new Dimension(this.getX(), this.getY()));
        this.windowPanel.setBackground(Color.BLACK);
        
        this.windowPanel.setLayout(new GridBagLayout());
        
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

	}

/*/
	public Integer callback(){
		Animate myAnimation2 = new Animate(new String[]{"fontSize", "72"}, 200, 10, AnimateLabel);
		return 0;
	}
//*/	

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
		//this.setVisible(true); 
	}

	
	
	public void hideCursor(){
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
	
		// Set the blank cursor to the JFrame.
		getContentPane().setCursor(blankCursor);
		
	}
	
	public void showCursor(JPanel panel){
		//setCursor(Cursor.getDefaultCursor());
		panel.setCursor(Cursor.getDefaultCursor());
	}
	
}


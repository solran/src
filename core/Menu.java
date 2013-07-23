package core;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import utilities.FiltreSimple;
import utilities.Langue;
import utilities.SoundClip;
import utilities.Utilities;





import java.awt.Toolkit;


@SuppressWarnings("serial")
public class Menu { 
	

	//Ici on set le langage de base au démarrage
	
	private JPanel mainPanel = new JPanel();

	private JPanel mainHeader = new JPanel();
	private JPanel header = new JPanel();
	private JPanel leftHeader = new JPanel();
	private JPanel rightHeader = new JPanel();
	
		private JLabel labelTitle = new JLabel();	
		private ImageIcon logo = new ImageIcon("images/Environnement/logoLescaTransparent.png");
	private JPanel header2 = new JPanel();
		private JLabel labelInput= new JLabel();
		private JTextField textInput = new JTextField();
		private JButton inputButton = new JButton ();
		
	private JPanel center = new JPanel();	
		
			private JTextField textSujetID = new JTextField("1");
			private JLabel labelSujetID= new JLabel();
			
			private JLabel radioLabelQteBlocDM= new JLabel();
			private JComboBox boxQteBlocDM = new JComboBox();			
			private JPanel panelSujetIDQteBlocDM = new JPanel();

			//private JPanel configPanel1 = new JPanel();	
			
			private JPanel radioPanelLangue = new JPanel();
			private JRadioButton radio1Langue  = new JRadioButton (),	radio2Langue = new JRadioButton ();
			private ButtonGroup bgLangue  = new ButtonGroup();	
			private JLabel radioLabelLangue = new JLabel();
						
			private JPanel radioPanelTypeQteEssai = new JPanel();
			private JLabel radioLabelType= new JLabel();
			private JLabel radioLabelQteEssai= new JLabel();
			private JComboBox boxQteEssai = new JComboBox();
			private JRadioButton radio1Type= new JRadioButton (),	radio2Type= new JRadioButton ();
			private ButtonGroup bgType = new ButtonGroup();
						
			private JPanel radioPanelVersion = new JPanel();
			private JRadioButton radio1Version= new JRadioButton (), radio2Version= new JRadioButton (), radio3Version= new JRadioButton (), radio4Version= new JRadioButton (),radio5Version= new JRadioButton ();
			private ButtonGroup bgVersion = new ButtonGroup();			
			private JLabel radioLabelVersion= new JLabel();	
			
			private JPanel radioPanelQte = new JPanel();
			private JRadioButton radio1Qte= new JRadioButton (), radio2Qte= new JRadioButton (), radio3Qte = new JRadioButton ();
			private ButtonGroup bgQte = new ButtonGroup();	
			private JLabel radioLabelQte= new JLabel();
			
			private JPanel configPanel2 = new JPanel();
			
			private JPanel radioPanelNBack = new JPanel();
			private JRadioButton radio1NBack  = new JRadioButton (),radio2NBack  = new JRadioButton (),radio3NBack = new JRadioButton (), radio4NBack = new JRadioButton ();
			private ButtonGroup bgNBack  = new ButtonGroup();	
			private JLabel radioLabelNBack = new JLabel();
			private JLabel boxLabelNback = new JLabel();
			private JComboBox boxNback = new JComboBox();
			
			private JPanel radioPanelisDT = new JPanel();
			private JCheckBox withSP = new JCheckBox ();
			private JCheckBox withSM = new JCheckBox ();
			private JCheckBox withDM = new JCheckBox ();	
			private JLabel radioLabelisDT = new JLabel();
			
			private JCheckBox checkBoxIsTimeUnlock = new JCheckBox ();
			private JLabel radioLabelIsTimeUnlock = new JLabel();
			
			private JLabel radioLabelISI= new JLabel();
			private JComboBox boxISI = new JComboBox();
			
			private JLabel radioLabelStimT= new JLabel();
			private JComboBox boxStimT = new JComboBox();
			
			private JLabel radioLabelAnswerT= new JLabel();
			private JComboBox boxAnswerT = new JComboBox();
			
			private JPanel radioPanelTiming = new JPanel();
			
			private JPanel radioPanelMixed = new JPanel();
			private JTextField textMixed = new JTextField("0");
			private JLabel radioLabelMixed = new JLabel();
			private JLabel radioLabelMixed2 = new JLabel();

			
			private JPanel radioPanelFormat = new JPanel();
			private JRadioButton radio1Format  = new JRadioButton (),radio2Format = new JRadioButton (),radio3Format = new JRadioButton ();
			private ButtonGroup bgFormat  = new ButtonGroup();	
			private JLabel radioLabelFormat = new JLabel();
			
			private JPanel radioPanelisIO = new JPanel();
			private JRadioButton radio1isIO = new JRadioButton (),radio2isIO = new JRadioButton (), radio3isIO = new JRadioButton (), radio4isIO = new JRadioButton ();
			private ButtonGroup bgisIO  = new ButtonGroup();	
			private JLabel radioLabelisIO = new JLabel();
			private JLabel boxIsIOLabel= new JLabel();
			private JComboBox boxIsIO = new JComboBox();
			
			private JPanel configPanel3 = new JPanel();

			
	private JPanel footer = new JPanel();
	
	//Philippe - 1
		private JPanel northFooter = new JPanel();
		private JPanel southFooter = new JPanel();
	//Philippe
		
		private JLabel labelOutput= new JLabel();
		private JTextField textOutput = new JTextField();
		private JButton buttonOutput = new JButton ();
		private JButton executeButton = new JButton ();
		private JLabel labelFooter= new JLabel();
		
	private Color myBlueLesca = new Color(53, 85, 143);	
	private Color myLightBlueLesca = new Color(132, 174, 224);	
	
	private HashMap<String, Integer> myInputParameters= new HashMap<String, Integer> ();
	private HashMap<String,  Integer> myParameters = new HashMap<String, Integer>();

    private KeyboardFocusManager manager;
    private KeyEventDispatcher enterListener;
    
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private Date 
	
	date = new Date();
	private JLabel dateLabel = new JLabel();
    private SoundClip introSong = new SoundClip("intro.wav");

	public Menu(){
		
		SoundClip.play(introSong);
		
 		Langue.setMenuLangue("francais");
		refreshLangue();
		
		Main.myWindow.setTitle("Programme d'entrainement cognitif");

		LinkButton francais = new LinkButton ("Français", Color.WHITE, new ChangeMenuLangage("francais"));
		LinkButton english = new LinkButton ("English", Color.WHITE, new ChangeMenuLangage("english"));
		dateLabel.setForeground(Color.WHITE);
		dateLabel.setText(dateFormat.format(this.date));

		Border empty;
		
		empty = BorderFactory.createEmptyBorder(5,0, 0, 0);	
		
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight());  
		Main.myWindow.setSize(xSize,ySize);
		Main.myWindow.setLocationRelativeTo(null);
		Main.myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Main.myWindow.setResizable(false);     
       
        Font myTitleFont = new Font("Arial", Font.BOLD, 20);
        Font myFont = new Font("Arial", Font.PLAIN, 14);
        
        //------------------------
        // ------- Header -------
        //------------------------
	        labelTitle.setFont(myTitleFont);
	        labelTitle.setForeground(myLightBlueLesca);
	        labelTitle.setIcon(logo);  
       
	        
	        textInput.setFont(myFont);
	        labelInput.setForeground(myLightBlueLesca);
	        textInput.setPreferredSize(new Dimension(300, 25));
	        inputButton.setPreferredSize(new Dimension(175, 25));
	        inputButton.addActionListener(new InputBoutonListener());
	        
	        //innerHeader.setLayout(null);
	        
	        //labelTitle.setBounds(0,0,200,200);
	        header.add(labelTitle);
	        
	        
	        header2.add(labelInput);
	        header2.add(textInput);
	        header2.add(inputButton);
	        

	        header.setBackground(myBlueLesca);
	        header2.setBackground(myBlueLesca);
	        

	        
	     
	        rightHeader.add(francais);		        
	        rightHeader.add(english);
	        leftHeader.add(dateLabel);
	        leftHeader.setBackground(myBlueLesca);
	        rightHeader.setBackground(myBlueLesca);
	        
	        //header.add(innerHeader);
	        
	        
	        mainHeader.setLayout(new BorderLayout());
	        mainHeader.add (header, BorderLayout.CENTER);  
	        
	        mainHeader.add (leftHeader, BorderLayout.WEST);
	        mainHeader.add (rightHeader, BorderLayout.EAST);
	        
	        mainHeader.add(header2, BorderLayout.SOUTH);

        
        //------------------------
        // ------- Center -------
        //------------------------
        

	        //SujetID
	        textSujetID.setPreferredSize(new Dimension(60, 30));
	        labelSujetID.setPreferredSize(new Dimension(100, 50));	
	        labelSujetID.setVerticalTextPosition(SwingConstants.TOP);
	        labelSujetID.setVerticalAlignment(SwingConstants.TOP);	        
	      
	        //QteBlocDM
	        boxQteBlocDM.setSelectedIndex(1);
	        boxQteBlocDM.setPreferredSize(new Dimension(100, 30));
	        boxQteBlocDM.addActionListener( new ComboBoxListener());
	        
	      //QteEssai
	        boxQteEssai.setSelectedIndex(0);
	        boxQteEssai.setPreferredSize(new Dimension(10, 10));
	        boxQteEssai.addActionListener( new ComboBoxListener());
	        
	        panelSujetIDQteBlocDM.setPreferredSize(new Dimension(120, 220));	
	        panelSujetIDQteBlocDM.add(labelSujetID); 	panelSujetIDQteBlocDM.add(textSujetID);	panelSujetIDQteBlocDM.add(radioLabelQteBlocDM);	panelSujetIDQteBlocDM.add(boxQteBlocDM);	
	        
	        //Config Panel 1
	       /* configPanel1.setLayout(new FlowLayout());
	        configPanel1.setPreferredSize(new Dimension(600, 70));
	        configPanel1.setBackground(myLightBlueLesca);
	        configPanel1.add(panelSujetID);	 configPanel1.add(radioPanelDuree);	
	        configPanel1.setBorder(empty);
	        center.add(configPanel1);*/
	        	        
	        // Radio Button Main
	        bgType.add(radio1Type);		bgType.add(radio2Type);		
	        radio1Type.addActionListener(new RadioListener());	radio2Type.addActionListener(new RadioListener());	
	        radio2Type.setSelected(true);
	        radioPanelTypeQteEssai.setLayout(new GridLayout(5,1));
	        radioPanelTypeQteEssai.setPreferredSize(new Dimension(150, 220));
	        
	        radioPanelTypeQteEssai.add(radioLabelType);	radioPanelTypeQteEssai.add(radio1Type);		radioPanelTypeQteEssai.add(radio2Type);	radioPanelTypeQteEssai.add(radioLabelQteEssai); radioPanelTypeQteEssai.add(boxQteEssai);
	        radioLabelType.setVerticalAlignment(SwingConstants.TOP);
	        radioLabelType.setHorizontalAlignment(SwingConstants.CENTER);

	        // Radio Button Version
	        bgVersion.add(radio1Version);		bgVersion.add(radio2Version);		bgVersion.add(radio3Version);		bgVersion.add(radio4Version);		bgVersion.add(radio5Version);
	        radio1Version.addActionListener(new RadioListener());	radio2Version.addActionListener(new RadioListener());	radio3Version.addActionListener(new RadioListener());	radio4Version.addActionListener(new RadioListener()); radio5Version.addActionListener(new RadioListener());	
	        radio1Version.setSelected(true);	        
	        radioPanelVersion.setLayout(new GridLayout(6,1));
	        radioPanelVersion.setPreferredSize(new Dimension(220, 220));
	        radioPanelVersion.add(radioLabelVersion);	radioPanelVersion.add(radio1Version);		radioPanelVersion.add(radio2Version);	radioPanelVersion.add(radio3Version);		radioPanelVersion.add(radio4Version);	radioPanelVersion.add(radio5Version);
	        radioLabelVersion.setVerticalAlignment(SwingConstants.TOP);
	        radioLabelVersion.setHorizontalAlignment(SwingConstants.CENTER);

	        
	        // Radio Button Qte
	        bgQte.add(radio1Qte);		bgQte.add(radio2Qte);		bgQte.add(radio3Qte);		
	        radio1Qte.addActionListener(new RadioListener());	radio2Qte.addActionListener(new RadioListener());	radio3Qte.addActionListener(new RadioListener());	
	        radio1Qte.setSelected(true);        
	        radioPanelQte.setLayout(new GridLayout(4,1));
	        radioPanelQte.setPreferredSize(new Dimension(250, 220));
	        radioPanelQte.add(radioLabelQte);	radioPanelQte.add(radio1Qte);	radioPanelQte.add(radio2Qte);	radioPanelQte.add(radio3Qte);		
	        radioLabelQte.setVerticalAlignment(SwingConstants.TOP);
	        radioLabelQte.setHorizontalAlignment(SwingConstants.CENTER);

	     // Radio Button TimeUnlock
	        checkBoxIsTimeUnlock.addActionListener(new CheckBoxListener());
	        checkBoxIsTimeUnlock.setSelected(true);	 
	        radioLabelIsTimeUnlock.setHorizontalAlignment(SwingConstants.CENTER);
	        radioLabelIsTimeUnlock.setVerticalAlignment(SwingConstants.TOP);
	        
	        //ISI
	        boxISI.setPreferredSize(new Dimension(100, 20));
	        boxISI.setSelectedIndex(1);
	        boxISI.addActionListener( new ComboBoxListener());
	        
	        //StimT
	        boxStimT.setPreferredSize(new Dimension(100, 20));
	        boxStimT.setSelectedIndex(0);
	        boxStimT.addActionListener( new ComboBoxListener());
	        
	        //AnswerT	//Philippe
	        boxAnswerT.setPreferredSize(new Dimension(100, 20));
	        boxAnswerT.setSelectedIndex(0);
	        boxAnswerT.addActionListener( new ComboBoxListener());
	        
	        //radioPanelTiming.setLayout(new GridLayout(8,1));
	        radioPanelTiming.setPreferredSize(new Dimension(190, 220));
	        radioPanelTiming.add(radioLabelIsTimeUnlock);	radioPanelTiming.add(checkBoxIsTimeUnlock);
	        radioPanelTiming.add(radioLabelStimT);	radioPanelTiming.add(boxStimT);	
	        radioPanelTiming.add(radioLabelAnswerT);	radioPanelTiming.add(boxAnswerT);	
	        radioPanelTiming.add(radioLabelISI);	radioPanelTiming.add(boxISI);	

	        //
	        radioPanelMixed.setPreferredSize(new Dimension(110, 220));
	        //radioPanelMixed.setLayout(new GridLayout(3,1));
	        textMixed.setPreferredSize(new Dimension(60, 30));
	        radioLabelMixed.setHorizontalAlignment(SwingConstants.CENTER);
	        radioLabelMixed.setVerticalAlignment(SwingConstants.TOP);
	        
	        radioPanelMixed.add(radioLabelMixed); 	radioPanelMixed.add(radioLabelMixed2); radioPanelMixed.add(textMixed);
	        
		    //Config Panel 2
	        configPanel2.setLayout(new FlowLayout());
	        configPanel2.setPreferredSize(new Dimension(1100, 240));
	        configPanel2.setBackground(myLightBlueLesca);
	        configPanel2.add(panelSujetIDQteBlocDM);	configPanel2.add(radioPanelTypeQteEssai);  configPanel2.add(radioPanelVersion); configPanel2.add(radioPanelQte); 	configPanel2.add(radioPanelTiming); 	configPanel2.add(radioPanelMixed); 
	        configPanel2.setBorder(empty);
	        center.add(configPanel2);
	        
	        // Radio Button langue
	        bgLangue.add(radio1Langue);		bgLangue.add(radio2Langue);			
	        radio1Langue.addActionListener(new RadioListener());	radio2Langue.addActionListener(new RadioListener());	
	        radio1Langue.setSelected(true); 
	        radioPanelLangue.setLayout(new GridLayout(3,1));
	        radioPanelLangue.setPreferredSize(new Dimension(170, 180));
	        radioPanelLangue.add(radioLabelLangue);	radioPanelLangue.add(radio1Langue);	radioPanelLangue.add(radio2Langue);	
	        radioLabelLangue.setHorizontalAlignment(SwingConstants.CENTER);
	        radioLabelLangue.setVerticalAlignment(SwingConstants.TOP);
	        
	        // Radio Button NBack
	        bgNBack.add(radio1NBack);		bgNBack.add(radio2NBack);		bgNBack.add(radio3NBack);		bgNBack.add(radio4NBack);	
	        radio1NBack.addActionListener(new RadioNBackListener());	radio2Version.addActionListener(new RadioNBackListener());	radio3Version.addActionListener(new RadioNBackListener());	
	        radio1NBack.setSelected(true);
	        boxNback.setSelectedIndex(0);
	        boxNback.setPreferredSize(new Dimension (80,20));
	        boxNback.addActionListener( new ComboBoxListener());
	        radioPanelNBack.setLayout(new GridLayout(7,1));
	        radioPanelNBack.setPreferredSize(new Dimension(210, 180));
	        radioPanelNBack.add(radioLabelNBack);	radioPanelNBack.add(radio1NBack);	radioPanelNBack.add(radio2NBack);	radioPanelNBack.add(radio3NBack); 	radioPanelNBack.add(radio4NBack);	radioPanelNBack.add(boxLabelNback); radioPanelNBack.add(boxNback);
;
	        radioLabelNBack.setVerticalAlignment(SwingConstants.TOP);
	        radioLabelNBack.setHorizontalAlignment(SwingConstants.CENTER);
	        
	        // Radio isDT  radioPanelisDT
	        
	        withSP.addActionListener(new CheckBoxListener());
	        withSP.setSelected(true);	 
	        withSM.addActionListener(new CheckBoxListener());
	        withSM.setSelected(true);
	        withDM.addActionListener(new CheckBoxListener());
	        withDM.setSelected(true);
	        radioPanelisDT.setLayout(new GridLayout(4,1));
	        radioPanelisDT.setPreferredSize(new Dimension(150, 180));
	        radioPanelisDT.add(radioLabelisDT);	radioPanelisDT.add(withSP);	radioPanelisDT.add(withSM);	radioPanelisDT.add(withDM);
	        radioLabelisDT.setVerticalAlignment(SwingConstants.TOP);
	        radioLabelisDT.setHorizontalTextPosition(SwingConstants.CENTER);
	        
	        // Radio Button Format
	        bgFormat.add(radio1Format);		bgFormat.add(radio2Format);		bgFormat.add(radio3Format);		
	        radio1Format.addActionListener(new RadioListener());	radio2Format.addActionListener(new RadioListener());	radio3Format.addActionListener(new RadioListener());	
	        radio1Format.setSelected(true);
	        radioPanelFormat.setLayout(new GridLayout(4,1));
	        radioPanelFormat.setPreferredSize(new Dimension(200, 180));
	        radioPanelFormat.add(radioLabelFormat);	radioPanelFormat.add(radio1Format);	radioPanelFormat.add(radio2Format);	radioPanelFormat.add(radio3Format);
	        radioLabelFormat.setHorizontalAlignment(SwingConstants.CENTER);
	        radioLabelFormat.setVerticalAlignment(SwingConstants.TOP);

	       
	        // Radio Button isIO        
	        bgisIO.add(radio1isIO);		bgisIO.add(radio2isIO);		bgisIO.add(radio3isIO);		bgisIO.add(radio4isIO);		
	        radio1isIO.addActionListener(new RadioListener());	radio2isIO.addActionListener(new RadioListener());	
	        radio1isIO.setSelected(true);
	        //radioPanelisIO.setLayout(new GridLayout(6,1));
	        radioPanelisIO.setPreferredSize(new Dimension(200, 180));
	        radioPanelisIO.add(radioLabelisIO);	radioPanelisIO.add(radio1isIO);	radioPanelisIO.add(radio2isIO);	radioPanelisIO.add(radio3isIO);	radioPanelisIO.add(radio4isIO);	radioPanelisIO.add(boxIsIOLabel);		radioPanelisIO.add(boxIsIO);	
	        radioLabelisIO.setHorizontalAlignment(SwingConstants.CENTER);
	        radioLabelisIO.setVerticalAlignment(SwingConstants.TOP);

	                    
		    //Config Panel 3
	        configPanel3.setLayout(new FlowLayout());
	        configPanel3.setPreferredSize(new Dimension(1000, 200));
	        configPanel3.setBackground(myLightBlueLesca);
	        configPanel3.add(radioPanelLangue); configPanel3.add(radioPanelNBack); configPanel3.add(radioPanelisDT); configPanel3.add(radioPanelFormat); configPanel3.add(radioPanelisIO); 
	        configPanel3.setBorder(empty);
	        center.add(configPanel3);  
	        
	        

	        
	        //chkEntete.addActionListener(new CheckBoxListener());
	       // center.add(chkEntete);
	        
	  //------------------------
	  // ------- Footer -------
	  //------------------------
	        
	        footer.setPreferredSize(new Dimension(700,80));

	        labelOutput.setFont(myFont);
	        labelOutput.setForeground(myLightBlueLesca);
	        textOutput.setFont(myFont);
	        textOutput.setPreferredSize(new Dimension(300, 30));
	        buttonOutput.setPreferredSize(new Dimension(160, 30));
	        buttonOutput.addActionListener(new OutputBoutonListener());
	        executeButton.setPreferredSize(new Dimension(115, 30));
	        executeButton.addActionListener(new ExecuteBoutonListener());

	        labelFooter.setFont(myFont);
	        labelFooter.setForeground(myLightBlueLesca);
	       
	        
	        footer.setLayout(new BorderLayout());
	//Philippe - 1  
	        
	        northFooter.setBackground(myBlueLesca);
	        northFooter.add(labelOutput);
	        northFooter.add(textOutput);
	        northFooter.add(buttonOutput);
	        footer.add(northFooter, BorderLayout.NORTH);

	        
	        southFooter.setBackground(myBlueLesca);      
	        southFooter.add(labelFooter);
	        southFooter.add(executeButton);
	        footer.add(southFooter, BorderLayout.SOUTH);
	 //Philippe
	        
	        /*footer.setBackground(myBlueLesca);
	        footer.setOpaque(true);*/
	        
	    mainPanel.setLayout(new BorderLayout());
	        
	    mainPanel.add(mainHeader, BorderLayout.NORTH);
	    mainPanel.add(center, BorderLayout.CENTER);
	    mainPanel.add(footer, BorderLayout.SOUTH);
        
	    Main.myWindow.setContentPane(mainPanel);
	    Main.myWindow.setVisible(true);
        
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        enterListener = new MyDispatcher();
        manager.addKeyEventDispatcher(enterListener);
        
        textSujetID.requestFocus();
	}
	
	 private class MyDispatcher implements KeyEventDispatcher {
	        @Override
	        public boolean dispatchKeyEvent(KeyEvent e) {
	            if (e.getID() == KeyEvent.KEY_PRESSED) {}
	            else if (e.getID() == KeyEvent.KEY_RELEASED) {
	    			if (e.getKeyCode() == KeyEvent.VK_ENTER)
	    			{
	    				executeButton.doClick();
	    			}
	            } else if (e.getID() == KeyEvent.KEY_TYPED) {}
	            return false;
	        }
	    }
	
	class ExecuteBoutonListener implements ActionListener{
		 
        /**
         * Red�finition de la m�thode actionPerformed
         */
		
        public void actionPerformed(ActionEvent arg0){
        	int ID = 0;
        	boolean isIDOK;
        	try { 
        		ID = Integer.parseInt(textSujetID.getText()); 
            	isIDOK= true;
            	Integer.parseInt(textMixed.getText());
    		} 
    		catch(NumberFormatException nFE) { 
    			labelFooter.setText(Langue.translate(new String[] {"execute", "warning"}, Utilities.iniHashMap ("idSujet", textSujetID.getText())))  ; 
            	isIDOK = false;
    		}
        	if (isIDOK){
	        	if(ID > 10000 || ID <0 || Integer.parseInt(textMixed.getText())<0 || Integer.parseInt(textMixed.getText())>100){
	        		labelFooter.setText(Langue.translate(new String[] {"execute", "warning"})); 
	        	}else{
		        	//set parameters
		        	myParameters.put("sujetID", Integer.parseInt(textSujetID.getText()));		
		        	verificationDesradiosEtBox();
	        		labelFooter.setText(Langue.translate(new String[] {"execute", "waiting"}));
	        		
	                manager.removeKeyEventDispatcher(enterListener);

		        	Main.startDesktop(myParameters);
	        	}
        	}
        }
	}
	
	class CheckBoxListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0){
			// TODO Auto-generated method stub
			if (checkBoxIsTimeUnlock.isSelected())
			{
				boxISI.setSelectedIndex(1);
				boxStimT.setSelectedIndex(0);
				boxAnswerT.setSelectedIndex(0);
			}
			else
			{
				boxISI.setSelectedIndex(1);
				boxStimT.setSelectedIndex(4);
				boxAnswerT.setSelectedIndex(2);
			}
			verificationDesradiosEtBox();
		}
	}
	
	class RadioNBackListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0){
			if (!radio1NBack.isSelected() )
			{
				checkBoxIsTimeUnlock.setSelected(true);
			}
			verificationDesradiosEtBox();
		}
	}
	

	
	class RadioListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0){
			// TODO Auto-generated method stub
			if (radio1isIO.isSelected())
			{
				boxIsIO.setSelectedIndex(0);
			}
			verificationDesradiosEtBox();
		}
	}
	
	class ComboBoxListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0){
			// TODO Auto-generated method stub
			if (boxStimT.getSelectedIndex() !=0 || boxAnswerT.getSelectedIndex() != 0)
				checkBoxIsTimeUnlock.setSelected(false);
			else
				checkBoxIsTimeUnlock.setSelected(true);
			verificationDesradiosEtBox();
		}
	}
	
	class OutputBoutonListener implements ActionListener{
		 
        /**
         * Red�finition de la m�thode actionPerformed
         */
        public void actionPerformed(ActionEvent arg0) {
        		
        	String folderName = "";
        		
        		
        	//---Configuration de la fen�tre pour choisir le dossier---
			FileSystemView vueSysteme = FileSystemView.getFileSystemView(); 
			File home = vueSysteme.getHomeDirectory(); 
			JFileChooser chooser = new JFileChooser(home);
        	String myFileName = "configDT";
			
			// Filtre par d�faut pour les fichiers texte
			FileFilter filtreTexte = new FiltreSimple("Fichiers textes", ".txt");
			chooser.setFileFilter(filtreTexte);
			
    	    int returnVal = chooser.showOpenDialog(null);    	  
    	    
    	    if(returnVal == JFileChooser.APPROVE_OPTION) {
    	    	folderName = chooser.getCurrentDirectory().getPath();
    	    	myFileName = chooser.getSelectedFile().getName();
    	    	
    	    	 //Faire une v�rification de la validit� du dossier !!!?!
    	    	if(myFileName.endsWith(".txt"))
    	    		textOutput.setText(folderName + "\\" +  myFileName);
    	    	else
    	    		textOutput.setText(folderName + "\\" +  myFileName + ".txt");
    	    	
	    		try
	    		{           
	    			FileWriter fic = new FileWriter(textOutput.getText(), false);
	    			PrintWriter out = new PrintWriter(fic);
	    			verificationDesradiosEtBox();
	    			//System.out.print(myParameters);
	    			out.write("" + myParameters);
	    		    // Fermeture du fichier
	    		    out.close();
	    		}
	    		catch (IOException E){Utilities.msgErreur("Le programme a généré une erreur lors de la lecture de la configuration !");
	    		}
    	    }
        }
	}
	
	class InputBoutonListener implements ActionListener{
		 
        /**
         * Redéfinition de la méthode actionPerformed
         */
        public void actionPerformed(ActionEvent arg0){
        	String folderName = "";
        	String myFileName = "resultats";
    		
    		
        	// --- Configuration de la fenétre pour choisir le dossier ---
			FileSystemView vueSysteme = FileSystemView.getFileSystemView(); 
			File home = vueSysteme.getHomeDirectory(); 
			JFileChooser chooser = new JFileChooser(home);
			
			// Filtre par défaut pour les fichiers texte
			FileFilter filtreTexte = new FiltreSimple("Fichiers textes", ".txt");
			chooser.setFileFilter(filtreTexte);
			
    	    int returnVal = chooser.showSaveDialog(null);
    	    
    	    if(returnVal == JFileChooser.APPROVE_OPTION) {
    	    	folderName = chooser.getCurrentDirectory().getPath();
    	    	myFileName = chooser.getSelectedFile().getName();
    	    	
    	    	 //Faire une vérification de la validité du dossier !!!?!
    	    	if(myFileName.endsWith(".txt"))
    	    		textInput.setText(folderName + "\\" +  myFileName);
    	    	else
    	    		textInput.setText(folderName + "\\" +  myFileName + ".txt");
    	    	
    	    	//on va chercher l'info dans le fichier et on reparti l'info

	    		try
	    		{           
	    			FileReader fichierInput = new FileReader(textInput.getText());
	    			BufferedReader in = new BufferedReader(fichierInput);
	    			String input = in.readLine().trim();
	    	    	//String input = "{TimeUnlock=False, nBack=-2, durée=45, isIO=True, format=tablette, isFrancais=True, isDT=True, type=�valuation, qte=3, version=3, sujetID=22}";
	    	    	//String input = textInput.getText();
	    			input = input.replace("{", "");		input = input.replace("}", "");
	    			String[] inputDivided = input.split(",");
	    			for (int i = 0; i<inputDivided.length; i++)
	    			{	
	    				String[] buffer = (inputDivided[i].split("="));
	    				myInputParameters.put(buffer[0].trim(), Integer.parseInt(buffer[1].trim())); 
	    			}
	    			//on place les radios en cons�quent
	    			paramSetRadio(bgType , "type", myInputParameters);
	    			paramSetRadio(bgLangue , "langue", myInputParameters);
	    			paramSetRadio(bgVersion , "version", myInputParameters);
	    			paramSetRadio(bgQte , "qte", myInputParameters);
	    			paramSetRadio(bgNBack , "nBack", myInputParameters);
	    			if (myInputParameters.get("withSP") == 0)
	    				withSP.setSelected(false);
	    			else if (myInputParameters.get("withSP") == 1)
	    				withSP.setSelected(true);
	    			if (myInputParameters.get("withSM") == 0)
	    				withSM.setSelected(false);
	    			else if (myInputParameters.get("withSM") == 1)
	    				withSM.setSelected(true);
	    			if (myInputParameters.get("withDM") == 0)
	    				withDM.setSelected(false);
	    			else if (myInputParameters.get("withDM") == 1)
	    				withDM.setSelected(true);
	    			paramSetRadio(bgFormat , "format", myInputParameters);
	    			paramSetRadio(bgisIO , "isIO", myInputParameters);
	    			if (myInputParameters.get("isTimeUnlock") == 0)
	    				checkBoxIsTimeUnlock.setSelected(false);
	    			else if (myInputParameters.get("isTimeUnlock") == 1)
	    				checkBoxIsTimeUnlock.setSelected(true);
	    			boxQteBlocDM.setSelectedIndex(myInputParameters.get("duree")-1);
	    			boxQteEssai.setSelectedIndex(myInputParameters.get("essai")-1);
	    			boxNback.setSelectedIndex(myInputParameters.get("boxNback")-1);
	    			boxISI.setSelectedIndex(myInputParameters.get("ISI")-1);
	    			boxStimT.setSelectedIndex(myInputParameters.get("stimT")-1);
	    			boxAnswerT.setSelectedIndex(myInputParameters.get("answerT")-1);
	    			textMixed.setText(""+ myInputParameters.get("%mixed"));
	    			boxIsIO.setSelectedIndex(myInputParameters.get("pauseT")-1);
	    		}
	    		// Y'a rien dans le catch pcq ca veut dire que c'est la 1ere session
	    		catch (IOException E){Utilities.msgErreur("Le programme a généré une erreur lors de la lecture de la configuration !");
	    		}
    	    }
		}
	}
	
	public void verificationDesradiosEtBox() {

			radioSetParam(bgType , "type", myParameters);
			radioSetParam(bgLangue , "langue", myParameters);
			radioSetParam(bgVersion , "version", myParameters);
			radioSetParam(bgQte , "qte", myParameters);
			radioSetParam(bgNBack , "nBack", myParameters);
			if (withSP.isSelected())
				myParameters.put("withSP", 1);	
			else 
				myParameters.put("withSP", 0);
			if (withSM.isSelected())
				myParameters.put("withSM", 1);	
			else 
				myParameters.put("withSM", 0);
			if (withDM.isSelected())
				myParameters.put("withDM", 1);	
			else 
				myParameters.put("withDM", 0);
			radioSetParam(bgFormat , "format", myParameters);
			radioSetParam(bgisIO , "isIO", myParameters);
			myParameters.put("duree", boxQteBlocDM.getSelectedIndex()+1);
			myParameters.put("essai", boxQteEssai.getSelectedIndex()+1);
			myParameters.put("boxNback", boxNback.getSelectedIndex()+1);
			myParameters.put("ISI", boxISI.getSelectedIndex()+1);
			myParameters.put("stimT", boxStimT.getSelectedIndex()+1);
			myParameters.put("answerT", boxAnswerT.getSelectedIndex()+1);
			myParameters.put("pauseT", boxIsIO.getSelectedIndex()+1);
			myParameters.put("%mixed", Integer.parseInt(textMixed.getText()));

			if (checkBoxIsTimeUnlock.isSelected())
				myParameters.put("isTimeUnlock", 1);	
			else 
				myParameters.put("isTimeUnlock", 0);

		}
	
	public static void radioSetParam(ButtonGroup buttonGroup, String tag, HashMap<String, Integer> parameters)
	{
		int i = 1;
		Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		do
		{
			if (buttons.nextElement().isSelected())
				parameters.put(tag, i);
			i++;
		}
		while (buttons.hasMoreElements());
	}
	
	public static void paramSetRadio(ButtonGroup buttonGroup, String tag,  HashMap<String, Integer> parameters)
	{
		int i = 1;
		int target = parameters.get(tag);
		Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		do
		{
			if  (target == i)
				buttons.nextElement().setSelected(true);
			else
				buttons.nextElement();
			
			i++;
		}
		while (buttons.hasMoreElements() && i <= target);
	}
	
	
	public void refreshLangue()
	{
		this.labelTitle.setText(Langue.translate("title"));	

		this.labelInput.setText(Langue.translate(new String[] {"input", "0"}));
		
		this.inputButton.setText(Langue.translate(new String[] {"input", "1"}));

		this.labelSujetID.setText(Langue.translate("sujetID"));	
			
		this.radioLabelQteBlocDM.setText(Langue.translate(new String[] {"radioDuree", "0"}));
		this.boxQteBlocDM.removeAllItems();
		this.boxQteBlocDM.addItem(Langue.translate(new String[] {"radioDuree", "1"}));
		this.boxQteBlocDM.addItem(Langue.translate(new String[] {"radioDuree", "2"}));
		this.boxQteBlocDM.addItem(Langue.translate(new String[] {"radioDuree", "3"}));
		this.boxQteBlocDM.addItem(Langue.translate(new String[] {"radioDuree", "4"}));
		this.boxQteBlocDM.addItem(Langue.translate(new String[] {"radioDuree", "5"}));
		this.boxQteBlocDM.addItem(Langue.translate(new String[] {"radioDuree", "6"}));
		
		
		this.radioLabelQteEssai.setText(Langue.translate(new String[] {"radioQteEssai", "0"}));
		this.boxQteEssai.removeAllItems();
		this.boxQteEssai.addItem(Langue.translate(new String[] {"radioQteEssai", "1"}));
		this.boxQteEssai.addItem(Langue.translate(new String[] {"radioQteEssai", "2"}));
		this.boxQteEssai.addItem(Langue.translate(new String[] {"radioQteEssai", "3"}));
		this.boxQteEssai.addItem(Langue.translate(new String[] {"radioQteEssai", "4"}));
		this.boxQteEssai.addItem(Langue.translate(new String[] {"radioQteEssai", "5"}));
		this.boxQteEssai.addItem(Langue.translate(new String[] {"radioQteEssai", "6"}));
		this.boxQteEssai.addItem(Langue.translate(new String[] {"radioQteEssai", "7"}));

		
		this.radioLabelType.setText(Langue.translate(new String[] {"radioType", "0"}));	
		this.radio1Type.setText(Langue.translate(new String[] {"radioType", "1"}));
		this.radio2Type.setText(Langue.translate(new String[] {"radioType", "2"}));
		
		this.radioLabelLangue .setText(Langue.translate(new String[] {"radioLangue", "0"}));
		this.radio1Langue .setText(Langue.translate(new String[] {"radioLangue", "1"}));
		this.radio2Langue .setText(Langue.translate(new String[] {"radioLangue", "2"}));
		
		this.radioLabelVersion.setText(Langue.translate(new String[] {"radioVersion", "0"}));
		this.radio1Version.setText(Langue.translate(new String[] {"radioVersion", "1"}));
		this.radio2Version.setText(Langue.translate(new String[] {"radioVersion", "2"}));
		this.radio3Version.setText(Langue.translate(new String[] {"radioVersion", "3"}));
		this.radio4Version.setText(Langue.translate(new String[] {"radioVersion", "4"}));
		this.radio5Version.setText(Langue.translate(new String[] {"radioVersion", "5"}));
		
		this.radioLabelQte.setText(Langue.translate(new String[] {"radioQte", "0"}));
		this.radio1Qte.setText(Langue.translate(new String[] {"radioQte", "1"}));
		this.radio2Qte.setText(Langue.translate(new String[] {"radioQte", "2"}));
		this.radio3Qte.setText(Langue.translate(new String[] {"radioQte", "3"}));

		this.radioLabelNBack .setText(Langue.translate(new String[] {"radioNBack", "0"}));
		this.radio1NBack .setText(Langue.translate(new String[] {"radioNBack", "1"}));
		this.radio2NBack .setText(Langue.translate(new String[] {"radioNBack", "2"}));
		this.radio3NBack .setText(Langue.translate(new String[] {"radioNBack", "3"}));
		this.radio4NBack .setText(Langue.translate(new String[] {"radioNBack", "4"}));
		this.boxLabelNback .setText(Langue.translate(new String[] {"radioNBack", "boxLabel"}));		
		this.boxNback.removeAllItems();
		this.boxNback.addItem(Langue.translate(new String[] {"radioNBack", "box1"}));
		this.boxNback.addItem(Langue.translate(new String[] {"radioNBack", "box2"}));
		this.boxNback.addItem(Langue.translate(new String[] {"radioNBack", "box3"}));
		
		this.radioLabelisDT .setText(Langue.translate(new String[] {"radioisDT", "info"}));
		this.withSP .setText(Langue.translate(new String[] {"radioisDT", "0"}));
		this.withSM .setText(Langue.translate(new String[] {"radioisDT", "1"}));
		this.withDM .setText(Langue.translate(new String[] {"radioisDT", "2"}));


		this.radioLabelIsTimeUnlock.setText(Langue.translate(new String[] {"radioTimeUnlock", "0"}));
		this.checkBoxIsTimeUnlock .setText(Langue.translate(new String[] {"radioTimeUnlock", "1"}));
        this.checkBoxIsTimeUnlock.setToolTipText(Langue.translate(new String[] {"radioTimeUnlock", "tooltip"}));
		
		this.radioLabelISI.setText(Langue.translate(new String[] {"radioISI", "0"}));
		this.boxISI.removeAllItems();
		this.boxISI.addItem(Langue.translate(new String[] {"radioISI", "1"}));
		this.boxISI.addItem(Langue.translate(new String[] {"radioISI", "2"}));
		this.boxISI.addItem(Langue.translate(new String[] {"radioISI", "3"}));
		this.boxISI.addItem(Langue.translate(new String[] {"radioISI", "4"}));
		this.boxISI.addItem(Langue.translate(new String[] {"radioISI", "5"}));
		this.boxISI.addItem(Langue.translate(new String[] {"radioISI", "6"}));

		this.radioLabelMixed.setText(Langue.translate(new String[] {"radioMixed", "0"}));
		this.radioLabelMixed2.setText(Langue.translate(new String[] {"radioMixed", "1"}));
		
		this.radioLabelStimT.setText(Langue.translate(new String[] {"radioStimT", "0"}));
		this.boxStimT.removeAllItems();
		this.boxStimT.addItem(Langue.translate(new String[] {"radioStimT", "1"}));
		this.boxStimT.addItem(Langue.translate(new String[] {"radioStimT", "2"}));
		this.boxStimT.addItem(Langue.translate(new String[] {"radioStimT", "3"}));
		this.boxStimT.addItem(Langue.translate(new String[] {"radioStimT", "4"}));
		this.boxStimT.addItem(Langue.translate(new String[] {"radioStimT", "5"}));
		this.boxStimT.addItem(Langue.translate(new String[] {"radioStimT", "6"}));
		this.boxStimT.addItem(Langue.translate(new String[] {"radioStimT", "7"}));
		this.boxStimT.addItem(Langue.translate(new String[] {"radioStimT", "8"}));
		this.boxStimT.addItem(Langue.translate(new String[] {"radioStimT", "9"}));
		this.boxStimT.addItem(Langue.translate(new String[] {"radioStimT", "10"}));

		
		this.radioLabelAnswerT.setText(Langue.translate(new String[] {"radioAnswerT", "0"}));
		this.boxAnswerT.removeAllItems();
		this.boxAnswerT.addItem(Langue.translate(new String[] {"radioAnswerT", "1"}));
		this.boxAnswerT.addItem(Langue.translate(new String[] {"radioAnswerT", "2"}));
		this.boxAnswerT.addItem(Langue.translate(new String[] {"radioAnswerT", "3"}));
		this.boxAnswerT.addItem(Langue.translate(new String[] {"radioAnswerT", "4"}));
		this.boxAnswerT.addItem(Langue.translate(new String[] {"radioAnswerT", "5"}));
		this.boxAnswerT.addItem(Langue.translate(new String[] {"radioAnswerT", "6"}));
		this.boxAnswerT.addItem(Langue.translate(new String[] {"radioAnswerT", "7"}));
		this.boxAnswerT.addItem(Langue.translate(new String[] {"radioAnswerT", "8"}));
		
		this.radioLabelFormat.setText(Langue.translate(new String[] {"radioFormat", "0"}));
		this.radio1Format .setText(Langue.translate(new String[] {"radioFormat", "1"}));
		this.radio2Format .setText(Langue.translate(new String[] {"radioFormat", "2"}));
		this.radio3Format .setText(Langue.translate(new String[] {"radioFormat", "3"}));
		
		this.radioLabelisIO.setText(Langue.translate(new String[] {"radioisIO", "0"}));
		this.radio1isIO .setText(Langue.translate(new String[] {"radioisIO", "1"}));
		this.radio2isIO .setText(Langue.translate(new String[] {"radioisIO", "2"}));
		this.radio3isIO .setText(Langue.translate(new String[] {"radioisIO", "3"}));
		this.radio4isIO .setText(Langue.translate(new String[] {"radioisIO", "4"}));

		
		this.boxIsIOLabel .setText(Langue.translate(new String[] {"radioisIO", "box"}));	
        this.boxIsIOLabel.setToolTipText(Langue.translate(new String[] {"radioisIO", "tooltip"}));
		this.boxIsIO.removeAllItems();
		this.boxIsIO.addItem(Langue.translate(new String[] {"radioisIO", "box1"}));
		this.boxIsIO.addItem(Langue.translate(new String[] {"radioisIO", "box2"}));
		this.boxIsIO.addItem(Langue.translate(new String[] {"radioisIO", "box3"}));
		this.boxIsIO.addItem(Langue.translate(new String[] {"radioisIO", "box4"}));
		this.boxIsIO.addItem(Langue.translate(new String[] {"radioisIO", "box5"}));
		this.boxIsIO.addItem(Langue.translate(new String[] {"radioisIO", "box6"}));	

		
		
		this.labelOutput.setText(Langue.translate(new String[] {"output", "0"}));
		this.buttonOutput.setText(Langue.translate(new String[] {"output", "1"}));
		
		this.labelFooter.setText(Langue.translate(new String[] {"execute" , "instruction"}));
		this.executeButton.setText(Langue.translate(new String[] {"execute" , "button"}));

	}
	
	class ChangeMenuLangage implements ActionListener{

		String choice;
		
		public ChangeMenuLangage (String choice){
			this.choice = choice;
		}
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
	        if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
            		Langue.setMenuLangue(this.choice);
            		refreshLangue();
                } catch (Exception ex) {
                }
	        } else {
	        }
		}
	}
	
	public class LinkButton extends JButton {
		
		public LinkButton (String myName, Color color, ActionListener action ){
			this.setText("<html><u>"+ myName + "</u></html>");
			this.setBorderPainted(false);
			this.setOpaque(false);
			this.setForeground(color);
			this.setBackground(Color.lightGray);
			this.addActionListener (action);
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}
	
	public void clearMenu()
	{
		Main.myWindow.remove(this.mainPanel);
	}
	
	public SoundClip getIntroSong() {
		return introSong;
	}
}

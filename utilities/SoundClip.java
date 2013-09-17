package utilities;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

import core.Main;
   
public class SoundClip {
	
	
	private URL u;
	private Clip clip;
	private String name = "";
	private AudioInputStream sample;
	
	public SoundClip(String path, String name)
	{
		this.name = name;
		
		if(! Main.isApplet){
			this.u = Main.getInstance().getClass().getClassLoader().getResource( "sounds/" + path);
			
			//this.u = Main.getInstance().getClass().getClassLoader().getResource( "sounds/" + path);
			//System.out.println("Intro Sound Path: " + u.getPath());
		}else if(Main.isApplet){
			///* %!#Applet
			try {
				this.u = new URL(Main.getInstance().getDocumentBase(),"../sounds/" + path);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//*/
		}
		
		
		// Preload the sounds, to avoid lag in applet mode
		try {
			 // Open an audio input stream.
		    sample = AudioSystem.getAudioInputStream(this.u);
		    // Get a sound clip resource.
		    this.setClip(AudioSystem.getClip());
		    // Open audio clip and load samples from the audio input stream.
		    this.getClip().open(sample);
		    
		    
		 } catch (UnsupportedAudioFileException e) {
			 System.out.print("playing sounds UNS");
		    e.printStackTrace();
		 } catch (IOException e) {
			 System.out.print("playing soundsIO");
		    e.printStackTrace();
		 } catch (LineUnavailableException e) {
			 System.out.print("playing soundsLIN");
		    e.printStackTrace();
	 	 } catch (NullPointerException e) {
	 		System.out.print("playing soundsNULL");
		    e.printStackTrace();
		 }
	}
	
	public void playMe(){
		play(this);
	}
	
	
	//"./sounds/"
	public void play (SoundClip sc)
	{
		sc.getClip().stop();
		sc.getClip().setFramePosition(0);
		sc.getClip().start();
	}
	
	public URL getURL() {
		return u;
	}
	
	public Clip getClip() {
		return clip;
	}
	public void setClip(Clip clip) {
		this.clip = clip;
	}
}

	
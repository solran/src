package utilities;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

import core.Main;
   
public class SoundClip {
	
	
	private URL u;
	private Clip clip;


	public SoundClip (String path)
	{
		this.u = Main.myWindow.getClass().getClassLoader().getResource( "sounds/" + path);
	}
	//"./sounds/"
	public static void play (SoundClip sc)
	{
		
		try {
		    // Open an audio input stream.
		    AudioInputStream audioIn = AudioSystem.getAudioInputStream(sc.getURL());
		    // Get a sound clip resource.
		    sc.setClip(AudioSystem.getClip());
		    // Open audio clip and load samples from the audio input stream.
		    sc.getClip().open(audioIn);
		    sc.getClip().start();
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

	
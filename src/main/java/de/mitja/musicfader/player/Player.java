package de.mitja.musicfader.player;

import java.io.File;
import java.util.prefs.Preferences;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import de.mitja.musicfader.ini.IniPack;

public class Player {
	
	private FloatControl gainControl;
	private Clip clip;
	private String filename;
	
	private double fadeInTime, fadeOutTime;
	
	public Player() {
		File wavDir = new File("wavs");
		if(!wavDir.exists()) wavDir.mkdir();
	}
	
	public boolean setFile(IniPack inipack) {
		Preferences prefs = inipack.getPrefs();
		filename = prefs.node("music-file").get("wav", null);
		
		fadeInTime = Double.parseDouble(prefs.node("fading").get("fadeInTime", null));
		fadeOutTime = Double.parseDouble(prefs.node("fading").get("fadeOutTime", null));
		
		if(new File("wavs/" + filename).exists()) return true;
		else return false;
	}
	
	public void start() {
		Thread play = new Thread() {
			public void run() {
				try {
					clip = AudioSystem.getClip();
					File wavfile = new File("wavs/" + filename);
					
					AudioInputStream inputStream = AudioSystem
			        .getAudioInputStream(wavfile);
					
			        clip.open(inputStream);
			        
			        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			        
			        fadeIn();
			        
			        clip.loop(clip.LOOP_CONTINUOUSLY);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		play.start();
	}
	
	public void stop() {
		fadeOut();
		
		clip.stop();
		clip.close();
	}
	
	public void fadeIn() {
		Thread fadeIn = new Thread() {
        	public void run() {
        		double vol = 0.0;
        		
        		for(int i = 0; i < fadeInTime * 200; i++) {
                	setVolume(vol);
                	vol = vol + (0.008 / fadeInTime);
                	
                	if(vol > 1.0) break;
                	
                	try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                }
        	}
        };
        fadeIn.start();
	}
	
	public void fadeOut() {
        try {
        	Thread fadeOut = new Thread() {
            	public void run() {
            		double vol = 1.0;
            		
            		for(int i = 0; i < fadeOutTime * 200; i++) {
                    	setVolume(vol);
                    	vol = vol - (0.008 / fadeOutTime);
                    	
                    	if(vol < 0.0) break;
                    	
                    	try {
    						Thread.sleep(40);
    					} catch (InterruptedException e) {
    						e.printStackTrace();
    					}
                    }
            	}
            };
            fadeOut.start();
			fadeOut.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setVolume(double value) {
	    // value is between 0 and 1
	    value = (value<=0.0)? 0.0001 : ((value>1.0)? 1.0 : value);
	    try {
	        float dB = (float)(Math.log(value)/Math.log(10.0)*20.0);
	        gainControl.setValue(dB);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
}

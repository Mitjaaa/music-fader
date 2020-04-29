package de.mitja.musicfader.ini;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.InvalidFileFormatException;

public class IniFiles {
	
	private File[] iniFiles;
	
	public IniFiles() throws InvalidFileFormatException, IOException {
		File iniFolder = new File("inis");
		if(!iniFolder.exists()) iniFolder.mkdir();
		
		
		File Default = new File("inis/carelesswhisper.ini");
		
		if(!Default.exists()) {
			Default.createNewFile();
			
			PrintWriter writer = new PrintWriter("inis/carelesswhisper.ini", "UTF-8");
			writer.println("[music-file]");
			writer.println("wav=carelesswhisperloop.wav");
			writer.println();
			writer.println("[fading]");
			writer.println("fadeInTime=1.5");
			writer.println("fadeOutTime=1.5");
			writer.close();
		}
		
		iniFiles = getAllIniFiles();
	}
	
	public IniPack[] getIniFiles() throws InvalidFileFormatException, IOException {
		IniPack[] inis = new IniPack[iniFiles.length];
		
		for(int i = 0; i < inis.length; i++) {
			Ini ini = new Ini(iniFiles[i]);
			Preferences prefs = new IniPreferences(ini);
			
			inis[i] = new IniPack(ini, prefs);	
		}
		
		return inis;
	}
	
	private File[] getAllIniFiles() {
		File folder = new File("inis");
		File[] listOfFiles = folder.listFiles();
		
		return listOfFiles;
	}

}

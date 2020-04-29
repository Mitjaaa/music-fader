package de.mitja.musicfader.ini;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.InvalidFileFormatException;

public class IniFiles {
	
	private File[] iniFiles;
	
	public IniFiles() throws InvalidFileFormatException, IOException {
		File iniFolder = new File("inis");
		if(!iniFolder.exists()) iniFolder.mkdir();
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
		File[] onlyInis;
		
		int counter = 0;
		for(int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].getName().endsWith(".ini")) {
				counter++;
			}
		}
		
		int oiCount = 0;
		onlyInis = new File[counter];
		for(int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].getName().endsWith(".ini")) {
				onlyInis[oiCount] = listOfFiles[i];
				oiCount++;
			}
		}
		
		return onlyInis;
	}

}

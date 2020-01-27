package de.mitja.musicfader.ini;

import java.util.prefs.Preferences;

import org.ini4j.Ini;

public class IniPack {
	
	private Ini ini;
	private Preferences prefs;
	
	public IniPack(Ini ini, Preferences prefs) {
		this.ini = ini;
		this.prefs = prefs;
	}
	
	
	public Ini getIni() {
		return ini;
	}
	
	public Preferences getPrefs() {
		return prefs;
	}
	
	public String toString() {
		return getIni().getFile().getName();
	}
}

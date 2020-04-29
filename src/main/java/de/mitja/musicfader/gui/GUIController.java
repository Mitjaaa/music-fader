package de.mitja.musicfader.gui;

import java.io.IOException;

import org.ini4j.InvalidFileFormatException;

import de.mitja.musicfader.ini.IniFiles;
import de.mitja.musicfader.ini.IniPack;
import de.mitja.musicfader.player.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class GUIController {
	
	@FXML
    private Button fadeInBtn;

    @FXML
    private Button fadeOutBtn;

    @FXML
    private ChoiceBox<String> iniSelector;

    @FXML
    private Label errorLabel;
    
    
    private IniFiles inis;
    private Player player;

    private boolean playing;
    private boolean fileExists;
    
    public Thread checkCB;

    @FXML
    void onFadeInClicked(MouseEvent event) throws InterruptedException {
    	playing = true;
    	
    	fadeInBtn.setDisable(true);
		fadeOutBtn.setDisable(false);
    	
		player.start();
    }

    @FXML
    void onFadeOutClicked(MouseEvent event) {
    	playing = false;
    	
    	fadeInBtn.setDisable(false);
    	fadeOutBtn.setDisable(true);
    	
    	player.stop();
    }

    @FXML
    void initialize() throws InvalidFileFormatException, IOException, Exception {
        assert fadeInBtn != null : "fx:id=\"fadeInBtn\" was not injected: check your FXML file 'GUI.fxml'.";
        assert fadeOutBtn != null : "fx:id=\"fadeOutBtn\" was not injected: check your FXML file 'GUI.fxml'.";
        assert iniSelector != null : "fx:id=\"iniSelector\" was not injected: check your FXML file 'GUI.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'GUI.fxml'.";
        
        inis = new IniFiles();
        IniPack[] inipacks = inis.getIniFiles();
        
        for (IniPack pack : inipacks) {
        	iniSelector.getItems().add(pack.toString());
        }
        
        player = new Player();
        checkChoiceBox(inipacks);
    }
    
    private void checkChoiceBox(IniPack[] inipacks) {
    	checkCB = new Thread() {
    		public void run() {
    			while(true) {
    				try {
    					if(!playing) {
    						if(iniSelector.getValue() == null || !fileExists) {
    	    					fadeInBtn.setDisable(true);
    	    					fadeOutBtn.setDisable(true);
    	    				} else {
    	    					fadeInBtn.setDisable(false);
    	    				}
    					}
						
						Thread.sleep(50);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
    			}
    		}
    	};
    	
    	checkCB.start();
    	
    	
    	iniSelector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
    	      @Override
    	      public void changed(ObservableValue<? extends Number> observableValue, Number firstNum, Number secNum) {
    	    	  fileExists = player.setFile(inipacks[iniSelector.getSelectionModel().getSelectedIndex()]);
    	    	  
    	    	  if(!fileExists) errorLabel.setVisible(true);
    	    	  else errorLabel.setVisible(false);
    	      }
    	});
    }
}

package de.mitja.musicfader.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MusicFader extends Application {
	
	@Override
	public void stop() {
		System.exit(0);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlloader = new FXMLLoader(this.getClass().getResource("GUI.fxml"));

		Parent root = fxmlloader.load();
		Scene scene = new Scene(root, 300, 150);
		primaryStage.setTitle("MusicFader");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
}

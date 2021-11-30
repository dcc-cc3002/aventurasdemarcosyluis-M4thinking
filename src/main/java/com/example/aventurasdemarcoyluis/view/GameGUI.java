package com.example.aventurasdemarcoyluis.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameGUI extends Application {
	private static final String RESOURCE_PATH = "src/main/resources/";
	private final Group root = new Group();

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		primaryStage.setTitle("Aventuras de Marco y Luis");
		primaryStage.setResizable(false);
		Scene scene= new Scene(root,1280,700);
		var background =
				new ImageView(new Image(new FileInputStream(RESOURCE_PATH + "marcoyluis.PNG")));
		root.getChildren().add(background);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

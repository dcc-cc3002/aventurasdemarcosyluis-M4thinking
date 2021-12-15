package com.example.aventurasdemarcoyluis.view.viewhandlers;

import com.example.aventurasdemarcoyluis.controller.handlers.IHandler;
import com.example.aventurasdemarcoyluis.view.GameGUI;

import java.beans.PropertyChangeEvent;
import java.io.FileNotFoundException;

public class EndGamePhaseHandler implements IViewHandler {
	private final GameGUI view;

	/**
	 * This handler allows you to act when the game ends
	 * @param view View of the game that will experience the change
	 */
	public EndGamePhaseHandler(GameGUI view) {
		this.view = view;
	}

	/**
	 * Method that executes the staging of the endgame phase in the view.
	 * @param evt Event launched at the end of the game.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try {
			view.showEndGame((String) evt.getNewValue());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

package com.example.aventurasdemarcoyluis.view.viewhandlers;

import com.example.aventurasdemarcoyluis.view.GameGUI;

import java.beans.PropertyChangeEvent;
import java.io.FileNotFoundException;

/** Observer of the start of a new battle, which allows to give warning from the controller in sight. */
public class NewBattlePhaseHandler implements IViewHandler {
	private final GameGUI view;

	/**
	 * Handler that detects when a battle ends, to carry out the next one.
	 * @param view View of the game that will experience the change.
	 */
	public NewBattlePhaseHandler(GameGUI view) {
		this.view = view;
	}

	/**
	 * <p>
	 *     Try to show from view the enemies
	 *     needed for the next battle when
	 *     the battle is over.
	 * </p>
	 * @param evt Event that warns the end of the current battle.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try {
			view.showEnemiesAtStart();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

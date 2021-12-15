package com.example.aventurasdemarcoyluis.controller.handlers;

import com.example.aventurasdemarcoyluis.controller.GameController;

import java.beans.PropertyChangeEvent;

public class EnemiesPhaseHandler implements IHandler{
	private final GameController controller;

	/**
	 * Observer who receives notification when the enemy's game phase ends
	 * @param controller Game controller that has the phase system that alerts the handler
	 */
	public EnemiesPhaseHandler(GameController controller) {
		this.controller = controller;
	}

	/**
	 * <p>
	 *    It records when the phase change
	 * 	  property is altered and attempts
	 * 	  to end the enemy's turn from the
	 * 	  controller.
	 * </p>
	 * @param evt Event that is notified at the beginning of the enemies phase,
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		controller.tryToEndEnemiesTurn();
	}
}

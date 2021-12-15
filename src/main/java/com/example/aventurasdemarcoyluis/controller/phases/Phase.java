package com.example.aventurasdemarcoyluis.controller.phases;

import com.example.aventurasdemarcoyluis.controller.GameController;
import com.example.aventurasdemarcoyluis.controller.phases.exceptions.InvalidMovementException;
import com.example.aventurasdemarcoyluis.controller.phases.exceptions.InvalidTransitionException;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.itemsconfig.IItem;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;

public class Phase {
	protected GameController controller;
	protected PrintStream errorStream = System.out; // By default

	/**
	 * Sets the controller for the phase
	 * @param gameController The game controller.
	 */
	public void setController(@NotNull GameController gameController) {
		this.controller = gameController;
		this.errorStream = gameController.getErrorStream(); //Inherit the stream
	}

	/**
	 * Allows phase change without losing controller registration
	 * @param aPhase new phase to transition to.
	 */
	protected void changePhase(Phase aPhase){controller.setPhase(aPhase);}

	/**
	 * <p>
	 *     Generates an invalid transition error
	 *     according to the context of the current
	 *     phase and the one to be transferred.
	 * </p>
	 * @param transitionPhase The phase or pseudo phase to which you want to reach
	 * @throws InvalidTransitionException The exception is thrown when a
	 *                                    phase is reached to which it is not allowed to move.
	 */
	protected void error(String transitionPhase) throws InvalidTransitionException {
		throw new InvalidTransitionException(
				"Can't change from " + this + " to " + transitionPhase + ".");
	}

	/**
	 * Try to transition to the battle phase.
	 * @throws InvalidTransitionException This exception is thrown when phase change is not allowed.
	 */
	public void toBattlePhase() throws InvalidTransitionException {
		error("BattlePhase");
	}

	/**
	 * Try to transition to the wait phase.
	 * @throws InvalidTransitionException This exception is thrown when phase change is not allowed.
	 */
	public void toWaitPhase() throws InvalidTransitionException {
		error("WaitPhase");
	}

	/**
	 * Try to transition to the wait attack phase.
	 * @throws InvalidTransitionException This exception is thrown when phase change is not allowed.
	 */
	public void toWaitAttackPhase() throws InvalidTransitionException {
		error("WaitAttackPhase");
	}

	/**
	 * Try to transition to the wait attackable phase.
	 * @throws InvalidTransitionException This exception is thrown when phase change is not allowed.
	 */
	public void toWaitAttackablePhase(AttackType anAttackType) throws InvalidTransitionException {
		error("WaitAttackablePhase");
	}

	/**
	 * Try to transition to the wait item select phase.
	 * @throws InvalidTransitionException This exception is thrown when phase change is not allowed.
	 */
	public void toWaitItemSelectPhase() throws InvalidTransitionException {
		error("WaitItemSelectPhase");
	}

	/**
	 * Try to transition to the wait item use phase.
	 * @throws InvalidTransitionException This exception is thrown when phase change is not allowed.
	 */
	public void toWaitItemUsePhase(IItem anItemType) throws InvalidTransitionException {
		error("WaitItemUsePhase");
	}

	/**
	 * Try to transition to the end attack turn pseudo-phase.
	 * @throws InvalidTransitionException This exception is thrown when phase change is not allowed.
	 */
	public void endAttackTurn() throws InvalidTransitionException, InvalidMovementException {
		error("EndAttackTurn");
	}

	/**
	 * Try to transition to the end item turn pseudo-phase.
	 * @throws InvalidTransitionException This exception is thrown when phase change is not allowed.
	 */
	public void endItemTurn() throws InvalidTransitionException, InvalidMovementException {
		error("EndItemTurn");
	}

	/**
	 * Try to transition to the end pass turn pseudo-phase.
	 * @throws InvalidTransitionException This exception is thrown when phase change is not allowed.
	 */
	public void passTurn() throws InvalidTransitionException {
		error("PassTurn");
	}

	/**
	 * Try to transition to the end game phase.
	 */
	public void toEndGamePhase() {
		changePhase(new EndGamePhase());
	}
}

package com.example.aventurasdemarcoyluis.controller.phases;

import com.example.aventurasdemarcoyluis.controller.GameController;
import com.example.aventurasdemarcoyluis.controller.phases.phasesassertions.InvalidTransitionException;

public class Phase {
	private GameController controller;

	public void setController(GameController gameController) {
		this.controller = controller;
	}

	protected void changePhase(Phase aPhase){controller.setPhase(aPhase);}

	public String toString(){
		return "Phase";
	}

	void error(String transitionPhase) throws InvalidTransitionException {
		throw new InvalidTransitionException(
				"Can't change from " + this.toString() + " to " + transitionPhase + "phase.");
	}

	protected void toBattlePhase() throws InvalidTransitionException {
		error("BattlePhase");
	}

	protected void toWaitPhase() throws InvalidTransitionException {
		error("WaitPhase");
	}

	protected void toWaitAttackPhase() throws InvalidTransitionException {
		error("WaitAttackPhase");
	}

	protected void toWaitAttackablePhase() throws InvalidTransitionException {
		error("WaitAttackablePhase");
	}

	protected void toEnemiesPhase() throws InvalidTransitionException {
		error("EnemiesPhase");
	}

	protected void toWaitItemSelectPhase() throws InvalidTransitionException {
		error("WaitItemSelectPhase");
	}

	protected void toWaitItemUsePhase() throws InvalidTransitionException {
		error("WaitItemUsePhase");
	}

	protected void toEndGamePhase() throws InvalidTransitionException {
		error("EndGamePhase");
	}
}

package com.example.aventurasdemarcoyluis.controller.phases;

/** Class of transition from initial phase to waiting phase, exists so that the game does not start immediately. */
public class BattlePhase extends Phase {

	@Override
	public void toWaitPhase() {
		changePhase(new WaitPhase());
	}
}

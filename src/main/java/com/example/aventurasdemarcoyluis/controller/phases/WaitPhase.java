package com.example.aventurasdemarcoyluis.controller.phases;

/** Initial waiting phase of the game. */
public class WaitPhase extends Phase {

	@Override
	public void toWaitAttackPhase(){
		changePhase(new WaitAttackPhase());
	}

	@Override
	public void toWaitItemSelectPhase(){
		changePhase(new WaitItemSelectPhase());
	}

	@Override
	public void passTurn(){
		controller.passTurn();
	}

	@Override
	public String toString(){
		return "Is your turn";
	}
}

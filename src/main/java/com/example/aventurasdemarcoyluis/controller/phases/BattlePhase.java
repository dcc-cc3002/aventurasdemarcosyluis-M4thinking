package com.example.aventurasdemarcoyluis.controller.phases;

public class BattlePhase extends Phase {

	@Override
	public void toWaitPhase() {
		changePhase(new WaitPhase());
	}
}

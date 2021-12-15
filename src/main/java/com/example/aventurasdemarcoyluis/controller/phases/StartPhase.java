package com.example.aventurasdemarcoyluis.controller.phases;

public class StartPhase extends Phase{

	@Override
	public void toBattlePhase() {
		controller.initialPhase();
		controller.startBattlePhase();
		changePhase(new BattlePhase());
	}

	@Override
	public String toString() {
		return "Start Phase";
	}
}

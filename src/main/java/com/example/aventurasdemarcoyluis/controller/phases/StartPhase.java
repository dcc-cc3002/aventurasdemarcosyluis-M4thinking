package com.example.aventurasdemarcoyluis.controller.phases;

import com.example.aventurasdemarcoyluis.controller.phases.phasesassertions.InvalidTransitionException;

public class StartPhase extends Phase{
	protected void toBattlePhase() {
		changePhase(new BattlePhase());
	}

	@Override
	public String toString() {
		return "Start Phase";
	}
}

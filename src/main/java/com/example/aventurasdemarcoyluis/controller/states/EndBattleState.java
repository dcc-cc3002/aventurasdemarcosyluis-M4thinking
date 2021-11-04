package com.example.aventurasdemarcoyluis.controller.states;

import com.example.aventurasdemarcoyluis.model.characters.enemies.IEnemy;
import com.example.aventurasdemarcoyluis.model.characters.players.IPlayer;

import java.util.List;

public class EndBattleState extends AbstractGameState {
	@Override
	public void makeTransition() {

	}

	@Override
	public boolean isFinal(){
		return true;
	}
}

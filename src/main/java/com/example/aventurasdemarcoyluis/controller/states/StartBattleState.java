package com.example.aventurasdemarcoyluis.controller.states;

import com.example.aventurasdemarcoyluis.model.characters.enemies.IEnemy;
import com.example.aventurasdemarcoyluis.model.characters.players.IPlayer;

/* When starting the battle, the player's turns are executed and then the enemies' turns */
public class StartBattleState extends AbstractGameState {
	@Override
	public void makeTransition() {
		for (IPlayer player : gameAutomata.players) {
			gameAutomata.gameController.runShift(player);
		}
		for(IEnemy enemy: gameAutomata.enemies){
			gameAutomata.gameController.runShift(enemy);
		}
	}
}

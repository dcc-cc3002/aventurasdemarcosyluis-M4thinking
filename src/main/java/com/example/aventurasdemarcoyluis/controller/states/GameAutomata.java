package com.example.aventurasdemarcoyluis.controller.states;

import com.example.aventurasdemarcoyluis.controller.GameController;
import com.example.aventurasdemarcoyluis.model.characters.enemies.IEnemy;
import com.example.aventurasdemarcoyluis.model.characters.players.IPlayer;

import java.util.List;

public class GameAutomata {
	private IGameState gameState;
	protected List<IPlayer> players;
	protected List<IEnemy> enemies;
	protected GameController gameController;

	public GameAutomata(GameController gameController) {
		setGameState(new InitialState());
		this.gameController = gameController;
	}

	protected void setGameState(IGameState aGameState) {
		this.gameState = gameState;
		this.gameState.setGameAutomata(this);
	}

	boolean makeTransition(){
		for(int i = 0; i < 3; i++){
			gameState.makeTransition();
		}
		return gameState.isFinal();
	}


}

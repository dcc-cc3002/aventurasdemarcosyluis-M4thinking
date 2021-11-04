package com.example.aventurasdemarcoyluis.controller.states;

public abstract class AbstractGameState implements IGameState {
	public GameAutomata gameAutomata;

	@Override
	public boolean isFinal() {
		return false;
	}

	@Override
	public void setGameAutomata(GameAutomata anAutomata) {
		this.gameAutomata = anAutomata;
	}
}
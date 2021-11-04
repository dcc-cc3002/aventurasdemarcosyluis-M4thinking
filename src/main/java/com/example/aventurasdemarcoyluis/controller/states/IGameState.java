package com.example.aventurasdemarcoyluis.controller.states;

public interface IGameState {
	void makeTransition();
	boolean isFinal();
	void setGameAutomata(GameAutomata anAutomata);

}

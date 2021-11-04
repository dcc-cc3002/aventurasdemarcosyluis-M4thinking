package com.example.aventurasdemarcoyluis.controller.states;

import com.example.aventurasdemarcoyluis.model.itemsconfig.ItemBag;
import com.example.aventurasdemarcoyluis.model.itemsconfig.items.HoneySyrup;
import com.example.aventurasdemarcoyluis.model.itemsconfig.items.RedMushroom;

public class InitialState extends AbstractGameState {

	@Override
	public void makeTransition() {
		ItemBag chest = ItemBag.instance();
		for(int i = 0; i < 3; i++){
			chest.addItem(new RedMushroom());
			chest.addItem(new HoneySyrup());
		}
		gameAutomata.setGameState(new StartBattleState());
	}
}

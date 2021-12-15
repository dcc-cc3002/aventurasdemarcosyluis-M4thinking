package com.example.aventurasdemarcoyluis.controller.phases;

import com.example.aventurasdemarcoyluis.controller.phases.exceptions.InvalidMovementException;
import com.example.aventurasdemarcoyluis.model.itemsconfig.IItem;

/** Items usage waiting phase. */
public class WaitItemUsePhase extends Phase{
	private final IItem anItem;

	/**
	 * The constructor of the item wait phase requires the item to use.
	 * @param anItem the item you want to use,
	 */
	protected WaitItemUsePhase(IItem anItem){
		this.anItem = anItem;
	}

	@Override
	public void endItemTurn() throws InvalidMovementException {
		toWaitPhase();
		controller.useItemTurn(controller.getAllyIndex()+1, anItem);
	}

	@Override
	public void toWaitPhase() {
		changePhase(new WaitPhase());
	}

	@Override
	public String toString(){
		return "Choose a non-KO ally";
	}
}

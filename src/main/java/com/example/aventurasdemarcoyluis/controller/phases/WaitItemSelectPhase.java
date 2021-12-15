package com.example.aventurasdemarcoyluis.controller.phases;

import com.example.aventurasdemarcoyluis.model.itemsconfig.IItem;

/** Item selection waiting phase. */
public class WaitItemSelectPhase extends Phase{

	@Override
	public void toWaitPhase() {
		changePhase(new WaitPhase());
	}

	@Override
	public void toWaitItemUsePhase(IItem anItem) {
		changePhase(new WaitItemUsePhase(anItem));
	}

	@Override
	public String toString(){
		return "Choose an Item";
	}
}

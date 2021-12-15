package com.example.aventurasdemarcoyluis.controller.phases;

import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;

public class WaitAttackPhase extends Phase{

	@Override
	public void toWaitPhase(){
		changePhase(new WaitPhase());
	}

	@Override
	public void toWaitAttackablePhase(AttackType attackType) {
		changePhase(new WaitAttackablePhase(attackType));
	}

	@Override
	public String toString(){
		return "Choose Attack";
	}
}

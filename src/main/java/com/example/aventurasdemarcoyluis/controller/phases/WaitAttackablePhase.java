package com.example.aventurasdemarcoyluis.controller.phases;

import com.example.aventurasdemarcoyluis.controller.phases.exceptions.InvalidMovementException;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;

public class WaitAttackablePhase extends Phase{
	private final AttackType attackType;

	/**
	 * The constructor of the attackable standby phase requires the type of attack to perform.
	 * @param attackType type of attack you want to execute.
	 */
	protected WaitAttackablePhase(AttackType attackType) {
		this.attackType = attackType;
	}

	@Override
	public void endAttackTurn() throws InvalidMovementException {
		toWaitPhase();
		controller.attackTurn(attackType, controller.getTargetIndex());

	}

	@Override
	public void toWaitPhase() {
		changePhase(new WaitPhase());
	}

	@Override
	public String toString(){
		return "Choose an Enemy";
	}
}

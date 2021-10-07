package com.example.aventurasdemarcoyluis.characters.players;

import com.example.aventurasdemarcoyluis.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.characters.enemies.IEnemy;

/** This interface must be implemented by all generic players, they can attack all enemies */
public interface IGenericPlayer extends IPlayer {

	/**
	 * Attack an {@code IEnemy} with an {@code AttackType}.
	 * <p>
	 *     The attack will only be effective
	 *     if the player has enough attack
	 *     points according to the cost of the
	 *     attack. If the player is defeated (K.O.),
	 *     he will attack with 0 damage.
	 * </p>
	 * @param anEnemy The enemy that will be attacked.
	 * @param anAttack The attack that will be used.
	 */
	void attack(IEnemy anEnemy, AttackType anAttack);
}

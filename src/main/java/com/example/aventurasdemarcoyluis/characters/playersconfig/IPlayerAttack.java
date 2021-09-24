package com.example.aventurasdemarcoyluis.characters.playersconfig;

import com.example.aventurasdemarcoyluis.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.IEnemy;

/**
 * Models the attack interaction of the players.
 * <p>
 *     All players (attack or not) must implement this interface.
 * </p>
 */
public interface IPlayerAttack {

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

	/**
	 * Method that warns the player
	 * that he is being attacked by
	 * for some enemy, to act on it.
	 * @param anEnemy The attacking enemy.
	 */
	void attackedByEnemy(IEnemy anEnemy);

	/**
	 * Method that warns the player
	 * that he is being attacked by
	 * the enemy Boo, to act on it.
	 * @param anEnemy The attacking enemy (Boo).
	 */
	void attackedByBoo(IEnemy anEnemy);

	/**
	 * Calculates the damage inflicted by an {@code IEnemy}
	 * to a {@code IPlayer}, by the game's formula.
	 * <p>
	 *     If {@code IEnemy} is defeated (K.O.), or the
	 * 	   {@code IPlayer} is invincible, the damage is 0.
	 * </p>
	 * @param anEnemy The Enemy who attacked him.
	 * @return Returns the damage to be inflicted.
	 */
	int damageReceived(IEnemy anEnemy);
}

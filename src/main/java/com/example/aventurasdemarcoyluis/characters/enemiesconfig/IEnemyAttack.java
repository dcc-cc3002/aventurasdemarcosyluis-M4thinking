package com.example.aventurasdemarcoyluis.characters.enemiesconfig;

import com.example.aventurasdemarcoyluis.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.characters.playersconfig.IPlayer;

/**
 * Models the attack interaction of the enemies.
 * <p>
 *     All enemies (attack or not) must implement this interface.
 * </p>
 */
public interface IEnemyAttack {

	/**
	 * Attack a {@code IPlayer}.
	 * <p>
	 *     The attack will only be effective
	 *     if the {@code IPlayer} is {@code invincible}
	 *     or the enemy is defeated (K.O.),
	 *     it will attack with 0 damage.
	 * </p>
	 * @param aPlayer The player that will be attacked.
	 */
	void attack(IPlayer aPlayer);

	/**
	 * Method that warns the player
	 * that he is being attacked by
	 * for some enemy, to act on it.
	 * @param aPlayer The attacking player.
	 * @param anAttack Type of attack received from the player.
	 */
	void attackedByPlayer(IPlayer aPlayer, AttackType anAttack);

	/**
	 * Method that warns the enemy
	 * that he is being attacked by
	 * the player Luis, to act on it.
	 * @param aPlayer The attacking enemy (Luis).
	 * @param anAttack Type of attack received from the player.
	 */
	void attackByLuis(IPlayer aPlayer, AttackType anAttack);

	/**
	 * Method that warns the enemy
	 * that he is being attacked by
	 * the player Marcos, to act on it.
	 * @param aPlayer The attacking enemy (Marcos).
	 * @param anAttack Type of attack received from the player.
	 */
	void attackByMarcos(IPlayer aPlayer, AttackType anAttack);

	/**
	 * Calculates the damage inflicted by a {@code IPlayer} to an {@code IEnemy},
	 * If {@code IPlayer} is defeated, the damage is 0.
	 *
	 * @param aPlayer The player who attacked him.
	 * @return Returns the damage to be inflicted.
	 */
	int damageReceived(IPlayer aPlayer, AttackType anAttack);
}

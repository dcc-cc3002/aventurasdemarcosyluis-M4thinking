package com.example.aventurasdemarcoyluis.characters.enemies;

import com.example.aventurasdemarcoyluis.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.characters.players.IPlayer;
import com.example.aventurasdemarcoyluis.characters.players.Luis;

/** This interface must be implemented by all generic enemies, they can attack all players. */
public interface IGenericEnemy extends IEnemy {

	/**
	 * Attack a {@code IPlayer}.
	 * <p>
	 *     The attack will only be effective
	 *     if the {@code IPlayer} is {@code invincible}
	 *     or the {@code IGenericEnemy} is defeated (K.O.),
	 *     it will attack with 0 damage.
	 * </p>
	 * @param aPlayer The player that will be attacked.
	 */
	void attack(IPlayer aPlayer);

	/**
	 * Method that warns the enemy
	 * that he is being attacked by
	 * the player Luis, to act on it.
	 * @param luis The attacking player Luis.
	 * @param anAttack Type of attack received from the player.
	 */
	void attackedByLuis(Luis luis, AttackType anAttack);

}

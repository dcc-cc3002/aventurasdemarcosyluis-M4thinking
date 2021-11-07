package com.example.aventurasdemarcoyluis.model.characters.players;

import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.Boo;
import com.example.aventurasdemarcoyluis.model.characters.enemies.IEnemy;
/**
 * Every scared player should implement this interface, if so,
 * the player will only attack generic enemies. And may
 * be attacked by spectral enemies.
 * */
public interface IScaredPlayer extends IPlayer{

	/**
	 * Attack only effective over {@code IGenericEnemy} with an {@code AttackType}.
	 * <p>
	 *     The attack will only be effective
	 *     if the player has enough attack
	 *     points according to the cost of the
	 *     attack. If the player is defeated (K.O.),
	 *     he will attack with 0 damage.
	 * </p>
	 * @param anEnemy The generic enemy that will be attacked.
	 * @param anAttack The attack that will be used.
	 */
	void attack(IEnemy anEnemy, AttackType anAttack);

	/**
	 * Method that warns the player
	 * that he is being attacked by
	 * the enemy Boo, to act on it.
	 * @param boo The attacking enemy Boo.
	 */
	void attackedByBoo(Boo boo);
}

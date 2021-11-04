package com.example.aventurasdemarcoyluis.model.characters.enemies;

import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.players.IPlayer;

/*This interface must be implemented by all enemies that have special reaction to an attack*/
public interface ISpecialReactionEnemy extends IEnemy{
	/**
	 * Verify that the special condition is met given the particular type of attack.
	 * If applicable, the attack will have a special effect on the enemy or the player.
	 * @param aPlayer The player who performs the attack.
	 * @param anAttack Type of attack received from the player.
	 */
	void attackCondition(IPlayer aPlayer, AttackType anAttack);
}

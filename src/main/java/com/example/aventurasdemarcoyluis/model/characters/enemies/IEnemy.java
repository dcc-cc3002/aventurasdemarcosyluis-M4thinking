package com.example.aventurasdemarcoyluis.model.characters.enemies;

import com.example.aventurasdemarcoyluis.model.characters.ICharacter;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.players.IPlayer;
import com.example.aventurasdemarcoyluis.model.characters.players.Marcos;

/** Interface that models the particular characteristics of an enemy. */
public interface IEnemy extends ICharacter {

    /**
     * Returns the {@code EnemyType} of the enemy.
     * @return {@code EnemyType}.
     */
    EnemyType getType();

    /**
     * Sets the enum {@code EnemyType} of the enemy.
     * @param aType An {@code enum} from  the EnemyType class.
     */
    void setType(EnemyType aType);

    /**
     * Verify that the health points are between 0 and
     * the maximum health.
     * @return {@code true} if restrictions are met, {@code false} if not.
     */
    boolean invariant();

    /** Invokes a phrase of hatred from the enemy on the console. */
    void insult();

    /**
     * Calculates the damage inflicted by a {@code IPlayer} to an {@code IEnemy},
     * If {@code IPlayer} is defeated, the damage is 0.
     *
     * @param aPlayer The player who attacked him.
     * @return Returns the damage to be inflicted.
     */
    int damageReceived(IPlayer aPlayer, AttackType anAttack);

    /**
     * Method that warns the enemy
     * that he is being attacked by
     * the player Marcos, to act on it.
     * @param marcos The attacking player Marcos.
     * @param anAttack Type of attack received from Marcos.
     */
    void attackedByMarcos(Marcos marcos, AttackType anAttack);

    /**
     * Method that warns the player
     * that he is being attacked by
     * an enemy, to act on it.
     * @param aPlayer The attacking player.
     * @param anAttack Type of attack received from the player.
     */
    void attackedByPlayer(IPlayer aPlayer, AttackType anAttack);
}

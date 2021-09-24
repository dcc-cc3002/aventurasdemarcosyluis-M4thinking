package com.example.aventurasdemarcoyluis.characters.enemiesconfig;

import com.example.aventurasdemarcoyluis.characters.ICharacter;

/** Interface that models the particular characteristics of an enemy. */
public interface IEnemy extends ICharacter, IEnemyAttack {

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

}

package com.example.aventurasdemarcoyluis.model.characters.enemies;

import com.example.aventurasdemarcoyluis.model.characters.ICharacter;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.players.*;

import java.util.List;

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
     * Method that warns the enemy
     * that he is being attacked by
     * the player Marcos, to act on it.
     * @param luis The attacking player Luis.
     * @param anAttack Type of attack received from Luis.
     */
    void attackedByLuis(Luis luis, AttackType anAttack);

    /**
     * Method that warns the Enemy
     * that he is being attacked by
     * a Player, to act on it.
     * @param aPlayer The attacking player.
     * @param anAttack Type of attack received from the player.
     */
    void attackedByPlayer(IPlayer aPlayer, AttackType anAttack);

    /**
     * Method that warns the player
     * that he is being attacked by
     * an enemy, to act on it.
     * @param aPlayer The attacked player.
     */
    void attack(IPlayer aPlayer);

    /**
     * Returns a sublist of players who can attack, given a list of more players
     * @param listOfPlayers player list to filter from
     * @return Sublist of possible players that the enemy can attack
     */
    List<IPlayer> getAttackablePlayers(List<IPlayer> listOfPlayers);

    /**
     * Delivers confirmation of whether this enemy
     * is attackable by a {@code IGenericPlayer}.
     * @param aPlayer The player that is verified
     * @return {@code true} if the player can attack it, {@code false} if not.
     */
    boolean isAttackableBy(IGenericPlayer aPlayer);

    /**
     * Delivers confirmation of whether this enemy
     * is attackable by a {@code IScaredPlayer}.
     * @param aPlayer The player that is verified
     * @return {@code true} if the player can attack it, {@code false} if not.
     */
    boolean isAttackableBy(IScaredPlayer aPlayer);

    /**
     * Allows to fix the results after attacks with probability with a seed
     * @param seed Number that fixes the obtained results
     */
    void setFailSeed(long seed);

    /**
     * Give the enemy's own identifier.
     * @return {@code IEnemy} identifier.
     */
    int getId();

    /**
     * Set the enemy's own identifier.
     * @param id {@code IEnemy} identifier.
     */
    void setId(int id);
}

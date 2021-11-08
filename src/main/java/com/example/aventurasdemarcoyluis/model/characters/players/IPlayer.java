package com.example.aventurasdemarcoyluis.model.characters.players;

import com.example.aventurasdemarcoyluis.model.characters.ICharacter;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.*;
import com.example.aventurasdemarcoyluis.model.itemsconfig.IItem;
import com.example.aventurasdemarcoyluis.model.itemsconfig.ItemBag;

import java.util.List;

/** Interface that models the players and that each of them must implement. */
public interface IPlayer extends ICharacter{

    /**
     * Gets the current player's fight points.
     * @return Fight points.
     */
    int getFp();

    /**
     * Sets the current player's fight points.
     * The fp must be between zero and the maximum,
     * otherwise, it will be forced to do so.
     *
     * @param fp current fight points.
     */
    void setFp(int fp);

    /**
     * Increase or decrease n units the Fp
     * @param numberOfFp The units to increase (+) or decrease (-).
     */
    void increaseOrDecreaseFp(int numberOfFp);

    /**
     * Gets the maximum player's fight points.
     * @return Maximum fight points.
     */
    int getMaxFp();

    /**
     * Sets the maximum player's fight points.
     * The maximum fp must be greater than zero,
     * otherwise it will be forced to do so.
     *
     * @param maxFp Maximum fight points.
     */
    void setMaxFp(int maxFp);

    /**
     * Verify that the health and fight points are between 0 and the maximum.
     * @return {@code true} if restrictions are met, {@code false} if not.
     */
    boolean invariant();

    /**
     * Counts a new instance of the {@code IItem}.
     * in the player's {@code ItemBag}.
     *
     * @param anItem Item to keep in the bag.
     */
    void addItem(IItem anItem);

    /**
     * Use one of the {@code IItem} instances available
     * in the {@code ItemBag}. In the case of not having
     * available instances of this item, the item will
     * simply not be used.
     *
     * @param anItem Item to be used and discounted from the bag.
     */
    void selectItem(IItem anItem);

    /**
     * Gets the player's current armament,
     * that is, each item associated with its quantity.
     * @return The player's armament of {@code ItemBag} type.
     */
    ItemBag getArmament();

    /**
     * The player's level is increased by 1
     * and his stats are increased by 15%
     * from the current value (in the case
     * of HP and FP, they increase by 15%
     * of their maximum only).
     */
    void levelUp();

    /** Writes the typical phrase of the player in the terminal. */
    void typicalPhrase();

    /*Attack logic common to players*/

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

    /**
     * Method that warns the player
     * that he is being attacked by
     * an enemy, to act on it.
     * @param anEnemy The attacking enemy.
     */
    void attackedByEnemy(IEnemy anEnemy);

    /**
     * Method that warns the player
     * that he is being attacked by
     * the enemy Spiny, to act on it.
     * @param spiny The attacking enemy Spiny.
     */
    void attackedBySpiny(Spiny spiny);

    /**
     * Method that warns the player
     * that he is being attacked by
     * the enemy Goomba, to act on it.
     * @param goomba The attacking enemy Goomba.
     */
    void attackedByGoomba(Goomba goomba);

    /**
     * Method that warns the player
     * that he is being attacked by
     * the enemy Boo, to act on it.
     * @param boo The attacking enemy Goomba.
     */
    void attackedByBoo(Boo boo);

    /**
     * Method that warns the player
     * that he is being attacked by
     * the enemy Boo, to act on it.
     * @param anEnemy The attacked Enemy.
     * @param anAttack The {@code AttackType} you want to perform.
     */
    void attack(IEnemy anEnemy, AttackType anAttack);

    /**
     * Returns a sublist of enemies who can attack, given a list of more enemies
     * @param listOfEnemies enemy list to filter from
     * @return Sublist of possible enemies that the player can attack
     */
    List<IEnemy> getAttackableEnemies(List<IEnemy> listOfEnemies);

    /** Dynamic stats HP and FP are restore to maxHp and maxFp */
    void restoreDynamicStats();

    /**
     * Delivers confirmation of whether this player
     * is attackable by a {@code IGenericEnemy}.
     * @param anEnemy The enemy that is verified
     * @return True if the enemy can attack it, false if not.
     */
    boolean isAttackableBy(IGenericEnemy anEnemy);

    /**
     * Delivers confirmation of whether this player
     * is attackable by a {@code IEspectralEnemy}.
     * @param anEnemy The enemy that is verified
     * @return {@code true} if the enemy can attack it, {@code false} if not.
     */
    boolean isAttackableBy(IEspectralEnemy anEnemy);
}

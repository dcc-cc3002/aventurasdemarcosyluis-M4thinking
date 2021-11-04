package com.example.aventurasdemarcoyluis.model.characters.players;

import com.example.aventurasdemarcoyluis.model.characters.ICharacter;
import com.example.aventurasdemarcoyluis.model.characters.enemies.Goomba;
import com.example.aventurasdemarcoyluis.model.characters.enemies.IEnemy;
import com.example.aventurasdemarcoyluis.model.characters.enemies.Spiny;
import com.example.aventurasdemarcoyluis.model.itemsconfig.IItem;
import com.example.aventurasdemarcoyluis.model.itemsconfig.ItemBag;

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
     * the enemy Boo, to act on it.
     * @param spiny The attacking enemy Spiny.
     */
    void attackedBySpiny(Spiny spiny);

    /**
     * Method that warns the player
     * that he is being attacked by
     * the enemy Boo, to act on it.
     * @param goomba The attacking enemy Goomba.
     */
    void attackedByGoomba(Goomba goomba);
}

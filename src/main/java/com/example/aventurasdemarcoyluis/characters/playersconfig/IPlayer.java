package com.example.aventurasdemarcoyluis.characters.playersconfig;

import com.example.aventurasdemarcoyluis.characters.ICharacter;
import com.example.aventurasdemarcoyluis.itemsconfig.IItem;
import com.example.aventurasdemarcoyluis.itemsconfig.ItemBag;

/** Interface that models the players and that each of them must implement. */
public interface IPlayer extends ICharacter,IPlayerAttack  {

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
     * Make a player invincible.
     * @param isInvincible Boolean statement of whether the player should become invincible or not.
     */
    void setInvincible(boolean isInvincible);

    /**
     * Check if the player is in invincible state.
     * @return Truth value of whether the player is currently invincible or not
     */
    boolean getInvincible();

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

    /** Writes the typical phrase of the player in the terminal. */
    void typicalPhrase();
}

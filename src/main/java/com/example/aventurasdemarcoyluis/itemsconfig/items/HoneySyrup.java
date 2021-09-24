package com.example.aventurasdemarcoyluis.itemsconfig.items;

import com.example.aventurasdemarcoyluis.characters.playersconfig.IPlayer;
import com.example.aventurasdemarcoyluis.itemsconfig.IItem;
import com.example.aventurasdemarcoyluis.itemsconfig.AbstractItem;
import com.example.aventurasdemarcoyluis.itemsconfig.ItemType;

/** Class that models an item of type HoneySyrup. */
public class HoneySyrup extends AbstractItem implements IItem {

    /** Item constructor with {@code ItemType} {@code HONEY_SYRUP}. */
    public HoneySyrup() {
        super(ItemType.HONEY_SYRUP);
    }

    /**
     * Apply the item to the player and increase his fight points ({@code fp}) by 3 units.
     * @param aPlayer Player who will have the effect of the star.
     */
    @Override
    public void applyToPlayer(IPlayer aPlayer) {
        aPlayer.setFp(aPlayer.getFp() + 3);
    }

}

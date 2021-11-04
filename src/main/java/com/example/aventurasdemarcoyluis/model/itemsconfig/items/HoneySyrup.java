package com.example.aventurasdemarcoyluis.model.itemsconfig.items;

import com.example.aventurasdemarcoyluis.model.characters.players.IPlayer;
import com.example.aventurasdemarcoyluis.model.itemsconfig.AbstractItem;
import com.example.aventurasdemarcoyluis.model.itemsconfig.ItemType;

/** Class that models an item of type HoneySyrup. */
public class HoneySyrup extends AbstractItem {

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

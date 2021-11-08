package com.example.aventurasdemarcoyluis.model.itemsconfig.items;

import com.example.aventurasdemarcoyluis.model.characters.players.IPlayer;
import com.example.aventurasdemarcoyluis.model.itemsconfig.AbstractItem;
import com.example.aventurasdemarcoyluis.model.itemsconfig.ItemType;

/** Class that models an item of type RedMushroom */
public class RedMushroom extends AbstractItem {

    /** Item constructor with {@code ItemType} {@code RED_MUSHROOM}. */
    public RedMushroom() {
        super(ItemType.RED_MUSHROOM);
    }

    /**
     * Apply the item to the player and heal for 10% of his maximum health points ({@code maxHp}).
     * @param aPlayer Player who will have the effect of the star.
     */
    @Override
    public void applyToPlayer(IPlayer aPlayer) {
        aPlayer.setHp((int)(Math.round(aPlayer.getMaxHp()*0.1) + aPlayer.getHp()));
    }
}

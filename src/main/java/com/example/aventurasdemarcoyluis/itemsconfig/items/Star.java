package com.example.aventurasdemarcoyluis.itemsconfig.items;

import com.example.aventurasdemarcoyluis.characters.players.IPlayer;
import com.example.aventurasdemarcoyluis.itemsconfig.AbstractItem;
import com.example.aventurasdemarcoyluis.itemsconfig.ItemType;

/** Class that models an item of type Star. */
public class Star extends AbstractItem {

    /** Item constructor with {@code ItemType} {@code STAR}. */
    public Star() {
        super(ItemType.STAR);
    }

    /**
     * Apply the item to the player making him invincible.
     * @param aPlayer Player who will have the effect of the star.
     */
    @Override
    public void applyToPlayer(IPlayer aPlayer) {
        aPlayer.setInvincible(true);
    }
}

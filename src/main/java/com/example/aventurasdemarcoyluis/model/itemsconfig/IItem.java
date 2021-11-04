package com.example.aventurasdemarcoyluis.model.itemsconfig;

import com.example.aventurasdemarcoyluis.model.characters.players.IPlayer;


/** Interface that models the functionalities that every item should have. */
public interface IItem {

	/**
	 * Gets the ItemType enum of the item.
	 * @return Returns the {@code ItemType}.
	 */
	ItemType getItemType();

	/**
	 * Generates the item's effect on the surrendered player.
	 * @param aPlayer Player receiving the effect.
	 */
	void applyToPlayer(IPlayer aPlayer);

}

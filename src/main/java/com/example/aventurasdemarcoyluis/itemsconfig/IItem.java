package com.example.aventurasdemarcoyluis.itemsconfig;

import com.example.aventurasdemarcoyluis.characters.playersconfig.IPlayer;


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

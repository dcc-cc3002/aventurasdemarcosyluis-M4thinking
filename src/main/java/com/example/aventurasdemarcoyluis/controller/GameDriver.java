package com.example.aventurasdemarcoyluis.controller;

import com.example.aventurasdemarcoyluis.model.characters.enemies.Boo;
import com.example.aventurasdemarcoyluis.model.characters.enemies.Goomba;
import com.example.aventurasdemarcoyluis.model.characters.enemies.Spiny;
import com.example.aventurasdemarcoyluis.model.characters.players.Luis;
import com.example.aventurasdemarcoyluis.model.characters.players.Marcos;
import com.example.aventurasdemarcoyluis.model.itemsconfig.ItemBag;
import com.example.aventurasdemarcoyluis.model.itemsconfig.items.HoneySyrup;
import com.example.aventurasdemarcoyluis.model.itemsconfig.items.RedMushroom;

/** Main class that handles the game execution. */
public class GameDriver {
	public static void main(String[] argv) {
		/* Create the main-characters/players */

		Marcos marcos = new Marcos(11, 9, 7, 5, 3);
		Luis luis = new Luis(12, 10, 8, 6, 4);
		//NullPlayer?

		/* Create enemies */

		Goomba goomba = new Goomba(2,4,15,8);
		Spiny spiny = new Spiny(5,4,18,8);
		Boo boo = new Boo(5,8,17,8);

		/* Create Items */

		RedMushroom redMushroom = new RedMushroom();
		HoneySyrup honeySyrup = new HoneySyrup();

		/* Instantiate main-characters/players chest */

		ItemBag chest = ItemBag.instance();
		chest.initializeEmpty();


	}

	/* Create the trunk of the main-characters/players */

	/* Implement shifts */

		/* Attack Turn */

		/* Turno para Ocupar un Item */

		/* Turno para Pasar */







	/* That a player can use an item to the trunk */

	/* Get the items from the trunk */

	/* Get all the characters of the turn */

	/* Remove a character from 'turn' when KO is */

	/* Know when the main-characters/players win or lose */

	/* Get the character that owns the current turn */

	/* Get the character of the next turn */

	/* End the current player's turn */
}

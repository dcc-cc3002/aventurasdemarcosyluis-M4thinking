package com.example.aventurasdemarcoyluis.model.characters.enemies.enemiesfactory;

import com.example.aventurasdemarcoyluis.model.characters.enemies.Boo;

public class BooFactory extends Boo implements EnemiesFactory {
	/**
	 * Creates a new Enemy "Boo".
	 *
	 * @param ATK attack points.
	 * @param DEF defense points.
	 * @param HP  heal points.
	 * @param LVL level of Boo.
	 */
	public BooFactory(int ATK, int DEF, int HP, int LVL) {
		super(ATK, DEF, HP, LVL);
	}

	@Override
	public Boo create() {
		return new Boo(atk,def,hp,lvl);
	}
}

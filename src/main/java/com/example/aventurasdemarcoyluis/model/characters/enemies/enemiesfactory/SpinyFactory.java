package com.example.aventurasdemarcoyluis.model.characters.enemies.enemiesfactory;

import com.example.aventurasdemarcoyluis.model.characters.enemies.Spiny;

public class SpinyFactory extends Spiny implements EnemiesFactory {
	private int factoryId;
	/**
	 * Creates a new Enemy "Spiny".
	 *
	 * @param ATK attack points.
	 * @param DEF defense points.
	 * @param HP  heal points.
	 * @param LVL level of Spiny.
	 */
	public SpinyFactory(int ATK, int DEF, int HP, int LVL) {
		super(ATK, DEF, HP, LVL);
		this.factoryId = 0;
	}

	@Override
	public Spiny create() {
		this.factoryId++;
		Spiny spiny = new Spiny(atk,def,hp,lvl);
		spiny.setId(factoryId);
		return spiny;
	}
}

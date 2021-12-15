package com.example.aventurasdemarcoyluis.model.characters.enemies.enemiesfactory;

import com.example.aventurasdemarcoyluis.model.characters.enemies.Goomba;

public class GoombaFactory extends Goomba implements EnemiesFactory {
	private int factoryId;
	/**
	 * Creates a new Enemy "Goomba".
	 *
	 * @param ATK attack points.
	 * @param DEF defense points.
	 * @param HP  heal points.
	 * @param LVL level of Goomba.
	 */
	public GoombaFactory(int ATK, int DEF, int HP, int LVL) {
		super(ATK, DEF, HP, LVL);
		this.factoryId = 0;
	}

	@Override
	public Goomba create() {
		this.factoryId++;
		Goomba goomba = new Goomba(atk,def,hp,lvl);
		goomba.setId(factoryId);
		return goomba;
	}
}

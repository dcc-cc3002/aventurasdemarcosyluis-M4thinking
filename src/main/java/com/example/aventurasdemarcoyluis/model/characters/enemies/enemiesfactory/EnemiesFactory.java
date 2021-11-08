package com.example.aventurasdemarcoyluis.model.characters.enemies.enemiesfactory;

import com.example.aventurasdemarcoyluis.model.characters.enemies.IEnemy;

/** Interface that every {@code IEnemy} factory must implement. */
public interface EnemiesFactory extends IEnemy {

	/**
	 * Create a new {@code IEnemy} according to the factory to be use.
	 * @return A new {@code IEnemy}.
	 */
	IEnemy create();
}

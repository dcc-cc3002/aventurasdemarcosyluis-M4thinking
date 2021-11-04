package com.example.aventurasdemarcoyluis.model.characters.enemies;

import com.example.aventurasdemarcoyluis.model.characters.players.IScaredPlayer;

/**
 * Every spectral enemy must implement this interface,
 * a spectral enemy only attacks {@code IScaredPlayer} and cannot
 * be attacked by players who are {@code IScaredPlayer}.
 */
public interface IEspectralEnemy extends IEnemy {

	/**
	 * Make a normal attack on the {@code IScaredPlayer}.
	 * @param aPlayer A scared player.
	 */
	void attack(IScaredPlayer aPlayer);

}

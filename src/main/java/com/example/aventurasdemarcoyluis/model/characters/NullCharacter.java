package com.example.aventurasdemarcoyluis.model.characters;

import com.example.aventurasdemarcoyluis.model.characters.enemies.IEspectralEnemy;
import com.example.aventurasdemarcoyluis.model.characters.enemies.IGenericEnemy;
import com.example.aventurasdemarcoyluis.model.characters.players.IGenericPlayer;
import com.example.aventurasdemarcoyluis.model.characters.players.IScaredPlayer;

import java.util.List;

public class NullCharacter extends AbstractCharacter {
	/** Creates a new NullCharacter. */
	public NullCharacter() {
		super(0, 0, 0, 0);
	}

	@Override
	public boolean invariant() {
		return true;
	}

	@Override
	public boolean isAttackableBy(IGenericEnemy anEnemy) {
		return false;
	}

	@Override
	public boolean isAttackableBy(IEspectralEnemy anEnemy) {
		return false;
	}

	@Override
	public boolean isAttackableBy(IGenericPlayer aPlayer) {
		return false;
	}

	@Override
	public boolean isAttackableBy(IScaredPlayer aPlayer) {
		return false;
	}

	@Override
	public boolean canUseOrReceiveItemInBattle() {
		return false;
	}

	@Override
	public List<ICharacter> getAttackableCharacters(List<ICharacter> characters) {
		return null;
	}
}

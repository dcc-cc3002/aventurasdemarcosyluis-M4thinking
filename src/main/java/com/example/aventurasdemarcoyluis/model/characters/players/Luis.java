package com.example.aventurasdemarcoyluis.model.characters.players;

import com.example.aventurasdemarcoyluis.model.characters.ICharacter;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.*;

import java.util.LinkedList;
import java.util.List;

/** Class that models the functionality of the player Luis. */
public class Luis extends AbstractPlayer implements IScaredPlayer {

    /**
     * Creates a new player "Luis".
     * @param ATK attack points
     * @param DEF defense points
     * @param HP  heal points
     * @param FP  fight points
     * @param LVL level of the Luis
     */
    public Luis(int ATK, int DEF, int HP, int FP, int LVL) {
        super(ATK, DEF, HP, FP, LVL);
    }

    @Override
    public void attack(IGenericEnemy anEnemy, AttackType anAttack){
        if (getFp() >= anAttack.fpCost) {
            anEnemy.attackedByLuis(this, anAttack);
        }
    }

    @Override
    public void attackedByBoo(Boo boo) {
        this.attackedByEnemy(boo);
    }

    @Override
    public void attackedBySpiny(Spiny spiny) {
        this.attackedByEnemy(spiny);
    }

    @Override
    public void attackedByGoomba(Goomba goomba) {
        this.attackedByEnemy(goomba);
    }

    @Override
    public void attackFromList(AttackType anAttack, int enemyIndex, List<ICharacter> attackableCharactersBy) {
        attack((IGenericEnemy) attackableCharactersBy.get(enemyIndex), anAttack);
    }

    @Override
    public void typicalPhrase() {
        print("Here we go!");
    }

    @Override
    public String toString() {
        return "Luis" + super.toString();
    }

    @Override
    public boolean isAttackableBy(IGenericEnemy anEnemy) {
        return true;
    }

    @Override
    public boolean isAttackableBy(IEspectralEnemy anEnemy) {
        return true;
    }

    @Override
    public List<ICharacter> getAttackableCharacters(List<ICharacter> characters) {
        List<ICharacter> attackableCharacters = new LinkedList<>();
        for(ICharacter attackableCharacter: characters){
            if(attackableCharacter.isAttackableBy(this)) {
                attackableCharacters.add(attackableCharacter);
            }
        }
        return attackableCharacters;
    }
}

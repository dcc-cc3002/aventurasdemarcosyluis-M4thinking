package com.example.aventurasdemarcoyluis.characters.players;

import com.example.aventurasdemarcoyluis.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.characters.enemies.Boo;
import com.example.aventurasdemarcoyluis.characters.enemies.Goomba;
import com.example.aventurasdemarcoyluis.characters.enemies.IGenericEnemy;
import com.example.aventurasdemarcoyluis.characters.enemies.Spiny;

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
    public void typicalPhrase() {
        print("Here we go!");
    }

    @Override
    public String toString() {
        return "Luis" + super.toString();
    }
}

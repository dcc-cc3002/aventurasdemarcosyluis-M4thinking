package com.example.aventurasdemarcoyluis.characters.players;

import com.example.aventurasdemarcoyluis.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.characters.enemies.Goomba;
import com.example.aventurasdemarcoyluis.characters.enemies.IEnemy;
import com.example.aventurasdemarcoyluis.characters.enemies.Spiny;

/** Class that models the functionality of the player Marcos. */
public class Marcos extends AbstractPlayer implements IGenericPlayer {

    /**
     * Creates a new player "Marcos".
     * @param ATK attack points.
     * @param DEF defense points.
     * @param HP  heal points.
     * @param FP  fight points.
     * @param LVL level of the Marcos.
     */
    public Marcos(int ATK, int DEF, int HP, int FP, int LVL) {
        super(ATK, DEF, HP, FP, LVL);
    }

    @Override
    public void attack(IEnemy anEnemy, AttackType anAttack){
        if (getFp() >= anAttack.fpCost) {
            anEnemy.attackedByMarcos(this, anAttack);
        }
    }

    @Override
    public void attackedByGoomba(Goomba anEnemy) {
        this.attackedByEnemy(anEnemy);
    }

    @Override
    public void attackedBySpiny(Spiny spiny) {
        this.attackedByEnemy(spiny);
    }

    @Override
    public void typicalPhrase() {
        print("Let's-a go!");
    }

    @Override
    public String toString() {
        return  "Marcos" + super.toString();
    }
}

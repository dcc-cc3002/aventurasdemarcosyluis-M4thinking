package com.example.aventurasdemarcoyluis.characters.playersconfig;

import com.example.aventurasdemarcoyluis.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.IEnemy;
import com.example.aventurasdemarcoyluis.characters.playersconfig.AbstractPlayer;
import com.example.aventurasdemarcoyluis.characters.playersconfig.IPlayer;
import com.example.aventurasdemarcoyluis.characters.playersconfig.IPlayerAttack;

/** Class that models the functionality of the player Luis. */
public class Luis extends AbstractPlayer implements IPlayer, IPlayerAttack {

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
    public void attack(IEnemy anEnemy, AttackType anAttack){
        anEnemy.attackByLuis(this, anAttack);
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

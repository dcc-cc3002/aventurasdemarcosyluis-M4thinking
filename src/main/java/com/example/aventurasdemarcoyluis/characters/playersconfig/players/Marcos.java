package com.example.aventurasdemarcoyluis.characters.playersconfig.players;

import com.example.aventurasdemarcoyluis.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.IEnemy;
import com.example.aventurasdemarcoyluis.characters.playersconfig.AbstractPlayer;
import com.example.aventurasdemarcoyluis.characters.playersconfig.IPlayer;
import com.example.aventurasdemarcoyluis.characters.playersconfig.IPlayerAttack;

/** Class that models the functionality of the player Marcos. */
public class Marcos extends AbstractPlayer implements IPlayer, IPlayerAttack {

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
        anEnemy.attackByMarcos(this, anAttack);
    }

    @Override
    public void attackedByBoo(IEnemy anEnemy){
        //Boo can't attack Marcos.
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

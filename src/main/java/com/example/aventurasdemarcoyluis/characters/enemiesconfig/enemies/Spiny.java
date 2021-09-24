package com.example.aventurasdemarcoyluis.characters.enemiesconfig.enemies;

import com.example.aventurasdemarcoyluis.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.AbstractEnemy;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.EnemyType;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.IEnemy;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.IEnemyAttack;
import com.example.aventurasdemarcoyluis.characters.playersconfig.IPlayer;

/** Class representing the behavior of the enemy Spiny. */
public class Spiny extends AbstractEnemy implements IEnemy, IEnemyAttack {

    /**
     * Creates a new Enemy "Spiny".
     * @param ATK attack points.
     * @param DEF defense points.
     * @param HP  heal points.
     * @param LVL level of Spiny.
     */
    public Spiny(int ATK, int DEF, int HP, int LVL) {
        super(ATK, DEF, HP, LVL, EnemyType.SPINY);
    }

    @Override
    public void attackedByPlayer(IPlayer aPlayer, AttackType anAttack) {
        //Bad practice, in the future maybe be a class/method will be implemented.
        if (anAttack == AttackType.SALTO) {
            aPlayer.setFp(aPlayer.getFp() - anAttack.fpCost);
            aPlayer.setHp( (int) ( (aPlayer.getHp() - aPlayer.getMaxHp() * 0.05) + 0.5) ); //Rounded int.
        }
        else {
            super.attackedByPlayer(aPlayer, anAttack);
        }
    }

    @Override
    public void insult() {
        print("I'm going to prick you. Ouch!");
    }

}

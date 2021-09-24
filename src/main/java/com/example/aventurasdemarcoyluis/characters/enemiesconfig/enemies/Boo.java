package com.example.aventurasdemarcoyluis.characters.enemiesconfig.enemies;

import com.example.aventurasdemarcoyluis.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.AbstractEnemy;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.EnemyType;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.IEnemy;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.IEnemyAttack;
import com.example.aventurasdemarcoyluis.characters.playersconfig.IPlayer;

/** Class representing the behavior of the enemy Boo. */
public class Boo extends AbstractEnemy implements IEnemy, IEnemyAttack {

    /**
     * Creates a new Enemy "Boo".
     * @param ATK attack points.
     * @param DEF defense points.
     * @param HP  heal points.
     * @param LVL level of Boo.
     */
    public Boo(int ATK, int DEF, int HP, int LVL) {
        super(ATK, DEF, HP, LVL, EnemyType.BOO);
    }

    @Override
    public void attack(IPlayer aPlayer){
        aPlayer.attackedByBoo(this);
    }

    @Override
    public void attackByLuis(IPlayer aPlayer, AttackType anAttack) {
        //Luis can't attack Boo.
    }

    @Override
    public void attackedByPlayer(IPlayer aPlayer, AttackType anAttack) {
        //Bad practice, in the future maybe be a class will be implemented.
        if (anAttack == AttackType.MARTILLO) {
            //The attack does nothing and the player loses the FP.
            aPlayer.setFp(aPlayer.getFp() - anAttack.fpCost);
        }
        else {
            super.attackedByPlayer(aPlayer, anAttack);
        }
    }

    @Override
    public void insult() {
        print("I'm Boo and I'm going to scare you!");
    }

}

package com.example.aventurasdemarcoyluis.characters.enemiesconfig;

import com.example.aventurasdemarcoyluis.characters.enemiesconfig.AbstractEnemy;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.EnemyType;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.IEnemy;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.IEnemyAttack;

/** Class representing the behavior of the enemy Goomba. */
public class Goomba extends AbstractEnemy implements IEnemy, IEnemyAttack {
    /**
     * Creates a new Enemy "Goomba".
     * @param ATK attack points.
     * @param DEF defense points.
     * @param HP  heal points.
     * @param LVL level of Goomba.
     */
    public Goomba(int ATK, int DEF, int HP, int LVL) {
        super(ATK, DEF, HP, LVL, EnemyType.GOOMBA);
    }

    /*
    OBS:An attack() or attackByGoomba() is not needed here because
    Goomba attacks all players equally, therefore you Goomba use the
    generic attackByEnemy method, in case it is needed for a new
    interaction, it is possible to add it without modifying the code.
     */

    @Override
    public void insult() {
        print("frrr, frrr, it's Gooomba!");
    }

}

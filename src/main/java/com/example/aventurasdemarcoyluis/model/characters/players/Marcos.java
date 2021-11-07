package com.example.aventurasdemarcoyluis.model.characters.players;

import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.*;

import java.util.LinkedList;
import java.util.List;

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
    public void attackedByBoo(Boo boo) {
        print("You cant attack Marcos");
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

    @Override
    public boolean isAttackableBy(IGenericEnemy anEnemy) {
        return true;
    }

    @Override
    public boolean isAttackableBy(IEspectralEnemy anEnemy) {
        return false;
    }


    @Override
    public List<IEnemy> getAttackableEnemies(List<IEnemy> listOfEnemies) {
        List<IEnemy> attackableEnemies = new LinkedList<>();
        for(IEnemy attackableEnemy: listOfEnemies){
            if(attackableEnemy.isAttackableBy(this) && !this.isKo()) {
                attackableEnemies.add(attackableEnemy);
            }
        }
        return attackableEnemies;
    }
}

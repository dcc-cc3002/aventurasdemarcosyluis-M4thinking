package com.example.aventurasdemarcoyluis.characters.enemies;

import com.example.aventurasdemarcoyluis.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.characters.players.IPlayer;
import com.example.aventurasdemarcoyluis.characters.players.Luis;
import com.example.aventurasdemarcoyluis.characters.players.Marcos;

/** Class representing the behavior of the enemy Spiny. */
public class Spiny extends AbstractEnemy implements IGenericEnemy, ISpecialReactionEnemy {

    /**
     * Creates a new Enemy "Spiny".
     *
     * @param ATK attack points.
     * @param DEF defense points.
     * @param HP  heal points.
     * @param LVL level of Spiny.
     */
    public Spiny(int ATK, int DEF, int HP, int LVL) {
        super(ATK, DEF, HP, LVL, EnemyType.SPINY);
    }

    @Override
    public void insult() {
        print("I'm going to prick you. Ouch!");
    }

    @Override
    public void attack(IPlayer aPlayer) {
        aPlayer.attackedBySpiny(this);
    }

    @Override
    public void attackedByMarcos(Marcos marcos, AttackType anAttack) {
        this.attackCondition(marcos, anAttack);
    }

    @Override
    public void attackedByLuis(Luis luis, AttackType anAttack) {
            this.attackCondition(luis, anAttack);
    }

    /**
     * Check the special condition of Spiny, of
     * not taking damage from the {@code AttackType.SALTO}, and deducting
     * health, a 5% of the maximum health points from the player in that case.
     * <p>
     *     If not, the attack is carried out normally.
     * </p>
     * @param aPlayer The player who performs the attack.
     * @param anAttack The player attack.
     */
    @Override
    public void attackCondition(IPlayer aPlayer, AttackType anAttack) {
        if (anAttack == AttackType.SALTO) {
            aPlayer.setFp(aPlayer.getFp() - anAttack.fpCost);
            aPlayer.setHp((int) ((aPlayer.getHp() - aPlayer.getMaxHp() * 0.05) + 0.5)); //Rounded int.
        } else {
            super.attackedByPlayer(aPlayer, anAttack);
        }
    }
}


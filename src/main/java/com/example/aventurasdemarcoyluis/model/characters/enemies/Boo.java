package com.example.aventurasdemarcoyluis.model.characters.enemies;

import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.players.IGenericPlayer;
import com.example.aventurasdemarcoyluis.model.characters.players.IPlayer;
import com.example.aventurasdemarcoyluis.model.characters.players.IScaredPlayer;
import com.example.aventurasdemarcoyluis.model.characters.players.Marcos;

/** Class representing the behavior of the enemy Boo. */
public class Boo extends AbstractEnemy implements IEspectralEnemy, ISpecialReactionEnemy {

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
    public void attack(IScaredPlayer aPlayer){
        aPlayer.attackedByBoo(this);
    }

    @Override
    public void insult() {
        print("I'm Boo and I'm going to scare you!");
    }

    @Override
    public void attackedByMarcos(Marcos marcos, AttackType anAttack) {
        this.attackCondition(marcos, anAttack);
    }

    /**
     * Check Boo's special condition of not taking damage
     * from the {@code AttackType.MARTILLO}.
     * <p>
     *     If not, the attack is carried out normally.
     * </p>
     * @param aPlayer The player who performs the attack.
     * @param anAttack The player attack.
     */
    @Override
    public void attackCondition(IPlayer aPlayer, AttackType anAttack) {
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
    public boolean isAttackableBy(IGenericPlayer aPlayer) {
        return true;
    }

    @Override
    public boolean isAttackableBy(IScaredPlayer aPlayer) {
        return false;
    }
}

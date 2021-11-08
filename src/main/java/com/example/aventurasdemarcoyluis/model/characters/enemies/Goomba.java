package com.example.aventurasdemarcoyluis.model.characters.enemies;

import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.players.*;

import java.util.LinkedList;
import java.util.List;

/** Class representing the behavior of the enemy Goomba. */
public class Goomba extends AbstractEnemy implements IGenericEnemy {
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
        print("frrr, frrr, it's Goomba!");
    }

    @Override
    public void attack(IPlayer aPlayer) {
        aPlayer.attackedByGoomba(this);
    }

    @Override
    public void attackedByMarcos(Marcos marcos, AttackType anAttack) {
        this.attackedByPlayer(marcos, anAttack);
    }

    @Override
    public void attackedByLuis(Luis luis, AttackType anAttack){
        this.attackedByPlayer(luis, anAttack);
    }

    @Override
    public boolean isAttackableBy(IGenericPlayer aPlayer) {
        return true;
    }

    @Override
    public boolean isAttackableBy(IScaredPlayer aPlayer) {
        return true;
    }

    @Override
    public List<IPlayer> getAttackablePlayers(List<IPlayer> listOfPlayers) {
        List<IPlayer> attackablePlayers = new LinkedList<>();
        for(IPlayer attackablePlayer: listOfPlayers){
            if(attackablePlayer.isAttackableBy(this) && !this.isKo()) {
                attackablePlayers.add(attackablePlayer);
            }
        }
        return attackablePlayers;
    }
}

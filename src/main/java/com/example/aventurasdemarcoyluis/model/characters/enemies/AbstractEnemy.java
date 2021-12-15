package com.example.aventurasdemarcoyluis.model.characters.enemies;

import com.example.aventurasdemarcoyluis.model.characters.AbstractCharacter;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.players.IPlayer;

import java.util.Objects;
import java.util.Random;

/** Class that represent an enemy in the game. Every enemy must extend this class. */
public abstract class AbstractEnemy extends AbstractCharacter implements IEnemy {
    private EnemyType type;
    private long failSeed;
    protected int id;

    /**
     * Creates a new Enemy
     * <p>
     *     Each enemy, in addition to being a character,
     *     has a {@code EnemyType} enum identifier.
     *     Added to this, an enemy can attack players
     *     and be attacked by them.
     * </p>
     * @param ATK attack points.
     * @param DEF defense points.
     * @param HP heal points.
     * @param LVL level of the enemy.
     * @param type enemy type.
     */
    protected AbstractEnemy(int ATK, int DEF, int HP, int LVL, EnemyType type ){
        super(ATK, DEF, HP, LVL);
        this.type = type;
        this.failSeed = 0;
        this.id = 0;
    }

    /* Main characteristics of IEnemy */

    @Override
    public void setFailSeed(long failSeed){
        this.failSeed = failSeed;
    }

    @Override
    public EnemyType getType() {
        return type;
    }

    @Override
    public void setType(EnemyType aType) {
        this.type = aType;
    }

    @Override
    public boolean invariant() {
        return (this.maxHp>= this.hp && this.hp>= 0);
    }

    @Override
    public abstract void attack(IPlayer aPlayer);

    /*Attack logic from IEnemy Attack*/

    @Override
    public void attackedByPlayer(IPlayer aPlayer, AttackType anAttack) {
        //If negative, hp becomes 0, and enemy will be K.O.
        int damage = damageReceived(aPlayer, anAttack);

        //Random Boolean with the probability of failing the attack
        Random rand = new Random();
        if(failSeed!=0){
            rand.setSeed(failSeed);
        }
        boolean doRandomAttack = rand.nextDouble() > anAttack.failProbability;
        if ( doRandomAttack ) { this.setHp(this.getHp() - damage);}
        //Fp discount
        aPlayer.setFp(aPlayer.getFp() - anAttack.fpCost);
    }

    @Override
    public int damageReceived(IPlayer aPlayer, AttackType anAttack){
        if ( aPlayer.isKo() ) {
            return 0;
        }
        else {//The result is rounded to integer
            return (int) ( anAttack.attackConstant * aPlayer.getAtk() * ( (double) aPlayer.getLvl() / (double) this.getDef() ) + 0.5 );
        }
    }

    @Override
    public abstract void insult();

    @Override
    public int getId(){
        return id;
    }

    @Override
    public void setId(int id){
        this.id = id;
    }

    /**
     * Overwriting the equals method based on enemy's statistics of {@code atk},
     * {@code def}, {@code hp}, {@code lvl}, {@code maxHp}, {@code EnemyType} and {@code getClass} method.
     *
     * @param o The object to compare with this.
     * @return {@code true}, if the objects are equal in statistical terms, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IEnemy anEnemy)) {
            return false;
        }
        return getAtk() == anEnemy.getAtk()
                && getDef() == anEnemy.getDef()
                && getHp() == anEnemy.getHp()
                && getLvl() == anEnemy.getLvl()
                && getMaxHp() == anEnemy.getMaxHp()
                && getType() == anEnemy.getType()
                && getClass() == anEnemy.getClass()
                && getId() == anEnemy.getId();
    }

    /**
     * Hash Code based on statistics of {@code atk},{@code def},
     * {@code hp}, {@code lvl}, {@code maxHp}, {@code EnemyType} and {@code getClass} method.
     *
     * @return HashCode type encryption of the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                getAtk(),
                getDef(),
                getHp(),
                getMaxHp(),
                getLvl(),
                getType(),
                getClass());
    }

    /**
     * Reads the {@code IEnemy} object and returns a {@code String} with
     * the label of the enemy and its statistics {@code atk}, {@code def},
     * {@code hp}, {@code maxHp} and {@code lvl}.
     *
     * @return Returns the enemy label and all statistics with its respective value.
     */
    @Override
    public String toString() {
        return type+"{" +
                "atk=" + atk +
                ", def=" + def +
                ", hp=" + hp +
                ", maxHp=" + maxHp +
                ", lvl=" + lvl +
                '}';
    }
}

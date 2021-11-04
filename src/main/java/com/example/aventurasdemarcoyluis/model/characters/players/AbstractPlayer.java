package com.example.aventurasdemarcoyluis.model.characters.players;

import com.example.aventurasdemarcoyluis.model.characters.AbstractCharacter;
import com.example.aventurasdemarcoyluis.model.characters.enemies.IEnemy;
import com.example.aventurasdemarcoyluis.model.itemsconfig.IItem;
import com.example.aventurasdemarcoyluis.model.itemsconfig.ItemBag;

import java.util.Objects;

/**
 * Class that represent a player in the game. Every player must extend this class.
 *
 *  @author Sebastián Guzmán O.
 */
public abstract class AbstractPlayer extends AbstractCharacter implements IPlayer {
    private int fp;
    private int maxFp;
    private final ItemBag armament;

    /**
     * Creates a new player.
     * <p>
     *     Each player, in addition to being a character,
     *     has the notion of {@code Item} in his armament of type {@code ItemBag}.
     *     Added to this, the player will have a current and maximum number
     *     of fight points ({@code fp}), the notion of invincibility and can
     *     attack enemies or be attacked by them.
     * </p>
     * @param ATK attack points.
     * @param DEF defense points.
     * @param HP  heal points.
     * @param FP  Current and max fighting points in the first instance.
     * @param LVL level of the player.
     */
    protected AbstractPlayer(int ATK, int DEF, int HP, int FP, int LVL) {
        super(ATK, DEF, HP, LVL);
        fp = FP;
        maxFp = FP;
        armament = ItemBag.instance();
    }

    /* Main characteristics of IPlayer. */

    @Override
    public int getFp() {
        assert invariant();
        return fp;
    }

    @Override
    public void setFp(int anFp) {
        if (anFp <= 0) {
            this.fp = 0;
        } else {
            this.fp = Math.min(anFp, maxFp);
        }
        assert invariant();
    }

    @Override
    public int getMaxFp() {
        assert invariant();
        return maxFp;
    }

    @Override
    public void setMaxFp(int anFp) {
        if (anFp > 0) {
            this.maxFp = anFp;
            if (maxFp < fp) {
                this.setFp(maxFp);
            }
        } else {
            this.maxFp = 0;
            this.setFp(0);
        }
    }

    @Override
    public boolean invariant() {
        return (this.maxHp >= this.hp && this.hp >= 0)
                && (this.maxFp >= this.fp && this.fp >= 0);
    }

    @Override
    public boolean canUseOrReceiveItemInBattle(){
        return true;
    }

    @Override
    public boolean isAttackableBy(IGenericPlayer aPlayer) {
        return false;
    }

    @Override
    public boolean isAttackableBy(IScaredPlayer aPlayer) {
        return false;
    }

    @Override
    public void addItem(IItem anItem) {
        armament.addItem(anItem);
    }

    @Override
    public void selectItem(IItem anItem) {
        boolean itemExists = armament.itemExists(anItem);
        if (itemExists) {
            armament.removeItem(anItem);
            anItem.applyToPlayer(this);
        }
        else {
            print("You don't have this item yet");
        }
    }

    @Override
    public ItemBag getArmament() {
        return armament;
    }

    @Override
    public void levelUp(){
        this.setAtk((int) (getAtk()*1.15));
        this.setDef((int) (getDef()*1.15));
        this.setMaxHp((int) (getMaxHp()*1.15));
        this.setMaxFp((int) (getMaxFp()*1.15));
        this.setLvl(getLvl()+1);
    }

    @Override
    public abstract void typicalPhrase();

    /* Attack logic from IPlayerAttack. */

    @Override
    public void attackedByEnemy(IEnemy anEnemy) {
        int damage = damageReceived(anEnemy);
        //If negative, hp becomes 0, and player will be K.O.
        this.setHp(this.getHp() - damage);
    }

    @Override
    public int damageReceived(IEnemy anEnemy) {
        if ( anEnemy.isKo()) {
            return 0;
        }
        else { //The result is rounded to integer
            return (int) ( 0.75 * anEnemy.getAtk() * ( (double) anEnemy.getLvl() / (double) this.getDef() ) + 0.5 );
        }
    }

    /* Identifying methods. */

    /**
     * Method that distinguishes when two {@code IPlayer} are equal based
     * on their statistics {@code atk}, {@code def}, {@code hp}, {@code lvl},
     * {@code fp}, {@code maxHp}, {@code maxFp} and {@code getClass} method.
     *
     * @param o The object to be compared.
     * @return  {@code true} if the players are the same object or are equal
     * in terms of statistics, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IPlayer aPlayer)) {
            return false;
        }
        return  getAtk() == aPlayer.getAtk()
                && getDef() == aPlayer.getDef()
                && getMaxHp() == aPlayer.getMaxHp()
                && getHp() == aPlayer.getHp()
                && getMaxFp() == aPlayer.getMaxFp()
                && getFp() == aPlayer.getFp()
                && getLvl() == aPlayer.getLvl()
                && getClass() == aPlayer.getClass();
    }

    /**
     * Hash Code based on player's statistics of {@code atk},{@code def},
     * {@code hp}, {@code maxHp}, {@code lvl}, {@code maxHf},
     * {@code fp} and {@code getClass} method.
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
                getMaxFp(),
                getFp(),
                getClass());
    }

    /**
     * Reads the {@code IPlayer} object and returns a {@code String} with
     * its statistics {@code atk}, {@code def}, {@code hp}, {@code maxHp},
     * {@code lvl},{@code fp} and {@code maxFp} and the count of purchased items.
     *
     * @return Returns each statistic with its respective
     * value and each item with its available quantity.
     */
    @Override
    public String toString() {
        return "{" +
                "atk=" + getAtk() +
                ", def=" + getDef() +
                ", hp=" + getHp() +
                ", maxHp=" + getMaxHp() +
                ", lvl=" + getLvl() +
                ", fp=" + getFp() +
                ", maxFp=" + getMaxFp() +
                ", armament=" + getArmament() +
                '}';
    }

}


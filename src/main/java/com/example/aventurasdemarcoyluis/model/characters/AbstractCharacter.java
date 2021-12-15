package com.example.aventurasdemarcoyluis.model.characters;

import java.util.Random;

/**
 * Represents the character's behavior.
 * <p>
 *     Abstract class with the methods that
 *     every character in the game must have.
 * </p>
 */
public abstract class AbstractCharacter implements ICharacter{
    protected int atk;
    protected int def;
    protected int hp;
    protected int maxHp;
    protected int lvl;
    protected long seed;

    /**
     * Creates a new Character.
     * @param ATK attack points.
     * @param DEF defense points.
     * @param HP  heal points.
     * @param LVL level of the character.
     */
    protected AbstractCharacter(int ATK, int DEF, int HP, int LVL){
        def = DEF;
        hp = HP;
        maxHp = HP;
        lvl = LVL;
        setAtk(ATK);
        this.seed = 0;
    }

    @Override
    public void setSeed(long seed){
        this.seed = seed;
    }

    @Override
    public int dice(int numberOfFaces) {
        Random rand = new Random();
        if (seed != 0) {
            rand.setSeed(seed);
        }
        return rand.nextInt(numberOfFaces);
    }

    @Override
    public int getAtk() {
        assert invariant();
        return atk;
    }

    @Override
    public void setAtk(int atk) {
        this.atk = atk;
    }

    @Override
    public int getDef() {
        return def;
    }

    @Override
    public void setDef(int def) {
        this.def = def;
    }

    @Override
    public int getHp() {
        assert invariant();
        return hp;
    }

    @Override
    public void setHp(int anHp) {
        if ( anHp <= 0 ) {
            this.hp = 0;
        }
        else{
            this.hp = Math.min(anHp, maxHp);
        }
        assert invariant();
    }

    @Override
    public int getMaxHp() {
        assert invariant();
        return maxHp;
    }

    @Override
    public void setMaxHp(int anHp) {
        if ( anHp > 0 ) {
            this.maxHp = anHp;
            if ( maxHp < hp ) {
                this.setHp(maxHp);
            }
        }
        else {
            this.maxHp = 0;
            this.setHp(0);
        }
    }

    @Override
    public int getLvl() {
        return lvl;
    }

    @Override
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public boolean isKo() {
        return this.hp == 0;
    }

    @Override
    public void print(String output) {
        System.out.println(output);
    }

    @Override
    public abstract boolean invariant();

    @Override
    public abstract String getName();
}


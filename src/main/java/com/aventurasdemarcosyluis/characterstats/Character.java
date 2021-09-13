package com.aventurasdemarcosyluis.characterstats;

public abstract class Character {
    protected int atk;
    protected int def;
    protected int hp;
    protected int lvl;

    /**
     * Creates a new Enemy
     * @param ATK attack points
     * @param DEF defense points
     * @param HP  heal points
     * @param LVL level of the Unit
     *
     */
    public Character(int ATK, int DEF, int HP, int LVL){
        def = DEF;
        hp = HP;
        lvl = LVL;
        setAtk(ATK);
    }

    public int getAtk() {
        assert invariant();
        return atk;
    }

    public void setAtk(int atk) {
        if (isKO()){
            this.atk = 0 ;
        }
        else {
            this.atk = atk;
        }
        assert invariant();
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getHp() {
        assert invariant();
        return hp;
    }

    public void setHp(int hp) {
        if (hp == 0){
            setAtk(0);
        }
        else{
            this.hp = hp;
        }
        assert invariant();
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public boolean isKO() {
        return this.hp == 0;
    }

    protected boolean invariant() {
        if (isKO()){
            return atk == 0;
        }
        return this.hp > 0;
    }

    @Override
    public abstract boolean equals(Object o);
}


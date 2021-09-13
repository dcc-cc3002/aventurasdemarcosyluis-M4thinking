package com.aventurasdemarcosyluis.characterstats;

import com.aventurasdemarcosyluis.types.EnemyType;

public class Enemy extends Character{
    private EnemyType type;

    /**
     * Creates a new Enemy
     * @param ATK attack points
     * @param DEF defense points
     * @param HP  heal points
     * @param LVL level of the Unit
     *
     */
    public Enemy(int ATK, int DEF, int HP, int LVL, EnemyType t ){
        super(ATK, DEF, HP, LVL);
        type=t;
    }

    public EnemyType getType() {
        return type;
    }

    public void setType(EnemyType aType) {
        this.type = aType;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enemy)) return false;
        Enemy enemies = (Enemy) o;
        return atk == enemies.atk
                && def == enemies.def
                && hp == enemies.hp
                && lvl == enemies.lvl
                && getType() == enemies.getType();
    }


}

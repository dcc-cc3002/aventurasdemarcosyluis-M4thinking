package com.aventurasdemarcosyluis.characterstats;


import java.util.List;

/**
 * Class that represent a Player in the game
 *
 *  @author Sebastián Guzmán O.
 */
public class Player extends Character {
    private int fp;
    public List<Items> armamento;

    /**
     * Creates a new player
     * @param ATK attack points
     * @param DEF defense points
     * @param HP  heal points
     * @param FP  fight points
     * @param LVL level of the Unit
     *
     */
    public Player(int ATK, int DEF, int HP, int FP, int LVL ){
        super(ATK, DEF, HP, LVL);
        fp=FP;
    }

    public int getFp() {
        return fp;
    }

    public void setFp(int fp) {this.fp = fp;}

    public void addAItem(Items a){
        armamento.add(a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return atk == player.atk
                && def == player.def
                && hp == player.hp
                && lvl == player.lvl
                && fp == player.fp;
    }







}

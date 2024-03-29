package com.example.aventurasdemarcoyluis.model.characters;

/** Interface that models any character within the video game. */
public interface ICharacter {

    /**
     * Seed to fix the results, if it is 0 it is understood randomness
     * @param seed New seed to be stable.
     */
    void setSeed(long seed);

    /**
     * Abstraction of a dice with homogeneous probability
     * distribution with respect to its faces, is
     * deterministic if the current seed is different from zero.
     * @param numberOfFaces Number of faces of the dice.
     * @return Face result indexed from zero.
     */
    int dice(int numberOfFaces);

    /**
     * Gets the attack points.
     * @return Attack points.
     */
    int getAtk();

    /**
     * Sets the attack points.
     * @param atk Attack points.
     */
    void setAtk(int atk);

    /**
     * Gets the defense points.
     * @return Defense points.
     */
    int getDef();

    /**
     * Sets the defense points.
     * @param def Defense points.
     */
    void setDef(int def);

    /**
     * Gets the current health points.
     * @return Health points.
     */
    int getHp();

    /**
     * Sets the current health points.
     * <p>
     *     It will always be forced to be
     *     between 0 and the maximum health.
     * </p>
     * @param anHp Updated health points
     */
    void setHp(int anHp);

    /**
     * Gets the maximum health points.
     * @return Maximum health points.
     */
    int getMaxHp();

    /**
     * Sets the maximum health points.
     * <p>
     *     The maximum life is always greater than 0,
     *     if it is less than the current life, the
     *     latter must be equal to the maximum.
     * </p>
     * @param anHp Maximum health points.
     */
    void setMaxHp(int anHp);

    /**
     * Gets the character's level.
     * @return Character's level.
     */
    int getLvl();

    /**
     * Sets the character's level.
     * @param lvl Character's level.
     */
    void setLvl(int lvl);

    /**
     * {@code boolean} of whether the character has
     * health points available or not, that is, it
     * checks if it is defeated.
     *
     * @return True if defeated, false if not.
     */
    boolean isKo();

    /**
     * Abbreviates the nomenclature of {@code System.out.println()}.
     * @param output Message that will be sent to the console.
     */
    void print(String output);

    /**
     * Abstract method that must
     * be written in each child class
     * of the abstract class that
     * implements this interface.
     *<p>
     *     Verifies that the character
     *     stat restrictions and limit
     *     cases are met.
     *</p>
     * @return {@code true} if constraints and edge cases are met, false if not.
     */
    boolean invariant();

    /**
     * Get the character's name
     * @return Character's name
     */
    String getName();

}

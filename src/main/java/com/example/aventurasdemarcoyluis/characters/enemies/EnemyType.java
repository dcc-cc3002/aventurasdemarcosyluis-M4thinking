package com.example.aventurasdemarcoyluis.characters.enemies;

/** All enemies must have their EnemyType enum. */
public enum EnemyType {
    GOOMBA("Goomba"),BOO("Boo"),SPINY("Spiny");

    public final String label;

    /**
     * Each {@code EnemyType} must have its associated label.
     * @param label Label representative of the type of the enemy.
     */
    EnemyType(String label) {
        this.label = label;
    }

    /**
     * Deliver the enemy label.
     * @return Return the enemy label.
     */
    @Override
    public String toString() {
        return label;
    }
}


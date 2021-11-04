package com.example.aventurasdemarcoyluis.model.characters.attackconfig;

/** Enumerator that represents de type of attack that a player can choose. */
public enum AttackType {
    SALTO("Salto", 0, 1, 1),
    MARTILLO("Martillo", 0.25, 2,1.5);

    public final String label;
    public final double failProbability;
    public final double attackConstant;
    public final int fpCost;

	/**
	 * Each attack must have its associated {@code AttackType},
     * along with the parameters to be used.
     * @param label Label representing the type of attack.
     * @param failProbability Probability of missing the attack.
     * @param fpCost Attack points it costs to use the attack type.
     * @param attackConstant Constant that weights the damage attack.
     */
    AttackType(String label, double failProbability, int fpCost, double attackConstant) {
        this.label = label;
        this.failProbability = failProbability;
        this.attackConstant = attackConstant;
        this.fpCost = fpCost;
    }

}


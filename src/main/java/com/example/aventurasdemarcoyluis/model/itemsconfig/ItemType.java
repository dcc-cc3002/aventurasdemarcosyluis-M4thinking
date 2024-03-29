package com.example.aventurasdemarcoyluis.model.itemsconfig;

/** Enumerator that represents the items that can be use in the game. */
public enum ItemType {
    RED_MUSHROOM("Red Mushroom"), HONEY_SYRUP("Honey Syrup");//STAR("Star") Deprecated

    public final String label;

    /**
     * Each item must have its associated {@code ItemType},
     * along with the parameters to be used.
     * @param label Label representing the item.
     */
    ItemType(String label) {
        this.label = label;
    }

    /**
     * Deliver the item label.
     * @return Item label.
     */
    @Override
    public String toString() {
        return label;
    }

}

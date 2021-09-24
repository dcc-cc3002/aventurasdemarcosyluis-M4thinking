package com.example.aventurasdemarcoyluis.itemsconfig;

import com.example.aventurasdemarcoyluis.characters.playersconfig.IPlayer;

import java.util.Objects;

/**
 * Class that models the functionality of every item.
 * <p>
 *     Every item must extend this class.
 * </p>
 */
public abstract class AbstractItem implements IItem{
    protected ItemType type;

    /**
     * Create a new item with its respective {@code ItemType}.
     * @param anItemType Type of item to be generated.
     */
    protected AbstractItem(ItemType anItemType) {
        this.type = anItemType;
    }

    public ItemType getItemType() { return this.type;}

    @Override
    public abstract void applyToPlayer(IPlayer aPlayer);

    /**
     * Method that distinguishes when two {@code IItem} are equal based on their {@code ItemType}.
     * @param o The object to be compared
     * @return  {@code true} if the items are the same object or are equal
     * in terms of statistics, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if ( !(o instanceof IItem item) ) {return false;}
        return type == item.getItemType();
    }
    /**
     * Hash Code based on item {@code ItemType}.
     * @return HashCode type encryption of the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

}

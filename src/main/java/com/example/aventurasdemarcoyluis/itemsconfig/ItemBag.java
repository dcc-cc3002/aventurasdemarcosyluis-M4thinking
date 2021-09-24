package com.example.aventurasdemarcoyluis.itemsconfig;

import java.util.EnumMap;
import java.util.Map;

/** Class that models the armament (ItemBag) of the players. */
public class ItemBag {
    //Enum map that stores the types of each item and its quantity
    private final Map<ItemType, Integer> items = new EnumMap<>(ItemType.class);

    /** Constructor of the ItemBag that initializes the items with quantity 0. */
    public ItemBag() {
        for ( ItemType itemType : ItemType.values() ) {
            items.put(itemType, 0);
        }
    }

    /**
     * Get the quantity of a certain item in the {@code ItemBag}.
     * @param anItem Item you want to know its quantity.
     * @return Returns the amount that the {@code ItemBag} has of that item.
     */
    public int getItemQuantity(IItem anItem) {
        return items.get(anItem.getItemType());
    }

    /**
     * Add one to the quantity of the item in the {@code ItemBag}.
     * @param anItem Item to be added.
     */
    public void addItem(IItem anItem) {
        int itemQuantity = this.getItemQuantity(anItem);
        items.put(anItem.getItemType(), itemQuantity + 1);
    }

    /**
     * Checks if an item exists in the armament, that is, its quantity is greater than 0.
     * @param anItem Item that will be verified in the {@code ItemBag}.
     * @return {@code true} if it exists, {@code false} if not.
     */
    public boolean itemExists(IItem anItem) {
        return this.getItemQuantity(anItem) > 0;
    }

    /**
     * Discounts 1 to the quantity of the item in the {@code ItemBag},
     * if it does not exist, the quantity remains at 0.
     *
     * @param anItem Item that will be discounted.
     */
    public void removeItem(IItem anItem) {
        if ( this.itemExists(anItem) ) {
            int itemQuantity = this.getItemQuantity(anItem);
            items.put(anItem.getItemType(), itemQuantity - 1);
        }
    }
    /**
     * Deliver the item labels on the {@code ItemBag}.
     * @return Return the labels of the items contained in the armament.
     */
    @Override
    public String toString() {
        return "" + items;
    }
}

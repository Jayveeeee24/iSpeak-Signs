package com.artemis.ispeaksigns.adapter_list_favorite;

public class FavoriteCategoryItem {
    private String itemName;
    private String itemType;

    public FavoriteCategoryItem(String itemName, String itemType) {
        this.itemName = itemName;
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}

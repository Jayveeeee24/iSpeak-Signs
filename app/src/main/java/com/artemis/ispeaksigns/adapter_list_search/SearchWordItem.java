package com.artemis.ispeaksigns.adapter_list_search;

public class SearchWordItem {
    private String itemName;

    public SearchWordItem(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "SearchWordItem{" +
                "itemName='" + itemName + '\'' +
                '}';
    }
}

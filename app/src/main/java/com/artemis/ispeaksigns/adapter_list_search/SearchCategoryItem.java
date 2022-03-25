package com.artemis.ispeaksigns.adapter_list_search;

public class SearchCategoryItem {
    private String searchItemName;
    private String itemCategory;

    public SearchCategoryItem(String searchItemName, String itemCategory) {
        this.searchItemName = searchItemName;
        this.itemCategory = itemCategory;
    }

    public String getSearchItemName() {
        return searchItemName;
    }

    public void setSearchItemName(String searchItemName) {
        this.searchItemName = searchItemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }
}

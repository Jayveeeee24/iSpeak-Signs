package com.artemis.ispeaksigns.adapter_list_favorite;

public class FavoriteCategoryItem {
    private String categoryName;

    public FavoriteCategoryItem(String categoryName) {
        this.categoryName = categoryName;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    @Override
    public String toString() {
        return "FavoriteCategoryItem{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}

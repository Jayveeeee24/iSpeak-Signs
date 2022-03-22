package com.artemis.ispeaksigns.home_list_adapter;

public class HomeCategoryItem {
    private String categoryName;
    private Integer bgColor;
    private Integer progress;
    private Integer imageUrl;

    public HomeCategoryItem(String categoryName, Integer bgColor, Integer progress, Integer imageUrl) {
        this.categoryName = categoryName;
        this.bgColor = bgColor;
        this.progress = progress;
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getBgColor() {
        return bgColor;
    }

    public void setBgColor(Integer bgColor) {
        this.bgColor = bgColor;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "LearnCategoryItem{" +
                "categoryName='" + categoryName + '\'' +
                ", bgColor=" + bgColor +
                ", progress=" + progress +
                ", imageUrl=" + imageUrl +
                '}';
    }
}

package com.artemis.ispeaksigns.adapter_list_home;

public class HomeVideoCategoryItem {
    private String itemName;
    private Integer bgColor;
    private Integer itemImage;

    public HomeVideoCategoryItem(String itemName, Integer bgColor, Integer itemImage) {
        this.itemName = itemName;
        this.bgColor = bgColor;
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getBgColor() {
        return bgColor;
    }

    public void setBgColor(Integer bgColor) {
        this.bgColor = bgColor;
    }

    public Integer getItemImage() {
        return itemImage;
    }

    public void setItemImage(Integer itemImage) {
        this.itemImage = itemImage;
    }
}

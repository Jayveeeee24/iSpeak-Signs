package com.artemis.ispeaksigns.adapter_list_profile;

public class ProfileProgressItem {

    private Integer imageBg;
    private Integer imageResource;
    private String itemName;
    private String itemPercent;
    private Integer itemProgress;

    public ProfileProgressItem(Integer imageBg, Integer imageResource, String itemName, String itemPercent, Integer itemProgress) {
        this.imageBg = imageBg;
        this.imageResource = imageResource;
        this.itemName = itemName;
        this.itemPercent = itemPercent;
        this.itemProgress = itemProgress;
    }

    public Integer getImageBg() {
        return imageBg;
    }

    public void setImageBg(Integer imageBg) {
        this.imageBg = imageBg;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPercent() {
        return itemPercent;
    }

    public void setItemPercent(String itemPercent) {
        this.itemPercent = itemPercent;
    }

    public Integer getItemProgress() {
        return itemProgress;
    }

    public void setItemProgress(Integer itemProgress) {
        this.itemProgress = itemProgress;
    }

    @Override
    public String toString() {
        return "ProfileProgressItem{" +
                "imageBg=" + imageBg +
                ", imageResource=" + imageResource +
                ", itemName='" + itemName + '\'' +
                ", itemPercent='" + itemPercent + '\'' +
                ", itemProgress=" + itemProgress +
                '}';
    }
}

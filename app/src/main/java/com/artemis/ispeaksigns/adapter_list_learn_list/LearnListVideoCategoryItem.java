package com.artemis.ispeaksigns.adapter_list_learn_list;

public class LearnListVideoCategoryItem {
    private String itemName;
    private Integer isLearned;

    public LearnListVideoCategoryItem(String itemName, Integer isLearned) {
        this.itemName = itemName;
        this.isLearned = isLearned;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getIsLearned() {
        return isLearned;
    }

    public void setIsLearned(Integer isLearned) {
        this.isLearned = isLearned;
    }
}

package com.artemis.ispeaksigns.adapter_list_learn_word;

public class LearnListWordCategoryItem {
    private String itemName;
    private Integer isLearned;

    public LearnListWordCategoryItem(String itemName, Integer isLearned) {
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

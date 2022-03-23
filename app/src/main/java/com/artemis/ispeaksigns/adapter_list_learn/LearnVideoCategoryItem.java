package com.artemis.ispeaksigns.adapter_list_learn;

public class LearnVideoCategoryItem {

    private String categoryName;
    private String categoryType;
    private String itemCount;
    private Integer learnItemParentBg;
    private Integer gotoParentBg;

    public LearnVideoCategoryItem(String categoryName, String categoryType, String itemCount, Integer learnItemParentBg, Integer gotoParentBg) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.itemCount = itemCount;
        this.learnItemParentBg = learnItemParentBg;
        this.gotoParentBg = gotoParentBg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getLearnItemParentBg() {
        return learnItemParentBg;
    }

    public void setLearnItemParentBg(Integer learnItemParentBg) {
        this.learnItemParentBg = learnItemParentBg;
    }

    public Integer getGotoParentBg() {
        return gotoParentBg;
    }

    public void setGotoParentBg(Integer gotoParentBg) {
        this.gotoParentBg = gotoParentBg;
    }

    @Override
    public String toString() {
        return "LearnVideoCategoryItem{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryType='" + categoryType + '\'' +
                ", itemCount='" + itemCount + '\'' +
                ", learnItemParentBg=" + learnItemParentBg +
                ", gotoParentBg=" + gotoParentBg +
                '}';
    }
}

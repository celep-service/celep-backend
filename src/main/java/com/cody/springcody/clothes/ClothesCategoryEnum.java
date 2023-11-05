package com.cody.springcody.clothes;

public enum ClothesCategoryEnum {
    TOP("상의"),
    BOTTOM("하의"),
    OUTER("아우터"),
    SHOES("신발"),
    BAG("가방"),
    ACCESSORY("악세사리");

    private final String clothesCategory;

    ClothesCategoryEnum(String clothesCategory) {
        this.clothesCategory = clothesCategory;
    }

    public String getClothesCategory() {
        return clothesCategory;
    }
}

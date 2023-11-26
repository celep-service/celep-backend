package com.celeb.clothes;

public enum ClothesCategoryEnum {
    TOP,
    BOTTOM,
    OUTER,
    SHOES,
    BAG,
    ACCESSORY;

    public String getClothesCategory() {
        return this.name();
    }
}

package com.celeb.celeb;

public enum CelebCategoryEnum {
    TALENT,
    MODEL,
    SINGER,
    ACTOR,
    INFLUENCER,
    ETC;

    public String getCategory() {
        return this.name();
    }
}

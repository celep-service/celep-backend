package com.celeb.celeb;

public enum CelebCategoryEnum {
    TALENT("방송인"),
    MODEL("모델"),
    SINGER("가수"),
    ACTOR("배우"),
    INFLUENCER("인플루언서"),
    ETC("기타");

    private final String category;

    CelebCategoryEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}

package com.cody.springcody.influencer;

public enum InfluencerCategoryEnum {
    TALENT("방송인"),
    MODEL("모델"),
    SINGER("가수"),
    ACTOR("배우"),
    ETC("기타");

    private final String category;

    InfluencerCategoryEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}

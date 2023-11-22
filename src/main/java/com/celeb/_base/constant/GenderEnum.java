package com.celeb._base.constant;

public enum GenderEnum {
    MALE("남자"),
    FEMALE("여자");

    private final String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

}

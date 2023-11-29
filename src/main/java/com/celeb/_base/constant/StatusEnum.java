package com.celeb._base.constant;

public enum StatusEnum {
    ACTIVE,
    INACTIVE;

    public String getStatus() {
        return this.name();
    }
}

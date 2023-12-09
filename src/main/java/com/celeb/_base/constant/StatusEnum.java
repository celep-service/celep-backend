package com.celeb._base.constant;

public enum StatusEnum {
    ACTIVE,
    // 비활성화: 계정
    INACTIVE,
    // 삭제: 게시글
    DELETED;

    public String getStatus() {
        return this.name();
    }
}

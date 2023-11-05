package com.cody.springcody._base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// 이렇게 하면 알아서 BaseEntity를 extends하는 엔티티는 아래와 같은 속성을 넣어줌.
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseTimeEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

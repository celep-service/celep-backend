package com.celeb.celeb;

import com.celeb._base.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Celeb extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private CelebCategoryEnum celebCategory;

    @Builder
    public Celeb(String name, String imageUrl, CelebCategoryEnum celebCategory) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.celebCategory = celebCategory;
    }

}

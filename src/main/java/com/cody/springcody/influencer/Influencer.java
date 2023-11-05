package com.cody.springcody.influencer;

import com.cody.springcody._base.entity.BaseTimeEntity;
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
public class Influencer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private InfluencerCategoryEnum influencerCategory;

    @Builder
    public Influencer(String name, String imageUrl, InfluencerCategoryEnum influencerCategory) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.influencerCategory = influencerCategory;
    }

}

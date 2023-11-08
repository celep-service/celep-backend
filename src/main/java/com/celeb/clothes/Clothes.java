package com.celeb.clothes;


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
public class Clothes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Enumerated(EnumType.STRING)
    private ClothesCategoryEnum clothesCategory;

    private String brand;
    private String gender;
    private String imageUrl;
    private String sellUrl;

    @Builder
    public Clothes(String name, String brand, String gender, String imageUrl, String sellUrl,
        ClothesCategoryEnum clothesCategory) {
        this.name = name;
        this.brand = brand;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.sellUrl = sellUrl;
        this.clothesCategory = clothesCategory;
    }

}

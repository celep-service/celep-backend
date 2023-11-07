package com.celeb.ClothesBookmark;


import com.celeb._base.entity.BaseTimeEntity;
import com.celeb.clothes.Clothes;
import com.celeb.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ClothesBookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @OneToOne
    private Clothes clothes;

    @Builder
    public ClothesBookmark(User user, Clothes clothes) {
        this.user = user;
        this.clothes = clothes;
    }


}

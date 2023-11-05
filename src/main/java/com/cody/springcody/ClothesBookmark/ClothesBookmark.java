package com.cody.springcody.ClothesBookmark;


import com.cody.springcody._base.entity.BaseTimeEntity;
import com.cody.springcody.clothes.Clothes;
import com.cody.springcody.user.User;
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

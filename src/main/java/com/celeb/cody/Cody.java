package com.celeb.cody;


import com.celeb._base.entity.BaseTimeEntity;
import com.celeb.clothes.Clothes;
import com.celeb.post.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Cody extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "clothes_id")
    private Clothes clothes;

    @Builder
    public Cody(Post post, Clothes clothes) {
        this.post = post;
        this.clothes = clothes;
    }


}

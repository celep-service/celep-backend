package com.celeb.postBookmark;

import com.celeb._base.entity.BaseTimeEntity;
import com.celeb.post.Post;
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
public class PostBookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @OneToOne
    private Post post;

    @Builder
    public PostBookmark(User user, Post post) {
        this.user = user;
        this.post = post;
    }

}

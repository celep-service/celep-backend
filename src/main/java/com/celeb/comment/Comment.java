package com.celeb.comment;

import com.celeb._base.entity.BaseTimeEntity;
import com.celeb.post.Post;
import com.celeb.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;

    @OneToOne
    private User user;

    @ManyToOne
    private Post post;


    @Builder
    public Comment(Integer id, String content, User user, Post post) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.post = post;
    }
}

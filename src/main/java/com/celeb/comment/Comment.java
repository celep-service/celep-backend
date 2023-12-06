package com.celeb.comment;

import com.celeb._base.entity.BaseTimeEntity;
import com.celeb.post.Post;
import com.celeb.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    private String status;


    @Builder
    public Comment(Integer id, String content, User user, Post post, String status) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.post = post;
        this.status = status;
    }
}

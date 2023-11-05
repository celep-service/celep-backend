package com.cody.springcody.comment;

import com.cody.springcody._base.entity.BaseTimeEntity;
import com.cody.springcody.post.Post;
import com.cody.springcody.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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


}

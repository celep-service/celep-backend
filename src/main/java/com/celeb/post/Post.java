package com.celeb.post;


import com.celeb.user.User;
import com.celeb._base.entity.BaseTimeEntity;
import com.celeb.celeb.Celeb;
import com.celeb.cody.Cody;
import com.celeb.comment.Comment;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String status;
    private String imageUrl;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;

    @OneToMany(mappedBy = "post")
    private List<Cody> codies;

    @OneToOne
    private Celeb celeb;


    @Builder
    public Post(Integer id, String title, String content, String status, String imageUrl,
        User user, List<Cody> codies, Celeb celeb) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.imageUrl = imageUrl;
        this.user = user;
        this.codies = codies;
        this.celeb = celeb;
    }


}

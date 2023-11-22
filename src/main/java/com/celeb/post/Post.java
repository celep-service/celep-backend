package com.celeb.post;


import com.celeb._base.constant.GenderEnum;
import com.celeb._base.entity.BaseTimeEntity;
import com.celeb.celeb.Celeb;
import com.celeb.cody.Cody;
import com.celeb.comment.Comment;
import com.celeb.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String status;
    private String imageUrl;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;

    @OneToMany(mappedBy = "post")
    private List<Cody> codies;

    @ManyToOne
    private Celeb celeb;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;


    @Builder
    public Post(Integer id, String title, String status, String imageUrl,
        User user, List<Cody> codies, Celeb celeb, GenderEnum gender) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.imageUrl = imageUrl;
        this.user = user;
        this.codies = codies;
        this.celeb = celeb;
        this.gender = gender;
    }


}

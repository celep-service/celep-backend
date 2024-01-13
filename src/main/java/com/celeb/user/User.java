package com.celeb.user;

import com.celeb._base.entity.BaseTimeEntity;
import com.celeb.post.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String role;
    private String password;
    private String status;
    private String gender;

    private String userProfileImage;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;


    @Builder
    public User(Integer id, String name, String email, String password,
        String status, String gender, String role, String userProfileImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.gender = gender;
        this.role = role;
        this.userProfileImage = userProfileImage;
    }
}

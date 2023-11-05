package com.cody.springcody.user;

import com.cody.springcody._base.entity.BaseTimeEntity;
import com.cody.springcody.post.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String status;
    private String gender;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;


    @Builder
    public User(Integer id, String name, String email, String password,
        String status, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.gender = gender;
    }
}

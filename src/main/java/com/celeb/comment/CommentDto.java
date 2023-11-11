package com.celeb.comment;

import com.celeb.post.Post;
import com.celeb.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {

    private Integer id;
    private String content;
    private User user;
    private Post post;

    private Integer userId;
    private Integer postId;

    public Comment toEntity() {
        return Comment.builder()
            .content(content)
            .user(user)
            .post(post)
            .build();
    }

}

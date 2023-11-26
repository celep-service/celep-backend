package com.celeb.comment;

import com.celeb._base.constant.StatusEnum;
import com.celeb.post.Post;
import com.celeb.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;

    private Integer userId;
    private Integer postId;

    private String userName;

    public static List<CommentDto> commentListResponse(List<Comment> postComments) {
        return postComments.stream()
            .map(CommentDto::commentResponse)
            .toList();
    }

    private static CommentDto commentResponse(Comment comment) {
        return CommentDto.builder()
            .id(comment.getId())
            .content(comment.getContent())
            .userId(comment.getUser().getId())
            .userName(comment.getUser().getName())
            .createdAt(comment.getCreatedAt())
            .updatedAt(comment.getUpdatedAt())
            .status(comment.getStatus())
            .build();
    }


    public Comment toEntity() {
        return Comment.builder()
            .content(content)
            .user(user)
            .post(post)
            .status(StatusEnum.ACTIVE.getStatus())
            .build();
    }

}

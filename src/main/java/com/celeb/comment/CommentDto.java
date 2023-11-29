package com.celeb.comment;

import com.celeb._base.constant.StatusEnum;
import com.celeb.post.Post;
import com.celeb.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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

    @Hidden
    private Integer id;
    @NotEmpty(message = "내용을 입력해주세요.")
    @Schema(description = "댓글 내용", example = "내용입니다.")
    private String content;
    @Hidden
    private User user;
    @Hidden
    private Post post;
    @Hidden
    private LocalDateTime createdAt;
    @Hidden
    private LocalDateTime updatedAt;
    @Hidden
    private String status;

    @Schema(description = "유저 아이디", example = "1")
    private Integer userId;
    @Schema(description = "포스트 아이디", example = "1")
    private Integer postId;

    @Hidden
    @Schema(description = "유저 이름", example = "홍길동")
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

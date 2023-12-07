package com.celeb.comment.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.Data;

/**
 * 댓글 수정 요청 DTO
 */

@Data
public class EditCommentRequestDto {

    @Hidden
    private Integer userId;
    private String content;
    private String imageUrl;
    private List<Integer> clothesIdList;
    @Pattern(regexp = "MALE|FEMALE", message = "성별은 MALE, FEMALE만 가능합니다.")
    @Schema(description = "성별", example = "MALE")
    private String gender;

    @NotNull(message = "포스트 아이디를 입력해주세요.")
    private Integer postId;

}

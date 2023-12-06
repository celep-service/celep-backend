package com.celeb.comment;

import com.celeb._base.dto.DataResponseDto;
import com.celeb._base.dto.EntityIdResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Tag(name = "댓글 관련 API", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 정보 생성", description = "댓글 정보를 만듭니다.")
    @PostMapping("")
    public DataResponseDto<EntityIdResponseDto> createComment(@RequestBody CommentDto commentDto) {
        return DataResponseDto.of(commentService.createComment(commentDto));
    }

    @Operation(summary = "댓글 정보 조회", description = "댓글 정보를 가져옵니다.")
    @GetMapping("")
    public DataResponseDto<List<CommentDto>> getComments(@RequestParam Integer postId) {
        return DataResponseDto.of(commentService.getComments(postId));
    }

    @Operation(summary = "댓글 정보 삭제", description = "댓글 정보를 삭제합니다.")
    @PatchMapping("{commentId}/delete")
    public DataResponseDto<EntityIdResponseDto> deleteComment(@PathVariable Integer commentId) {
        return DataResponseDto.of(commentService.deleteComment(commentId));
    }

}

package com.celeb.comment;

import com.celeb._base.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public DataResponseDto<Object> createComment(@RequestBody CommentDto commentDto) {
        return DataResponseDto.of(commentService.createComment(commentDto));
    }

}

package com.celeb.post;

import com.celeb._base.dto.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Tag(name = "게시글 관련 API", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;

    @Operation(summary = "포스트 정보 조회", description = "회원 정보가 삭제됩니다.", tags = {"게시글 관련 API"})
    @GetMapping("")
    public DataResponseDto<Object> getPosts(
        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
        String celebCategory, String search, Integer userId, String gender) {
        return DataResponseDto.of(
            postService.getPosts(pageable, celebCategory, search, userId, gender));
    }

    @PostMapping("")
    public DataResponseDto<Object> createPost(@Valid @RequestBody PostDto postDto) {
        return DataResponseDto.of(postService.createPost(postDto));
    }

}

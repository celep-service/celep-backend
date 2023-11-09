package com.celeb.post;

import com.celeb._base.dto.DataResponseDto;
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
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public DataResponseDto<Object> getPosts(
        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
        String celebCategory, String search) {
        return DataResponseDto.of(postService.getPosts(pageable, celebCategory, search));
    }

    @PostMapping("")
    public DataResponseDto<Object> createPost(@RequestBody PostDto postDto) {
        return DataResponseDto.of(postService.createPost(postDto));
    }

}

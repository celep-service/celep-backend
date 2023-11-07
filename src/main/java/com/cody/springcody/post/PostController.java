package com.cody.springcody.post;

import com.cody.springcody._base.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
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
    public DataResponseDto<Object> getPosts() {
        return DataResponseDto.of(postService.getPosts());
    }

    @PostMapping("")
    public DataResponseDto<Object> createPost(@RequestBody PostDto postDto) {
        return DataResponseDto.of(postService.createPost(postDto));
    }

}

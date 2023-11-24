package com.celeb.post;

import com.celeb._base.constant.Code;
import com.celeb._base.dto.DataResponseDto;
import com.celeb._base.exception.GeneralException;
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
        String celebCategory, String search, Integer userId, String gender) {
        return DataResponseDto.of(
            postService.getPosts(pageable, celebCategory, search, userId, gender));
    }

    @PostMapping("")
    public DataResponseDto<Object> createPost(@RequestBody PostDto postDto) {
        //Validation
        if (postDto.getTitle() == null || postDto.getTitle().isEmpty()) {
            throw new GeneralException(Code.VALIDATION_ERROR, "내용을 입력해주세요.");
        } else if (postDto.getCelebId() == null) {
            throw new GeneralException(Code.VALIDATION_ERROR, "셀럽을 선택해주세요.");
        } else if (postDto.getClothesIdList() == null || postDto.getClothesIdList().isEmpty()) {
            throw new GeneralException(Code.VALIDATION_ERROR, "코디를 선택해주세요.");
        } else if (postDto.getGender() == null || postDto.getGender().isEmpty()) {
            throw new GeneralException(Code.VALIDATION_ERROR, "성별을 선택해주세요.");
        } else if (postDto.getImageUrl() == null || postDto.getImageUrl().isEmpty()) {
            throw new GeneralException(Code.VALIDATION_ERROR, "이미지를 선택해주세요.");
        }
        
        return DataResponseDto.of(postService.createPost(postDto));
    }

}

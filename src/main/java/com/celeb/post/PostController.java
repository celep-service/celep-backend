package com.celeb.post;

import com.celeb._base.constant.GenderEnum;
import com.celeb._base.dto.DataResponseDto;
import com.celeb._base.dto.EntityIdResponseDto;
import com.celeb.post.dto.EditPostRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/posts")
@Tag(name = "게시글 관련 API", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;

    @Operation(summary = "포스트 정보 조회", description = "포스트 정보를 가져옵니다.")
    @Parameters({
        @Parameter(name = "celebCategory", description = "셀럽 카테고리"),
        @Parameter(name = "search", description = "검색어"),
        @Parameter(name = "userId", description = "유저 아이디"),
        @Parameter(name = "gender", description = "성별"),
        @Parameter(name = "pageable", description = "페이징"),
        @Parameter(name = "page", description = "페이지 번호"),
        @Parameter(name = "size", description = "페이지 크기")
    })
    @GetMapping("")
    public DataResponseDto<Slice<PostDto>> getPosts(
        // @Nullable @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @Nullable String celebCategory,
        @Nullable String search,
        @Nullable Integer userId,
        @Nullable GenderEnum gender) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return DataResponseDto.of(
            postService.getPosts(pageable, celebCategory, search, userId, gender));
    }

    @Operation(summary = "포스트 정보 생성", description = "포스트를 생성합니다.")
    @PostMapping("")
    public DataResponseDto<EntityIdResponseDto> createPost(@Valid @RequestBody PostDto postDto) {
        return DataResponseDto.of(postService.createPost(postDto));
    }

    @Operation(summary = "포스트 삭제", description = "포스트를 삭제합니다.")
    @PatchMapping("/{postId}/delete")
    public DataResponseDto<EntityIdResponseDto> deletePost(@PathVariable int postId) {
        return DataResponseDto.of(postService.deletePost(postId));
    }

    // editPost는 clothes와 cody를 수정하는 것이므로 clothes와 cody의 수정 API를 만들어야 함.
    @Operation(summary = "포스트 수정", description = "포스트를 수정합니다. 필요한 인자만 보내면 해당 항목들만 수정됩니다.")
    @PatchMapping("")
    public DataResponseDto<EntityIdResponseDto> editPost(
        @Valid @RequestBody EditPostRequestDto editPostRequestDto) {
        return DataResponseDto.of(postService.editPost(editPostRequestDto));
    }


}

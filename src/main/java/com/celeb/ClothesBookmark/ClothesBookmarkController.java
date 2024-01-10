package com.celeb.ClothesBookmark;

import com.celeb._base.dto.BookmarkResponseDto;
import com.celeb._base.dto.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookmarks/clothes")
@RequiredArgsConstructor
@Tag(name = "옷 북마크 관련 API", description = "옷 북마크 관련 API")
public class ClothesBookmarkController {

    private final ClothesBookmarkService clothesBookmarkService;

    @Operation(summary = "북마크 업데이트", description = "특정 옷의 북마크 여부에 따라 생성 또는 삭제를 수행합니다.")
    @PostMapping("/{clothes_id}")
    public DataResponseDto<BookmarkResponseDto> updateBookmark(@PathVariable int clothes_id,
        Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        return DataResponseDto.of(clothesBookmarkService.updateBookmark(email, clothes_id));
    }

    @Operation(summary = "북마크 목록 조회", description = "사용자의 북마크 목록을 조회합니다")
    @GetMapping("")
    public DataResponseDto<Object> getClothesBookmark(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return DataResponseDto.of(clothesBookmarkService.getClothesBookmark(pageable, email));
    }
}

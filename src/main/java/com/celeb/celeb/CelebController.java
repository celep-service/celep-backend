package com.celeb.celeb;

import com.celeb._base.dto.DataResponseDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/celebs")
@Tag(name = "셀럽 관련 API", description = "셀럽 관련 API")
public class CelebController {

    private final CelebService celebService;

    @Operation(summary = "셀럽 정보 조회", description = "셀럽 정보 리스트를 가져옵니다.")
    @Parameters({
        @Parameter(name = "celebCategory", description = "셀럽 카테고리"),
        @Parameter(name = "pageable", description = "페이징"),
        @Parameter(name = "page", description = "페이지 번호"),
        @Parameter(name = "size", description = "페이지 크기"),
        @Parameter(name = "search", description = "검색어"),
    })
    @GetMapping("")
    public DataResponseDto<Slice<Celeb>> getCelebs(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @Nullable String celebCategory,
        @Nullable String search
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return DataResponseDto.of(
            celebService.getCelebs(pageable, celebCategory, search));
    }

    @Operation(summary = "셀럽 정보 생성", description = "셀럽 정보를 생성합니다.")
    @PostMapping("")
    public DataResponseDto<CelebDto> createCeleb(@Valid CelebDto celebDto) {
        return DataResponseDto.of(celebService.createCeleb(celebDto));
    }


}

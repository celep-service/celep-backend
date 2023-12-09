package com.celeb.clothes;

import com.celeb._base.dto.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clothes")
@Tag(name = "옷 관련 API", description = "옷 관련 API")
public class ClothesController {

    private final ClothesService clothesService;


    @Operation(summary = "옷 정보 생성", description = "옷 정보를 만듭니다.")
    @PostMapping("")
    public DataResponseDto<Object> createClothes(@Valid @RequestBody ClothesDto clothesDto) {
        return DataResponseDto.of(clothesService.createClothes(clothesDto));
    }


    @Operation(summary = "옷 정보 조회", description = "옷 정보를 가져옵니다.")
    @GetMapping("")
    public DataResponseDto<Object> getClothesList(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @Nullable ClothesCategoryEnum clothesCategory) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return DataResponseDto.of(clothesService.getClothesList(pageable, clothesCategory));
    }

}

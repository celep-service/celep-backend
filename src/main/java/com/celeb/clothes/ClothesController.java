package com.celeb.clothes;

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
@RequestMapping("/clothes")
public class ClothesController {

    private final ClothesService clothesService;


    @PostMapping("")
    public DataResponseDto<Object> createClothes(@RequestBody ClothesDto clothesDto) {
        return DataResponseDto.of(clothesService.createClothes(clothesDto));
    }


    @GetMapping("")
    public DataResponseDto<Object> getClothesList(
        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
        String celebCategory) {
        return DataResponseDto.of(clothesService.getClothesList(pageable, celebCategory));
    }


}

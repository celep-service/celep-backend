package com.celeb.clothes;

import com.celeb._base.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
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
    public DataResponseDto<Object> createPost(@RequestBody ClothesDto clothesDto) {
        return DataResponseDto.of(clothesService.createClothes(clothesDto));
    }


}

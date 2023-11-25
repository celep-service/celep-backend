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
        // validation
//        if (clothesDto.getClothesCategory() == null || clothesDto.getClothesCategory().isEmpty()) {
//            throw new GeneralException(Code.VALIDATION_ERROR, "카테고리를 선택해주세요.");
//        } else if (clothesDto.getName() == null || clothesDto.getName().isEmpty()) {
//            throw new GeneralException(Code.VALIDATION_ERROR, "옷 이름을 입력해주세요.");
//        } else if (clothesDto.getImageUrl() == null || clothesDto.getImageUrl()
//            .isEmpty()) {
//            throw new GeneralException(Code.VALIDATION_ERROR, "이미지를 입력해주세요.");
//        } else if (clothesDto.getBrand() == null || clothesDto.getBrand().isEmpty()) {
//            throw new GeneralException(Code.VALIDATION_ERROR, "브랜드를 입력해주세요.");
//        } else if (clothesDto.getGender() == null || clothesDto.getGender()
//            .isEmpty()) {
//            throw new GeneralException(Code.VALIDATION_ERROR, "성별을 입력해주세요.");
//        } else if (EnumUtils.isValidEnum(GenderEnum.class, clothesDto.getGender())) {
//            throw new GeneralException(Code.VALIDATION_ERROR, "올바른 성별을 입력해주세요.");
//        } else if (EnumUtils.isValidEnum(ClothesCategoryEnum.class,
//            clothesDto.getClothesCategory())) {
//            throw new GeneralException(Code.VALIDATION_ERROR, "올바른 의류 카테고리를 입력해주세요.");
//        }

        return DataResponseDto.of(clothesService.createClothes(clothesDto));
    }


    @GetMapping("")
    public DataResponseDto<Object> getClothesList(
        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
        String clothesCategory) {
        return DataResponseDto.of(clothesService.getClothesList(pageable, clothesCategory));
    }


}

package com.celeb.clothes;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClothesService {

    private final ClothesRepository clothesRepository;

    public Clothes createClothes(ClothesDto clothesDto) {
        System.out.println("working");
        Clothes clothes;
        try {
            clothes = clothesDto.toEntity();
        } catch (Exception e) {
            throw new GeneralException(Code.ERROR_UPLOAD_CLOTHES);
        }

        return clothesRepository.save(clothes);
    }

    public Slice<Clothes> getClothesList(Pageable pageable, ClothesCategoryEnum clothesCategory) {
        if (clothesCategory != null) {
            return clothesRepository.findAllByClothesCategory(
                ClothesCategoryEnum.valueOf(clothesCategory.getClothesCategory()), pageable);
        }
        return clothesRepository.findAll(pageable);
    }
}

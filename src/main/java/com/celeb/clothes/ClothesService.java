package com.celeb.clothes;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClothesService {

    private final ClothesRepository clothesRepository;

    public Object createClothes(ClothesDto clothesDto) {
        System.out.println("working");
        Clothes clothes;
        try {
            clothes = clothesDto.toEntity();
        } catch (Exception e) {
            throw new GeneralException(Code.ERROR_UPLOAD_CLOTHES);
        }

        return clothesRepository.save(clothes);
    }

    public List<Clothes> getClothesList() {
        return clothesRepository.findAll();
    }
}

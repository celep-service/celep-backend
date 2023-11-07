package com.celeb.celeb;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CelebService {

    private final CelebRepository celebRepository;

    public CelebDto createCeleb(CelebDto celebDto) {
        try {
            Celeb celeb = Celeb.builder()
                .name(celebDto.getName())
                .imageUrl(celebDto.getImageUrl())
                .celebCategory(
                    CelebCategoryEnum.valueOf(celebDto.getCelebCategory()))
                .build();
            Celeb save = celebRepository.save(celeb);
            return CelebDto.celebResponse(save);
        } catch (Exception e) {
            throw new GeneralException(Code.VALIDATION_ERROR);
        }

    }

}

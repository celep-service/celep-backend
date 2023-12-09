package com.celeb.celeb;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
import com.celeb.celeb.dto.EditCelebRequestDto;
import com.celeb.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
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

    public Slice<Celeb> getCelebs(Pageable pageable, String celebCategory, String search) {
        Specification<Post> spec = Specification.where(null);
        if (search != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), "%" + search + "%")
                )
            );
        }

        if (celebCategory != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                    criteriaBuilder.equal(root.get("celebCategory"),
                        CelebCategoryEnum.valueOf(celebCategory))
                )
            );
        }

        return celebRepository.findAll(spec, pageable);


    }

    public CelebDto editCeleb(EditCelebRequestDto celebDto) {
        Celeb celeb = celebRepository.findById(celebDto.getId())
            .orElseThrow(() -> new GeneralException(Code.NOT_FOUND_CELEB));

        // [TODO] admin이 아닌 경우 수정 불가 기능 추가하기

        // celebDto에 값이 없으면 기존 값을 유지한다.
        if (celebDto.getName() == null) {
            celeb.setName(celeb.getName());
        }
        if (celebDto.getImageUrl() == null) {
            celeb.setImageUrl(celeb.getImageUrl());
        }
        if (celebDto.getCelebCategory() == null) {
            celeb.setCelebCategory(celeb.getCelebCategory());
        }

        Celeb save = celebRepository.save(celeb);
        return CelebDto.celebResponse(save);

    }
}

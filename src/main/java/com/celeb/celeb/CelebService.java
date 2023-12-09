package com.celeb.celeb;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
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

        Slice<Celeb> celebsResponse = celebRepository.findAll(spec, pageable);

        return celebsResponse;


    }
}

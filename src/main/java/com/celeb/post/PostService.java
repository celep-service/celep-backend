package com.celeb.post;

import com.celeb._base.constant.Code;
import com.celeb._base.constant.GenderEnum;
import com.celeb._base.constant.StatusEnum;
import com.celeb._base.exception.GeneralException;
import com.celeb.celeb.CelebCategoryEnum;
import com.celeb.celeb.CelebRepository;
import com.celeb.clothes.Clothes;
import com.celeb.clothes.ClothesRepository;
import com.celeb.cody.Cody;
import com.celeb.cody.CodyRepository;
import com.celeb.cody.CodyService;
import com.celeb.user.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final CodyRepository codyRepository;


    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClothesRepository clothesRepository;
    private final CodyService codyService;
    private final CelebRepository celebRepository;

    public Slice<PostDto> getPosts(Pageable pageable, String celebCategory, String search,
        Integer userId, String gender) {
        Specification<Post> spec = Specification.where(null);

        if (gender != null) {
            GenderEnum genderEnum = GenderEnum.valueOf(gender.toUpperCase());
            spec = spec.and(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gender"),
                    genderEnum)
            );
        }
        if (userId != null) {
            spec = spec.and(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"),
                    userId));
        }
        if (search != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                    criteriaBuilder.like(root.get("title"), "%" + search + "%"),
                    criteriaBuilder.like(root.get("celeb").get("name"), "%" + search + "%")
                )
            );
        }
        if (celebCategory != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("celeb").get("celebCategory"), CelebCategoryEnum.valueOf(celebCategory)));
        }

        // 추가: status 값이 "ACTIVE"인 경우만 필터링
        spec = spec.and((root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("status"), StatusEnum.ACTIVE.getStatus())
        );

        Slice<Post> postsResponse = postRepository.findAll(spec, pageable);

        return PostDto.postListResponse(postsResponse);
    }

    @Transactional
    public PostDto createPost(PostDto postDto) {

        // jwt기능이 구현된다면 config단에서 user정보를 가져올 수 있을 것
        // 그러나 지금은 그렇지 않으므로 user정보를 가져오는 과정이 필요함
        postDto.setUser(
            userRepository.findById(postDto.getUserId()).orElseThrow(() ->
                new GeneralException(Code.NOT_FOUND_USER)));

        postDto.setCeleb(
            celebRepository.findById(postDto.getCelebId()).orElseThrow(() ->
                new GeneralException(Code.NOT_FOUND_CELEB)));

        Post post = postDto.toEntity();

        // 우선 post를 저장해야 id가 생기므로
        // post를 저장하고 id를 가져와서 cody에 저장해야함
        Post savedPost = postRepository.save(post);

        // clothesIdList -> cody에 등록하고 엔티티 리턴 필요

        // 옷 찾기
        List<Clothes> clothesList = clothesRepository.findAllById(postDto.getClothesIdList());
        if (clothesList.size() != postDto.getClothesIdList().size()) {
            throw new GeneralException(Code.NOT_FOUND_CLOTHES);
        }

        List<Cody> codyList = codyService.saveCody(savedPost, clothesList);
        savedPost.setCodies(codyList);

        PostDto returnPostDto = new PostDto();
        returnPostDto.setId(savedPost.getId());

        return returnPostDto;
    }
}

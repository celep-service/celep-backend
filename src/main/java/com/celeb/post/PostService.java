package com.celeb.post;

import com.celeb._base.constant.Code;
import com.celeb._base.constant.GenderEnum;
import com.celeb._base.constant.StatusEnum;
import com.celeb._base.dto.EntityIdResponseDto;
import com.celeb._base.exception.GeneralException;
import com.celeb.celeb.CelebCategoryEnum;
import com.celeb.celeb.CelebRepository;
import com.celeb.clothes.Clothes;
import com.celeb.clothes.ClothesRepository;
import com.celeb.cody.Cody;
import com.celeb.cody.CodyRepository;
import com.celeb.cody.CodyService;
import com.celeb.post.dto.EditPostRequestDto;
import com.celeb.postBookmark.PostBookmarkRepository;
import com.celeb.security.userDetails.CustomUserDetails;
import com.celeb.user.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final PostBookmarkRepository postBookmarkRepository;

    public Slice<PostDto> getPosts(Pageable pageable, String celebCategory, String search,
        Integer userId, GenderEnum gender) {

        Specification<Post> spec = Specification.where(null);

        if (gender != null) {

            spec = spec.and(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gender"),
                    gender)
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

        Slice<Post> posts = postRepository.findAll(spec, pageable);

        // 조회된 post의 id를 이용해서 bookmark 테이블에서 count를 가져와야함
        // post의 id를 이용해서 bookmark 테이블에서 count를 가져오는 쿼리 필요

        Slice<PostDto> postsResponse = PostDto.postListResponse(posts);

        postsResponse.forEach(postDto -> {
            int count = postBookmarkRepository.countByPostId(postDto.getId());
            postDto.setBookmarkCount(count);
        });

        return postsResponse;
    }

    @Transactional
    public EntityIdResponseDto createPost(PostDto postDto) {

        // 옷 찾기, 먼저 찾아야 post index가 낭비되지 않음.
        List<Clothes> clothesList = clothesRepository.findAllById(postDto.getClothesIdList());
        if (clothesList.size() != postDto.getClothesIdList().size()) {
            throw new GeneralException(Code.NOT_FOUND_CLOTHES);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer currentUserId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();

        // 현재 로그인한 사용자의 id를 가져와서 postDto에 저장
        postDto.setUser(
            userRepository.findById(currentUserId).orElseThrow(() ->
                new GeneralException(Code.NOT_FOUND_USER)));

        postDto.setCeleb(
            celebRepository.findById(postDto.getCelebId()).orElseThrow(() ->
                new GeneralException(Code.NOT_FOUND_CELEB)));

        Post post = postDto.toEntity();

        // 우선 post를 저장해야 id가 생기므로
        // post를 저장하고 id를 가져와서 cody에 저장해야함
        Post savedPost = postRepository.save(post);

        // clothesIdList -> cody에 등록하고 엔티티 리턴 필요

        List<Cody> codyList = codyService.saveCody(savedPost, clothesList);
        savedPost.setCodies(codyList);

        //PostDto returnPostDto = new PostDto();
        //returnPostDto.setId(savedPost.getId());

        return new EntityIdResponseDto(savedPost.getId());
    }

    @Transactional
    public EntityIdResponseDto deletePost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
            new GeneralException(Code.NOT_FOUND_POST));

        // 확인: post의 status가 ACTIVE인 경우에만 삭제 가능하도록
        if (!post.getStatus().equals(StatusEnum.ACTIVE.getStatus())) {
            throw new GeneralException(Code.NOT_FOUND_POST);
        }

        // 인가: post의 user와 현재 로그인한 user가 같은 경우에만 삭제 가능하도록
        // 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 아이디 가져오기
        Integer currentUserId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();

        if (!currentUserId.equals(post.getUser().getId())) {
            throw new GeneralException(Code.NOT_AUTHORIZED_USER);
        }

        post.setStatus(StatusEnum.DELETED.getStatus());
        System.out.println("post.getStatus() = " + post.getStatus());
        return new EntityIdResponseDto(post.getId());

    }

    // @Transactional
    public EntityIdResponseDto editPost(EditPostRequestDto editPostRequestDto) {
        // 조회: 포스트 확인
        Post post = postRepository.findById(editPostRequestDto.getPostId()).orElseThrow(() ->
            new GeneralException(Code.NOT_FOUND_POST));

        // 인가: post의 user와 현재 로그인한 user가 같은 경우에만 수정 가능하도록
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 아이디 가져오기
        Integer currentUserId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();

        if (!currentUserId.equals(post.getUser().getId())) {
            throw new GeneralException(Code.NOT_AUTHORIZED_USER);
        }

        // 수정: post 수정
        if (editPostRequestDto.getContent() != null) {
            post.setTitle(editPostRequestDto.getContent());
        }
        if (editPostRequestDto.getImageUrl() != null) {
            post.setImageUrl(editPostRequestDto.getImageUrl());
        }
        if (editPostRequestDto.getClothesIdList() != null) {
            List<Clothes> clothesList = clothesRepository.findAllById(
                editPostRequestDto.getClothesIdList());
            if (clothesList.size() != editPostRequestDto.getClothesIdList().size()) {
                throw new GeneralException(Code.NOT_FOUND_CLOTHES);
            }

            List<Cody> codyList = codyService.saveCody(post, clothesList);
            post.setCodies(codyList);
        }
        if (editPostRequestDto.getGender() != null) {
            post.setGender(GenderEnum.valueOf(editPostRequestDto.getGender()));
        }

        // 변경사항 한번에 저장
        postRepository.save(post);
        return new EntityIdResponseDto(post.getId());
    }
}

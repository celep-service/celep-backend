package com.celeb.post;

import com.celeb._base.constant.Code;
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

    public Slice<PostDto> getPosts(Pageable pageable,
        String celebCategory, String search, Integer userId) {

        // userId는 타 검색 조건과 함께 사용할 수 없음
        if (userId != null) {
            return PostDto.postListResponse(
                postRepository.findAllByUser_Id(userId, pageable));
        }

        if (search != null && celebCategory != null) {
            return PostDto.postListResponse(
                postRepository.findAllByContentContainingAndCeleb_CelebCategory(
                    search, CelebCategoryEnum.valueOf(celebCategory), pageable));
        }

        if (celebCategory != null) {
            return PostDto.postListResponse(
                postRepository.findAllByCeleb_CelebCategory(
                    CelebCategoryEnum.valueOf(celebCategory), pageable));
        }

        if (search != null) {
            return PostDto.postListResponse(
                postRepository.findAllByContentContaining(search, pageable));
        }

        Slice<Post> postList = postRepository.findAll(pageable);
        return PostDto.postListResponse(postList);
    }

    @Transactional
    public PostDto createPost(PostDto postDto) {

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
        postDto.getClothesIdList().forEach(
            clothes -> clothesRepository.findById(clothes).orElseThrow(() ->
                new GeneralException(Code.NOT_FOUND_CLOTHES))
        );

        List<Cody> codyList = codyService.saveCody(savedPost, clothesList);

        savedPost.setCodies(codyList);

        PostDto returnPostDto = new PostDto();
        returnPostDto.setId(savedPost.getId());

        return returnPostDto;
    }
}

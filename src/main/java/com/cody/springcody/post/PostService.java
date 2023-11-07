package com.cody.springcody.post;

import com.cody.springcody._base.constant.Code;
import com.cody.springcody._base.exception.GeneralException;
import com.cody.springcody.celeb.CelebRepository;
import com.cody.springcody.clothes.Clothes;
import com.cody.springcody.clothes.ClothesRepository;
import com.cody.springcody.cody.Cody;
import com.cody.springcody.cody.CodyRepository;
import com.cody.springcody.cody.CodyService;
import com.cody.springcody.user.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    public List<PostDto> getPosts() {
        List<Post> postList = postRepository.findAll();
        return PostDto.postListResponse(postList);
    }

    @Transactional
    public String createPost(PostDto postDto) {

        postDto.setUser(
            userRepository.findById(postDto.getUserId()).orElseThrow(() ->
                new GeneralException(Code.NOT_FOUND_USER)));

        postDto.setCeleb(
            celebRepository.findById(postDto.getInfluencerId()).orElseThrow(() ->
                new GeneralException(Code.NOT_FOUND_INFLUENCER)));

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

        postDto.setCodies(codyList);

        postRepository.save(postDto.toEntity());

        return "포스트 생성 완료";
    }
}

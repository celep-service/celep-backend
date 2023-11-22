package com.celeb.post;

import com.celeb._base.constant.GenderEnum;
import com.celeb.celeb.Celeb;
import com.celeb.cody.Cody;
import com.celeb.cody.CodyDto;
import com.celeb.user.User;
import com.celeb.user.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Slice;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {

    private Integer id;
    private String title;
    private String status;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Cody> codies;
    private User user;
    private Celeb celeb;

    // post시 clothesId를 받아서 저장하기 위한 변수
    private List<Integer> clothesIdList;

    // post시 userId를 받아서 저장하기 위한 변수
    private Integer userId;

    // post시 influencerId를 받아서 저장하기 위한 변수
    private Integer celebId;

    // get시 codyDto를 반환하는 변수
    private List<CodyDto> codiesDtoList;

    // get시 commentCount를 반환하는 변수
    private Integer commentCount;

    // get시 userDto를 반환하는 변수
    private UserDto userDto;

    private String gender;

    public PostDto() {

    }

    public static Slice<PostDto> postListResponse(
        Slice<Post> posts) {
//        return posts.stream()
//            .map(PostDto::postResponse)
//            .toList();
        return posts.map(PostDto::postResponse);
    }

    public static PostDto postResponse(Post post) {
        List<CodyDto> codiesDtoList = CodyDto.codyListResponse(post.getCodies());
//        Influencer influencer = ;

        return PostDto.builder()
            .id(post.getId())
            .title(post.getTitle())
            .gender(post.getGender().toString())
            .status(post.getStatus())
            .imageUrl(post.getImageUrl())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .codiesDtoList(codiesDtoList)
            .userDto(UserDto.userSignUpResponse(post.getUser()))
            .celeb(post.getCeleb())
            // commentCount 조회
            .commentCount(post.getComment().size())
            .build();
    }


    public Post toEntity() {
        return Post.builder()
            .title(title)
            .status(status)
            .imageUrl(imageUrl)
            .celeb(celeb)
            .user(user)
            .gender(GenderEnum.valueOf(gender))
            .build();
    }
}

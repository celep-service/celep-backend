package com.celeb.post;

import com.celeb._base.constant.GenderEnum;
import com.celeb._base.constant.StatusEnum;
import com.celeb.celeb.Celeb;
import com.celeb.cody.Cody;
import com.celeb.cody.CodyDto;
import com.celeb.user.User;
import com.celeb.user.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Hidden
    private Integer id;
    @NotEmpty(message = "내용을 입력해주세요.")
    @Schema(description = "제목", example = "제목입니다.")
    private String title;
    @Hidden
    private String status;
    @NotNull(message = "이미지를 입력해주세요.")
    @Schema(description = "이미지", example = "naver.com")
    private String imageUrl;
    @Hidden
    private LocalDateTime createdAt;
    @Hidden
    private LocalDateTime updatedAt;
    @Hidden
    private List<Cody> codies;
    @Hidden
    private User user;
    @Hidden
    private Celeb celeb;

    private Integer bookmarkCount;

    // post시 clothesId를 받아서 저장하기 위한 변수
    @NotEmpty(message = "옷 정보를 입력해주세요.")
    @Schema(description = "옷 정보 리스트", example = "[1,2,3]")
    private List<Integer> clothesIdList;

    // post시 userId를 받아서 저장하기 위한 변수
    // @Schema(description = "유저 아이디", example = "1")
    // private Integer userId;

    // post시 influencerId를 받아서 저장하기 위한 변수
    @NotNull(message = "셀럽을 입력해주세요.")
    @Schema(description = "셀럽 아이디", example = "1")
    private Integer celebId;

    // get시 codyDto를 반환하는 변수
    @Hidden
    private List<CodyDto> codiesDtoList;

    // get시 commentCount를 반환하는 변수
    @Hidden
    private Integer commentCount;

    // get시 userDto를 반환하는 변수
    @Hidden
    private UserDto userDto;

    @NotEmpty(message = "성별을 입력해주세요.")
    @Pattern(regexp = "MALE|FEMALE", message = "성별은 MALE, FEMALE만 가능합니다.")
    @Schema(description = "성별", example = "MALE")
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
            .status(post.getStatus())
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
            .status(StatusEnum.ACTIVE.getStatus())
            .gender(GenderEnum.valueOf(gender))
            .build();
    }
}

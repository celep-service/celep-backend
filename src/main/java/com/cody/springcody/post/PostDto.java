package com.cody.springcody.post;

import com.cody.springcody.cody.Cody;
import com.cody.springcody.cody.CodyDto;
import com.cody.springcody.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {

    private Integer id;
    private String title;
    private String content;
    private String status;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Cody> codies;
    private User user;

    // post시 clothesId를 받아서 저장하기 위한 변수
    private List<Integer> clothesIdList;

    // post시 userId를 받아서 저장하기 위한 변수
    private Integer userId;

    // get시 codyDto를 반환하는 변수
    private List<CodyDto> codiesDtoList;

    public static List<PostDto> postListResponse(
        List<Post> posts) {
        return posts.stream()
            .map(PostDto::postResponse)
            .toList();
    }

    public static PostDto postResponse(Post post) {
        List<CodyDto> codiesDtoList = CodyDto.codyListResponse(post.getCodies());

        return PostDto.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .status(post.getStatus())
            .imageUrl(post.getImageUrl())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .codiesDtoList(codiesDtoList)
            .build();
    }


    public Post toEntity() {
        return Post.builder()
            .title(title)
            .content(content)
            .status(status)
            .imageUrl(imageUrl)
            .build();
    }

    public Post toEntity(String title, String content, String status, String imageUrl,
        List<Cody> codies) {
        return Post.builder()
            .title(title)
            .content(content)
            .status(status)
            .imageUrl(imageUrl)
            .codies(codies)
            .build();
    }
}

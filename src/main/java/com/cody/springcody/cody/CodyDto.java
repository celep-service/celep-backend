package com.cody.springcody.cody;

import com.cody.springcody.clothes.Clothes;
import com.cody.springcody.post.Post;
import com.cody.springcody.post.PostDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodyDto {

    private Integer id;
    private Post post;
    private Clothes clothes;

    private Integer postId;
    private Integer clothesId;

    private PostDto postDto;


    public Cody toEntity() {
        return Cody.builder()
            .post(this.post)
            .clothes(this.clothes)
            .build();
    }

    public static CodyDto codyResponse(Cody cody) {
        return CodyDto.builder()
            .id(cody.getId())
            .clothes(cody.getClothes())
            .build();
    }

    public static List<CodyDto> codyListResponse(List<Cody> codyList) {
        return codyList.stream()
            .map(CodyDto::codyResponse)
            .toList();
    }


}
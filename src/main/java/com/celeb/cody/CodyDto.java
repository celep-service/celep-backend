package com.celeb.cody;

import com.celeb.clothes.Clothes;
import com.celeb.clothes.ClothesDto;
import com.celeb.post.Post;
import com.celeb.post.PostDto;
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
    private ClothesDto clothesDto;

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
        ClothesDto clothesDto = ClothesDto.builder()
            .id(cody.getClothes().getId())
            .name(cody.getClothes().getName())
            .clothesCategory(cody.getClothes().getClothesCategory().toString())
            .gender(cody.getClothes().getGender())
            .imageUrl(cody.getClothes().getImageUrl())
            .sellUrl(cody.getClothes().getSellUrl())
            .brand(cody.getClothes().getBrand())
            .build();

        return CodyDto.builder()
            .id(cody.getId())
            .clothesDto(clothesDto)
            .build();
    }

    public static List<CodyDto> codyListResponse(List<Cody> codyList) {
        return codyList.stream()
            .map(CodyDto::codyResponse)
            .toList();
    }


}

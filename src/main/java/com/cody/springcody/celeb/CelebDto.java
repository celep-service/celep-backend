package com.cody.springcody.celeb;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CelebDto {

    private Integer id;
    private String name;
    private String imageUrl;
    private String celebCategory;

    public CelebDto(Celeb celeb) {
        this.id = celeb.getId();
        this.name = celeb.getName();
        this.imageUrl = celeb.getImageUrl();
        this.celebCategory = celeb.getCelebCategory().getCategory();
    }

    public static CelebDto celebResponse(Celeb celeb) {
        return CelebDto.builder()
            .id(celeb.getId())
            .name(celeb.getName())
            .imageUrl(celeb.getImageUrl())
            .celebCategory(celeb.getCelebCategory().getCategory())
            .build();
    }

}

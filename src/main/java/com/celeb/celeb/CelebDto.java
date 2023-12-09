package com.celeb.celeb;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CelebDto {

    @Hidden
    private Integer id;
    private String name;
    private String imageUrl;
    @Pattern(regexp = "TALENT|MODEL|SINGER|ACTOR|INFLUENCER|ETC", message = "셀럽 카테고리는 TALENT|MODEL|SINGER|ACTOR|INFLUENCER|ETC만 가능합니다.")
    @Schema(description = "성별", example = "MALE")
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

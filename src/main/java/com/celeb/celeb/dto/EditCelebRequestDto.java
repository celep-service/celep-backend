package com.celeb.celeb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EditCelebRequestDto {

    @NotNull(message = "셀럽 아이디를 입력해주세요.")
    private Integer id;
    private String name;
    private String imageUrl;
    @Pattern(regexp = "TALENT|MODEL|SINGER|ACTOR|INFLUENCER|ETC", message = "셀럽 카테고리는 TALENT|MODEL|SINGER|ACTOR|INFLUENCER|ETC만 가능합니다.")
    @Schema(description = "셀럽 카테고리", example = "TALENT")
    private String celebCategory;

}

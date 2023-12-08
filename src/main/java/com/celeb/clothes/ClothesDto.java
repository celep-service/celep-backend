package com.celeb.clothes;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // 이게 있어야 simple type 오류가 안난다.
// ObjectMapper가 @RequestBody를 바인딩할 때 기본 생성자를 사용하기 때문이다.
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClothesDto {

    @NotEmpty(message = "옷 이름을 입력해주세요.")
    @Schema(description = "옷 이름", example = "티셔츠")
    private String name;
    @NotEmpty(message = "옷 카테고리를 입력해주세요.")
    @Schema(description = "옷 카테고리", example = "TOP")
    private String clothesCategory;
    @Schema(description = "옷 브랜드", example = "나이키")
    private String brand;
    @NotEmpty(message = "옷에 어울리는 성별을 입력해주세요.")
    @Schema(description = "옷 성별", example = "남성")
    private String gender;
    @NotEmpty(message = "옷 이미지를 입력해주세요.")
    @Schema(description = "옷 이미지", example = "naver.com")
    private String imageUrl;
    @Schema(description = "옷 판매 링크", example = "naver.com")
    private String sellUrl;


    public Clothes toEntity() {
        return Clothes.builder()
            .name(name)
            .clothesCategory(ClothesCategoryEnum.valueOf(clothesCategory))
            .brand(brand)
            .gender(gender)
            .imageUrl(imageUrl)
            .sellUrl(sellUrl)
            .build();
    }


}

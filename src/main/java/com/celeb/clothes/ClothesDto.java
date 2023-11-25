package com.celeb.clothes;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String name;
    @NotEmpty(message = "옷 카테고리를 입력해주세요.")
    private String clothesCategory;
    private String brand;
    @NotEmpty(message = "옷에 어울리는 성별을 입력해주세요.")
    private String gender;
    @NotEmpty(message = "옷 이미지를 입력해주세요.")
    private String imageUrl;
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

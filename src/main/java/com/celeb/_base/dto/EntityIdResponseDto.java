package com.celeb._base.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EntityIdResponseDto {

    @Schema(description = "아이디", example = "1")
    private final Integer id;

}

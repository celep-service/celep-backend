package com.celeb._base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BookmarkResponseDto {

    @Schema(description = "북마크 유무", example = "true")
    private final Boolean isBookmarked;
}

package com.celeb;

import com.celeb.celeb.CelebDto;
import com.celeb.celeb.CelebService;
import com.celeb.clothes.ClothesDto;
import com.celeb.clothes.ClothesService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class CelepApplicationTests {

    @Autowired
    private CelebService celebService;

    @Autowired
    private ClothesService clothesService;

    @Test
    void contextLoads() {
    }

    @Test
    void 셀럽생성() {
        CelebDto celebDto1 = CelebDto.builder()
            .name("이승기")
            .imageUrl(
                "https://i.namu.wiki/i/R0AhIJhNi8fkU2Al72pglkrT8QenAaCJd1as-d_iY6MC8nub1iI5VzIqzJlLa-1uzZm--TkB-KHFiT-P-t7bEg.webp")
            .celebCategory("ACTOR")
            .build();
        CelebDto celebDto2 = CelebDto.builder()
            .name("아이유")
            .imageUrl(
                "https://i.namu.wiki/i/R0AhIJhNi8fkU2Al72pglkrT8QenAaCJd1as-d_iY6MC8nub1iI5VzIqzJlLa-1uzZm--TkB-KHFiT-P-t7bEg.webp")
            .celebCategory("SINGER")
            .build();
        CelebDto celebDto3 = CelebDto.builder()
            .name("박보검")
            .imageUrl(
                "https://i.namu.wiki/i/R0AhIJhNi8fkU2Al72pglkrT8QenAaCJd1as-d_iY6MC8nub1iI5VzIqzJlLa-1uzZm--TkB-KHFiT-P-t7bEg.webp")
            .celebCategory("ACTOR")
            .build();
        CelebDto celebDto4 = CelebDto.builder()
            .name("박서준")
            .imageUrl(
                "https://i.namu.wiki/i/R0AhIJhNi8fkU2Al72pglkrT8QenAaCJd1as-d_iY6MC8nub1iI5VzIqzJlLa-1uzZm--TkB-KHFiT-P-t7bEg.webp")
            .celebCategory("ACTOR")
            .build();
        CelebDto celebDto5 = CelebDto.builder()
            .name("박보영")
            .imageUrl(
                "https://i.namu.wiki/i/R0AhIJhNi8fkU2Al72pglkrT8QenAaCJd1as-d_iY6MC8nub1iI5VzIqzJlLa-1uzZm--TkB-KHFiT-P-t7bEg.webp")
            .celebCategory("ACTOR")
            .build();
        CelebDto celebDto6 = CelebDto.builder()
            .name("박민영")
            .imageUrl(
                "https://i.namu.wiki/i/R0AhIJhNi8fkU2Al72pglkrT8QenAaCJd1as-d_iY6MC8nub1iI5VzIqzJlLa-1uzZm--TkB-KHFiT-P-t7bEg.webp")
            .celebCategory("ACTOR")
            .build();

        celebService.createCeleb(celebDto1);
        celebService.createCeleb(celebDto2);
        celebService.createCeleb(celebDto3);
        celebService.createCeleb(celebDto4);
        celebService.createCeleb(celebDto5);
        celebService.createCeleb(celebDto6);


    }

    @Test
    void 옷생성() {
        ClothesDto build1 = ClothesDto.builder()
            .name("티셔츠asdf")
            .gender("MALE")
            .imageUrl(
                "https://i.pinimg.com/originals/6e/6e/6e/6e6e6e2b6b4b0b0b0b0b0b0b0b0b0b0b.jpg")
            .clothesCategory("TOP")
            .build();

        ClothesDto build2 = ClothesDto.builder()
            .name("티셔츠22")
            .gender("MALE")
            .imageUrl(
                "https://i.pinimg.com/originals/6e/6e/6e/6e6e6e2b6b4b0b0b0b0b0b0b0b0b0b0b.jpg")
            .clothesCategory("TOP")
            .build();

        ClothesDto build3 = ClothesDto.builder()
            .name("티셔츠")
            .gender("MALE333")
            .imageUrl(
                "https://i.pinimg.com/originals/6e/6e/6e/6e6e6e2b6b4b0b0b0b0b0b0b0b0b0b0b.jpg")
            .clothesCategory("BOTTOM")
            .build();

        ClothesDto build4 = ClothesDto.builder()
            .name("티셔츠44")
            .gender("MALE")
            .imageUrl(
                "https://i.pinimg.com/originals/6e/6e/6e/6e6e6e2b6b4b0b0b0b0b0b0b0b0b0b0b.jpg")
            .clothesCategory("TOP")
            .build();

        clothesService.createClothes(build1);
        clothesService.createClothes(build2);
        clothesService.createClothes(build3);
        clothesService.createClothes(build4);

    }


}

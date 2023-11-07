package com.celeb;

import com.celeb.celeb.CelebDto;
import com.celeb.celeb.CelebService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CelepApplicationTests {

    @Autowired
    private CelebService celebService;

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

}

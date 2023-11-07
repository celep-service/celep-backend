package com.celeb.cody;

import com.celeb.clothes.ClothesRepository;
import com.celeb.post.Post;
import com.celeb.clothes.Clothes;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodyService {


    private final CodyRepository codyRepository;
    private final ClothesRepository clothesRepository;

    public List<Cody> saveCody(Post post, List<Clothes> clothesList) {
        List<Cody> codyList = new ArrayList<>();

        clothesList.forEach(clothes -> {
                CodyDto codyDto = CodyDto.builder()
                    .post(post)
                    .clothes(clothes)
                    .build();
                Cody save = codyRepository.save(codyDto.toEntity());

                //codyList.add(CodyDto.codyResponse(save));
                codyList.add(save);
            }
        );

        return codyList;
    }
}

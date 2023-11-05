package com.cody.springcody.cody;

import com.cody.springcody.clothes.Clothes;
import com.cody.springcody.clothes.ClothesRepository;
import com.cody.springcody.post.Post;
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

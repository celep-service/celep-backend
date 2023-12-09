package com.celeb.ClothesBookmark;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
import com.celeb.clothes.Clothes;
import com.celeb.clothes.ClothesRepository;
import com.celeb.user.User;
import com.celeb.user.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ClothesBookmarkService {
    private final UserRepository userRepository;
    private final ClothesRepository clothesRepository;
    private final ClothesBookmarkRepository clothesBookmarkRepository;

    public String updateBookmark(String email, int clothes_id){
        Optional<User> user = userRepository.findByEmail(email);
        Optional<Clothes> clothes = clothesRepository.findById(clothes_id);
        if(user.isEmpty()){
            throw new GeneralException(Code.EMPTY_USER);
        }
        if(clothes.isEmpty()){
            throw new GeneralException(Code.NOT_FOUND_CLOTHES);
        }
        Optional<ClothesBookmark> clothesBookmark =
            clothesBookmarkRepository.findByUserAndClothes(user.get(), clothes.get());

        if(clothesBookmark.isPresent()){
            clothesBookmarkRepository.delete(clothesBookmark.get());
            return "북마크 해제";
        } else{
            clothesBookmarkRepository.save(new ClothesBookmark(user.get(), clothes.get()));
            return "북마크 추가";
        }
    }

    public Slice<Clothes> getClothesBookmark(Pageable pageable, String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new GeneralException(Code.EMPTY_USER);
        }

        return clothesBookmarkRepository.findClothesByMember(user.get(), pageable);
    }

    public int getClothesBookmarkCount(Clothes clothes) {
        Long count = clothesBookmarkRepository.countByClothes(clothes);
        return count.intValue();
    }

    public boolean isClothesBookmarked(Clothes clothes, String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new GeneralException(Code.EMPTY_USER);
        }

        Optional<ClothesBookmark> clothesBookmark
            = clothesBookmarkRepository.findByUserAndClothes(user.get(), clothes);
        return clothesBookmark.isPresent();
    }
}

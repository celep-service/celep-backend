package com.celeb.ClothesBookmark;

import com.celeb._base.constant.Code;
import com.celeb._base.dto.BookmarkResponseDto;
import com.celeb._base.exception.GeneralException;
import com.celeb.clothes.Clothes;
import com.celeb.clothes.ClothesDto;
import com.celeb.clothes.ClothesRepository;
import com.celeb.security.userDetails.CustomUserDetails;
import com.celeb.user.User;
import com.celeb.user.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ClothesBookmarkService {

    private final UserRepository userRepository;
    private final ClothesRepository clothesRepository;
    private final ClothesBookmarkRepository clothesBookmarkRepository;

    public BookmarkResponseDto updateBookmark(String email, int clothes_id) {
        Optional<User> user = userRepository.findByEmail(email);
        Optional<Clothes> clothes = clothesRepository.findById(clothes_id);
        if (user.isEmpty()) {
            throw new GeneralException(Code.EMPTY_USER);
        }
        if (clothes.isEmpty()) {
            throw new GeneralException(Code.NOT_FOUND_CLOTHES);
        }
        Optional<ClothesBookmark> clothesBookmark =
            clothesBookmarkRepository.findByUserAndClothes(user.get(), clothes.get());

        if (clothesBookmark.isPresent()) {
            clothesBookmarkRepository.delete(clothesBookmark.get());
            return new BookmarkResponseDto(false);
        } else {
            clothesBookmarkRepository.save(new ClothesBookmark(user.get(), clothes.get()));
            return new BookmarkResponseDto(true);
        }
    }

    public Slice<ClothesDto> getClothesBookmark(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer currentUserId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        Optional<User> user = userRepository.findById(currentUserId);
        if (user.isEmpty()) {
            throw new GeneralException(Code.EMPTY_USER);
        }

        Slice<Clothes> clothes = clothesBookmarkRepository.findClothesByMember(user.get(), pageable);
        Slice<ClothesDto> clothesListResponse = ClothesDto.clothesListResponse(clothes);

        clothesListResponse.forEach(clothesDto -> {
            Long count = clothesBookmarkRepository.countByClothesId(clothesDto.getId());
            clothesDto.setBookmarkCount(count);
            clothesDto.setIsBookmarked(true);  //clothesBookmarkRepository에서 select 한 것이므로
        });
        return clothesListResponse;
    }

    public int getClothesBookmarkCount(Clothes clothes) {
        Long count = clothesBookmarkRepository.countByClothes(clothes);
        return count.intValue();
    }

    public boolean isClothesBookmarked(Clothes clothes, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new GeneralException(Code.EMPTY_USER);
        }

        Optional<ClothesBookmark> clothesBookmark
            = clothesBookmarkRepository.findByUserAndClothes(user.get(), clothes);
        return clothesBookmark.isPresent();
    }
}

package com.celeb.ClothesBookmark;

import com.celeb.clothes.Clothes;
import com.celeb.user.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesBookmarkRepository extends JpaRepository<ClothesBookmark, Integer> {
    Optional<ClothesBookmark> findByUserAndClothes(User user, Clothes clothes);
    @Query("SELECT c FROM ClothesBookmark cb JOIN cb.clothes c WHERE cb.user = :user")
    Slice<Clothes> findClothesByMember(@Param("user") User user, Pageable pageable);

    Long countByClothes(Clothes clothes);

}

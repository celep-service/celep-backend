package com.celeb.clothes;

import jakarta.annotation.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Integer> {

    Slice<Clothes> findAllByClothesCategory(ClothesCategoryEnum clothesCategoryEnum,
        Pageable pageable);

    Slice<Clothes> findAll(@Nullable Specification<Clothes> spec, Pageable pageable);

}

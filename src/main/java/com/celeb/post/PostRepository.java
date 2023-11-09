package com.celeb.post;

import com.celeb.celeb.CelebCategoryEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Slice<Post> findAllByCeleb_CelebCategory(CelebCategoryEnum celebCategory, Pageable pageable);

    @Query("select p from Post p where p.content like %:search% or p.celeb.name like %:search% "
        + "or p.title like %:search%")
    Slice<Post> findAllByContentContaining(String search, Pageable pageable);

    @Query(
        "select p from Post p where (p.content like %:search% or p.celeb.name like %:search% or p.title like %:search%) "
            + "and p.celeb.celebCategory = :celebCategory")
    Slice<Post> findAllByContentContainingAndCeleb_CelebCategory(String search,
        CelebCategoryEnum celebCategory, Pageable pageable);

    Slice<Post> findAllByUser_Id(Integer userId, Pageable pageable);
}

package com.celeb.postBookmark;

import com.celeb.post.Post;
import com.celeb.user.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Integer> {
    Optional<PostBookmark> findByUserAndPost(User user, Post post);

    @Query("SELECT p FROM PostBookmark pb JOIN pb.post p WHERE pb.user = :user")
    Slice<Post> findPostByMember(@Param("user") User user, Pageable pageable);

    Integer countByPostId(Integer postId);
}

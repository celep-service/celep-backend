package com.celeb.celeb;

import com.celeb.post.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebRepository extends JpaRepository<Celeb, Integer> {
    
    Slice<Celeb> findAll(Specification<Post> spec, Pageable pageable);
}

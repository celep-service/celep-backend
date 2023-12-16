package com.celeb.postBookmark;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
import com.celeb.post.Post;
import com.celeb.post.PostDto;
import com.celeb.post.PostRepository;
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
public class PostBookmarkService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostBookmarkRepository postBookmarkRepository;

    public String updateBookmark(String email, int post_id){
        Optional<User> user = userRepository.findByEmail(email);
        Optional<Post> post = postRepository.findById(post_id);
        if(user.isEmpty()){
            throw new GeneralException(Code.EMPTY_USER);
        }
        if(post.isEmpty()){
            throw new GeneralException(Code.NOT_FOUND_POST);
        }
        Optional<PostBookmark> postBookmark =
            postBookmarkRepository.findByUserAndPost(user.get(), post.get());

        if(postBookmark.isPresent()){
            postBookmarkRepository.delete(postBookmark.get());
            return "북마크 해제";
        } else{
            postBookmarkRepository.save(new PostBookmark(user.get(), post.get()));
            return "북마크 추가";
        }
    }

    public Slice<PostDto> getPostBookmark(Pageable pageable, String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new GeneralException(Code.EMPTY_USER);
        }
        Slice<Post> postsResponse = postBookmarkRepository.findPostByMember(user.get(), pageable);

        return PostDto.postListResponse(postsResponse);
    }

    //public int getPostBookmarkCount(Post post) {
    //    Integer count = postBookmarkRepository.countByPostId(post);
    //    return count.intValue();
    //}

    public boolean isPostBookmarked(Post post, String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new GeneralException(Code.EMPTY_USER);
        }

        Optional<PostBookmark> postBookmark
            = postBookmarkRepository.findByUserAndPost(user.get(), post);
        return postBookmark.isPresent();
    }
}

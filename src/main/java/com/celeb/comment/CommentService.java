package com.celeb.comment;

import com.celeb._base.constant.Code;
import com.celeb._base.dto.EntityIdResponseDto;
import com.celeb._base.exception.GeneralException;
import com.celeb.post.Post;
import com.celeb.post.PostRepository;
import com.celeb.user.User;
import com.celeb.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public EntityIdResponseDto createComment(CommentDto commentDto) {
        // userId로 User를 찾아서 commentDto에 set
        // postId로 Post를 찾아서 commentDto에 set
        Integer userId = commentDto.getUserId();
        Integer postId = commentDto.getPostId();

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GeneralException(Code.NOT_FOUND_USER));
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new GeneralException(Code.NOT_FOUND_POST));

        commentDto.setUser(user);
        commentDto.setPost(post);

        // commentDto를 Comment Entity로 변환
        Comment comment = commentDto.toEntity();
        commentRepository.save(comment);

        //CommentDto returnCommentDto = CommentDto.builder()
        //    .id(comment.getId()).build();
        return new EntityIdResponseDto(comment.getId());
    }

    public List<CommentDto> getComments(Integer postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new GeneralException(Code.NOT_FOUND_POST));

        List<Comment> postComments = post.getComment();
        // commentDto로 변환해서 리턴

        List<Comment> activeCommentList = postComments.stream()
            .filter(comment -> comment.getStatus().equals("ACTIVE")).toList();

        return CommentDto.commentListResponse(activeCommentList);
    }
}

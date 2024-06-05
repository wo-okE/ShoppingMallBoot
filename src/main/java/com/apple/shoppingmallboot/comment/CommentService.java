package com.apple.shoppingmallboot.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Page<Comment> pageNationComment(int page, int cnt, Long id){
        return commentRepository.findPageByParentId(PageRequest.of(page,cnt), id);
    }
    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }
}

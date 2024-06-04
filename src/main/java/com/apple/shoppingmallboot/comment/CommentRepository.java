package com.apple.shoppingmallboot.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByParentId(Long id);

    Page<Comment> findPageByParentId(Pageable page);
}

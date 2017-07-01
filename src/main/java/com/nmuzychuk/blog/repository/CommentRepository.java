package com.nmuzychuk.blog.repository;

import com.nmuzychuk.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findById(Long commentId);

    Collection<Comment> findByPostId(Long postId);

}

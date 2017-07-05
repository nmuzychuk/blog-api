package com.nmuzychuk.blog.repository;

import com.nmuzychuk.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Repository for <code>Post</code> domain objects.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findById(Long postId);

    Collection<Post> findByUserId(Long userId);

}

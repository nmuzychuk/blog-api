package com.nmuzychuk.blog.controller;

import com.nmuzychuk.blog.exceptions.NotFoundException;
import com.nmuzychuk.blog.model.Comment;
import com.nmuzychuk.blog.model.Post;
import com.nmuzychuk.blog.model.User;
import com.nmuzychuk.blog.repository.CommentRepository;
import com.nmuzychuk.blog.repository.PostRepository;
import com.nmuzychuk.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

/**
 * Controller used to manage comments.
 */
@RestController
public class CommentController {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Autowired
    CommentController(UserRepository userRepository, PostRepository postRepository,
                      CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public Collection<Comment> readComments() {
        return commentRepository.findAll();
    }

    @RequestMapping(value = "/posts/{postId}/comments", method = RequestMethod.GET)
    public Collection<Comment> readPostComments(@PathVariable Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @RequestMapping(value = "/posts/{postId}/comments", method = RequestMethod.POST)
    public ResponseEntity<?> createPostComment(@PathVariable Long postId, @RequestBody Comment comment) {
        Post post = postRepository.findById(postId);
        User user = userRepository.findById(post.getUser().getId());
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedComment.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/comments/{commentId}")
    public Comment readComment(@PathVariable Long commentId) {
        Comment comment = commentRepository.findById(commentId);

        if (comment != null)
            return comment;
        else
            throw new NotFoundException(Comment.class.toString(), commentId);
    }

}

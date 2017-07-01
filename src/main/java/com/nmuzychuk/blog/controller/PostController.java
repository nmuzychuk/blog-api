package com.nmuzychuk.blog.controller;

import com.nmuzychuk.blog.exceptions.NotFoundException;
import com.nmuzychuk.blog.model.Post;
import com.nmuzychuk.blog.model.User;
import com.nmuzychuk.blog.repository.PostRepository;
import com.nmuzychuk.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
public class PostController {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Autowired
    PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public Collection<Post> readPosts() {
        return postRepository.findAll();
    }

    @RequestMapping(value = "/users/{userId}/posts", method = RequestMethod.GET)
    public Collection<Post> readUserPosts(@PathVariable Long userId) {
        return postRepository.findByUserId(userId);
    }

    @RequestMapping(value = "/users/{userId}/posts", method = RequestMethod.POST)
    public ResponseEntity<?> createUserPost(@PathVariable Long userId, @RequestBody Post post) {
        User user = userRepository.findById(userId);
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET)
    public Post readPost(@PathVariable Long postId) {
        Post post = postRepository.findById(postId);

        if (post != null) {
            return post;
        } else {
            throw new NotFoundException(Post.class.toString(), postId);
        }
    }

}

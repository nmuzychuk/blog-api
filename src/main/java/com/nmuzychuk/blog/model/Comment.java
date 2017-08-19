package com.nmuzychuk.blog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Simple object representing a comment.
 */
@Entity
public class Comment {

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(max = 400)
    private String body;

    protected Comment() {
    }

    public Comment(User user, Post post, String body) {
        this.user = user;
        this.post = post;
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}

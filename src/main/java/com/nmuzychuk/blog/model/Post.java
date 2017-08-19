package com.nmuzychuk.blog.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple object representing a post.
 */
@Entity
public class Post {

    @ManyToOne
    private User user;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(max = 128)
    private String title;

    @NotNull
    @Lob
    @Size(max = 2000)
    private String body;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments = new HashSet<>();

    protected Post() {
    }

    public Post(User user, String title, String body) {
        this.user = user;
        this.title = title;
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}

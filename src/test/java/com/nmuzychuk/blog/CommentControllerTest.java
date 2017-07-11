package com.nmuzychuk.blog;

import com.nmuzychuk.blog.model.Comment;
import com.nmuzychuk.blog.model.Post;
import com.nmuzychuk.blog.model.User;
import com.nmuzychuk.blog.repository.CommentRepository;
import com.nmuzychuk.blog.repository.PostRepository;
import com.nmuzychuk.blog.repository.UserRepository;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@WebAppConfiguration
public class CommentControllerTest {

    private MockMvc mockMvc;
    private Post mockPost;
    private Comment mockComment;
    private JSONObject jsonObject;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        commentRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        User mockUser = userRepository.save(new User("mockUser", "password"));
        mockPost = postRepository.save(new Post(mockUser, "title", "body"));
        mockComment = commentRepository.save(new Comment(mockUser, mockPost, "body"));
        jsonObject = new JSONObject();
    }

    @Test
    public void testReadComments() throws Exception {
        mockMvc.perform(get("/comments")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadPostComments() throws Exception {
        mockMvc.perform(get(String.format("/posts/%d/comments", mockPost.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreatePostComment() throws Exception {
        jsonObject.put("body", "body");

        mockMvc.perform(post(String.format("/posts/%d/comments", mockPost.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonObject.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void testReadComment() throws Exception {
        mockMvc.perform(get(String.format("/comments/%d", mockComment.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadCommentNotFound() throws Exception {
        mockMvc.perform(get("/comments/100")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}

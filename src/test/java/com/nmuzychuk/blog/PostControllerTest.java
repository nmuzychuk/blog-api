package com.nmuzychuk.blog;

import com.nmuzychuk.blog.model.Post;
import com.nmuzychuk.blog.model.User;
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
public class PostControllerTest {

    private MockMvc mockMvc;
    private User mockUser;
    private Post mockPost;
    private JSONObject jsonObject;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        postRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        mockUser = userRepository.save(new User("mockUser", "password"));
        mockPost = postRepository.save(new Post(mockUser, "title", "body"));
        jsonObject = new JSONObject();
    }

    @Test
    public void testReadPosts() throws Exception {
        mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadUserPosts() throws Exception {
        mockMvc.perform(get(String.format("/users/%d/posts", mockUser.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUserPost() throws Exception {
        jsonObject.put("title", "title");
        jsonObject.put("body", "body");

        mockMvc.perform(post(String.format("/users/%d/posts", mockUser.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonObject.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void testReadPost() throws Exception {
        mockMvc.perform(get(String.format("/posts/%d", mockPost.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadPostNotFound() throws Exception {
        mockMvc.perform(get("/posts/100")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

}

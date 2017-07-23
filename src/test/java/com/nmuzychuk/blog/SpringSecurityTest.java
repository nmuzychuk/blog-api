package com.nmuzychuk.blog;

import com.nmuzychuk.blog.model.User;
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

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@WebAppConfiguration
public class SpringSecurityTest {

    private MockMvc mockMvc;
    private User mockUser;
    private JSONObject jsonObject;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain).build();
        userRepository.deleteAllInBatch();

        mockUser = userRepository.save(new User("mockUser", "password"));
        jsonObject = new JSONObject();
    }

    @Test
    public void testRoot() throws Exception {
        mockMvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegister() throws Exception {
        jsonObject.put("username", "username");
        jsonObject.put("password", "password");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonObject.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void testLoginInvalidUsername() throws Exception {
        jsonObject.put("username", "invalid");
        jsonObject.put("password", mockUser.getPassword());

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonObject.toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginInvalidPassword() throws Exception {
        jsonObject.put("username", mockUser.getUsername());
        jsonObject.put("password", "invalid");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonObject.toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLogin() throws Exception {
        jsonObject.put("username", mockUser.getUsername());
        jsonObject.put("password", mockUser.getPassword());

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadUsersNoToken() throws Exception {
        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testReadPostNoToken() throws Exception {
        mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadCommentsNoToken() throws Exception {
        mockMvc.perform(get("/comments")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }

}

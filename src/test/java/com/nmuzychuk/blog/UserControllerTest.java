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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;
    private User mockUser;
    private JSONObject jsonObject;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAllInBatch();

        mockUser = userRepository.save(new User("mockUser", "password"));
        jsonObject = new JSONObject();
    }

    @Test
    public void testReadUsers() throws Exception {
        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUser() throws Exception {
        jsonObject.put("username", "username");
        jsonObject.put("password", "password");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonObject.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void testReadUser() throws Exception {
        mockMvc.perform(get(String.format("/users/%d", mockUser.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect((status().isOk()));
    }

    @Test
    public void testReadUserNotFound() throws Exception {
        mockMvc.perform(get("/users/100")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

}

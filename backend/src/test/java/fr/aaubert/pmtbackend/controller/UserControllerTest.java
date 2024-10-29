package fr.aaubert.pmtbackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"john\", \"password\": \"password\", \"email\": \"myEmail\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());

    }

    @Test
    void testRegisterWithMissingUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testRegisterWithMissingPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"john\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @Test
    void registerFailsWithInvalidUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"\", \"password\": \"\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testGetNonExistingUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .param("username", "i_am_not_here"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    @Test
    void testGetExistingUser() throws Exception {
        // Register a user in the mock
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"johndoe\", \"password\": \"password\", \"email\": \"myEmail2\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //then search this user
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .param("userName", "johndoe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("johndoe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("password"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("myEmail2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notifications").value(false))
                .andDo(print());
    }

    @Test
    void testGetWrongUsernameUser() throws Exception {
        // Register a user in the mock
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"johndoe2\", \"password\": \"password2\", \"email\": \"myEmail3\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());

        //then search this user
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .param("username", "johndoe"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    @Test
    void testGetUsers() throws Exception {
        // Register a user in the mock
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"johndoeAll\", \"password\": \"password\", \"email\": \"myEmailAll\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].notifications").value(false))
                .andDo(print());
    }

}

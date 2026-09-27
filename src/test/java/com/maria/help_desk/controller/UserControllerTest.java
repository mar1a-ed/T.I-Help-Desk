package com.maria.help_desk.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createUserWithSuccessStatus200() throws Exception {
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lolita@gmail.com",
                            "password": "12345678"
                        }
                        """)
        ).andExpect(status().isOk());
    }

    @Test
    public void createUserWithExistingEmailStatus409() throws Exception {
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "mariaeduarda@gmail.com",
                            "password": "12345678"
                        }
                        """)
        ).andExpect(status().isConflict());
    }

    @Test
    public void findUserByIdWithUserAuthenticatedStatus200() throws Exception {
        MvcResult login =
                mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "lilian@gmail.com",
                                    "password": "12345678"                       
                                }
                                """))
                        .andExpect(status().isOk())
                        .andReturn();

        String bearerToken = login.getResponse().getContentAsString();

        mockMvc.perform(get("/users/3")
                        .header("Authorization", "Bearer " + bearerToken)
                )
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void findUserByIdWithUserNotAuthenticatedStatus401(){
        mockMvc.perform(get("/users/3")).andExpect(status().isUnauthorized());
    }
}

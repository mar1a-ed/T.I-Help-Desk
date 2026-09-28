package com.maria.help_desk.controller;

import com.maria.help_desk.dto.user.UserResponseDTO;
import com.maria.help_desk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void openTicketWithValidUserStatus200() throws Exception {
        MvcResult login = mockMvc
                .perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "lilian@gmail.com",
                                    "password": "12345678"
                                }
                                """)
        ).andExpect(status().isOk()).andReturn();

        String bearerToken = login.getResponse().getContentAsString();

        mockMvc.perform(post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "userEmail": "lilian@gmail.com",
                                "title": "Abracadabra",
                                "description": "abracadabra abra abra bu bu da da gu gu",
                                "category": "SOFTWARE"
                            }
                        """)
                .header("Authorization", "Bearer " + bearerToken)
                ).andExpect(status().isOk());
    }

    @Test
    public void openTicketWithInvalidJsonStatus400() throws Exception {
        MvcResult login = mockMvc
                .perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "lilian@gmail.com",
                                    "password": "12345678"
                                }
                                """)
                ).andExpect(status().isOk()).andReturn();

        String bearerToken = login.getResponse().getContentAsString();

        mockMvc.perform(post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                             "userEmail": "lilian@gmail.com",
                             "title": "Abracadabra",
                             "description": "abracadabra abra abra bu bu da da gu gu",
                             "category": "SOFTWARE"
                        """)
                .header("Authorization", "Bearer " + bearerToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void findTicketByValidIdStatus200() throws Exception {
        MvcResult login = mockMvc
                .perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "venina@gmail.com",
                                    "password": "12345678"
                                }
                                """)
                ).andExpect(status().isOk()).andReturn();

        String bearerToken = login.getResponse().getContentAsString();

        mockMvc.perform(get("/tickets/2")
                .header("Authorization", "Bearer " + bearerToken)
                ).andExpect(status().isOk());
    }

    @Test
    public void findTicketByInvalidIdStatus401() throws Exception {
        MvcResult login = mockMvc
                .perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "venina@gmail.com",
                                    "password": "12345678"
                                }
                                """)
                ).andExpect(status().isOk()).andReturn();

        String bearerToken = login.getResponse().getContentAsString();

        mockMvc.perform(get("/tickets/77")
                .header("Authorization", "Bearer " + bearerToken)
        ).andExpect(status().isNotFound());
    }
}

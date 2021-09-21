package com.example.demo;

import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.util.RandomTokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testClientRestController() throws Exception {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setLogin("TestLogin");
        registerRequestDto.setToken(RandomTokenGenerator.generateNewToken(32));
        registerRequestDto.setBalance(999L);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(registerRequestDto);
        mockMvc.perform(
                post("/register").contentType(MediaType.APPLICATION_JSON).content(json)
        ).andExpect(status().isCreated());

        mockMvc.perform(
                put("/register").contentType(MediaType.APPLICATION_JSON).content(json)
        ).andExpect(status().is(444));

        mockMvc.perform(get("/client/{login}/balance", registerRequestDto.getLogin())
                .header("X-Client-Token", "_" + registerRequestDto.getToken()))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/client/{login}/balance", "Unknown")
                .header("X-Client-Token", registerRequestDto.getToken()))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/client/{login}/balance", registerRequestDto.getLogin())
                .header("X-Client-Token", registerRequestDto.getToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance").value(999L));
    }

    @Test
    public void testDefaultBalance() throws Exception {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setLogin("TestLogin2");
        registerRequestDto.setToken(RandomTokenGenerator.generateNewToken(32));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(registerRequestDto);
        mockMvc.perform(
                post("/register").contentType(MediaType.APPLICATION_JSON).content(json)
        ).andExpect(status().isCreated());

        mockMvc.perform(get("/client/{login}/balance", registerRequestDto.getLogin())
                .header("X-Client-Token", registerRequestDto.getToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance").value(0L));
    }

    @Test
    public void testValidation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setLogin("#invalidlogin");
        registerRequestDto.setToken(RandomTokenGenerator.generateNewToken(32));
        registerRequestDto.setBalance(1L);
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequestDto)))
                .andExpect(status().isBadRequest());

        registerRequestDto.setLogin("TestLogin3");
        registerRequestDto.setToken("123");
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequestDto)))
                .andExpect(status().isBadRequest());

        registerRequestDto.setToken(RandomTokenGenerator.generateNewToken(32));
        registerRequestDto.setBalance(-1L);
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequestDto)))
                .andExpect(status().isBadRequest());
    }
}

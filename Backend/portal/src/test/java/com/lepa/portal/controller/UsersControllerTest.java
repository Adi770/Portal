package com.lepa.portal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lepa.portal.dto.UsersDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("ADMIN")
@Slf4j
@Transactional
class UsersControllerTest {

    final static String controllerAddress = "/api/v1/user";


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    //region GET
    @Test
    void add() throws Exception {
        mockMvc.perform(get(controllerAddress + "/ADD"))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

    @Test
    void findOneUser() throws Exception {
        int id = 1;
        mockMvc.perform(get(controllerAddress + "/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void currentRole() throws Exception {
        mockMvc.perform(get(controllerAddress + "/Role"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllRoles() throws Exception {
        mockMvc.perform(get(controllerAddress + "/AllRoles"))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get(controllerAddress + "/AllUsers"))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

    //endregion

    //region Put
    @Test
    void updateUser() throws Exception {
        int id = 2;

        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUsername("Testowy User");
        usersDTO.setPassword("password");
        usersDTO.setEmail("new@mail.com");


        mockMvc.perform(put(controllerAddress + "/" + id)
                .content(objectMapper.writeValueAsString(usersDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //endregion

    //region Post
    @Test
    void addUser() throws Exception {

        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUsername("Testowy User");
        usersDTO.setPassword("password");
        usersDTO.setEmail("new@mail.com");

        mockMvc.perform(post(controllerAddress + "/Register")
                .content(objectMapper.writeValueAsString(usersDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void setRole() throws Exception {
        int id = 2;
        int role = 1;

        mockMvc.perform(post(controllerAddress + "/SetRole/" + id)
                .content(String.valueOf(role)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void changeEmail() throws Exception {

        String mail = "newMail@gmail.com";

        mockMvc.perform(post(controllerAddress + "/ChangeEmail").content(mail))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

    @Test
    void changePassword() throws Exception {
        String password = "newSecretPassword";

        mockMvc.perform(post(controllerAddress + "/ChangePassword").content(password))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

    @Test
    void resetPasswordsMail() throws Exception {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setEmail("ADMIN@admin.pl");

        mockMvc.perform(post(controllerAddress + "/ResetPasswordsMail")
                .content(objectMapper.writeValueAsString(usersDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void resetPassword() throws Exception {

        String token = "ffd4e231-695b-47fd-985d-647150d76355";
        String password = "weqew";

        mockMvc.perform(post(controllerAddress + "/ResetPassword").param("token", token)
                .content(password))
                .andDo(print())
                .andExpect(status().isOk());
    }
    //endregion
}

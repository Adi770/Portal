package com.lepa.portal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lepa.portal.dto.CommentsDTO;
import com.lepa.portal.dto.NewsDTO;
import com.lepa.portal.dto.RateDTO;
import com.lepa.portal.model.portal.News;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("ADMIN")
@Slf4j
@Transactional
class PortalControllerTest {

    private final static String controllerAddress = "/api/v1/Portal";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    //region Get
    @Test
    void dataGenerator() throws Exception {
        mockMvc.perform(get(controllerAddress + "/DataGenerator"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void allUsers() throws Exception{
        mockMvc.perform(get(controllerAddress + "/Users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void allNews() throws Exception{
        int size=2;
        int page=1;
        mockMvc.perform(get(controllerAddress + "/News")
                .param("size",String.valueOf(size))
                .param("page",String.valueOf(page)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void oneNews()throws Exception {
        int id = 11;
        mockMvc.perform(get(controllerAddress + "/News/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void allCommentsUser() throws Exception{
        int id=1;

        mockMvc.perform(get(controllerAddress + "/Comment").param("id",String.valueOf(id)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void allComents() throws Exception{
        mockMvc.perform(get(controllerAddress + "/Comments"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void oneComments()throws Exception {
        int id = 11;
        mockMvc.perform(get(controllerAddress + "/Comment/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void lastFiveComments() throws Exception{
        mockMvc.perform(get(controllerAddress + "/Comment/last5"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getSomeNews() throws Exception{
        mockMvc.perform(get(controllerAddress + "/News/Last10"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void bestArticle() throws Exception{
        int size = 1;

        mockMvc.perform(get(controllerAddress + "/BestArticle/" + size))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void numberOfItems() throws Exception{
        mockMvc.perform(get(controllerAddress + "/News/Size"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //endregion

    //region Put
    @Test
    void updateNews()throws Exception {
        int id = 11;
        NewsDTO newsDTO=new NewsDTO();
        MockMultipartFile file=new MockMultipartFile("images","your image".getBytes());

        mockMvc.perform(put(controllerAddress + "/News/" + id)
                .content(objectMapper.writeValueAsString(newsDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateComments() throws Exception{
        int id = 11;
        CommentsDTO commentsDTO=new CommentsDTO();

        mockMvc.perform(put(controllerAddress + "/Comment/" + id)
        .content(objectMapper.writeValueAsString(commentsDTO))
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //endregion

    //region Post
    @Test
    void addNews() throws Exception {
        MockMultipartFile file=new MockMultipartFile("images","your image".getBytes());

        News news=new News();



        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.multipart(controllerAddress + "/News")
                .file(file)
                .param("news",objectMapper.writeValueAsString(news)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void rateArticle() throws Exception{
        int id = 11;
        RateDTO rateDTO=new RateDTO();
        mockMvc.perform(post(controllerAddress + "/RateArticle/News/" + id)
                .content(objectMapper.writeValueAsString(rateDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void addComment() throws Exception{
        int id = 12;
        CommentsDTO commentsDTO=new CommentsDTO();

        mockMvc.perform(post(controllerAddress + "/News/" + id + "/Comment")
        .content(objectMapper.writeValueAsString(commentsDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
//endregion

    //region Delete

    @Test
    void deleteNews() throws Exception{
        int id = 11;
        mockMvc.perform(delete(controllerAddress + "/News/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteComment()throws Exception {
        int id = 11;
        mockMvc.perform(delete(controllerAddress + "/Comment/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }
    //endregion

}

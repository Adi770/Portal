package com.lepa.portal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lepa.portal.dto.forum.ForumSectionDTO;
import com.lepa.portal.dto.forum.NameDTO;
import com.lepa.portal.dto.forum.TopicDTO;
import com.lepa.portal.model.forum.ForumSection;
import com.lepa.portal.model.forum.ForumSubSection;
import com.lepa.portal.model.forum.ForumTopic;
import com.lepa.portal.model.forum.ForumTopicComm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("ADMIN")
@Slf4j
@Transactional
class ForumControllerTest {

    private static final String controllerAddress = "/api/v1/Forum";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    //region GET
    @Test
    void forumMainPage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Generate"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        List<ForumSectionDTO> forumSectionDTO;
        forumSectionDTO = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ForumSectionDTO[].class));
        assertThat(forumSectionDTO).isNotNull();

    }

    @Test
    void sectionList() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Section"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        List<ForumSection> forumSectionList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ForumSection[].class));
        assertThat(forumSectionList).isNotNull();
    }

    @Test
    void subSectionList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/SubSection"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        List<ForumSubSection> forumSubSectionList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ForumSubSection[].class));
        assertThat(forumSubSectionList).isNotNull();
    }

    @Test
    void topicsList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Topics"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        List<ForumTopic> forumTopicList = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ForumTopic[].class));
        assertThat(forumTopicList).isNotNull();
    }

    @Test
    void topicCommList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Comments"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        List<ForumTopicComm> forumTopicComms = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ForumTopicComm[].class));
        assertThat(forumTopicComms).isNotNull();
    }

    @Test
    @Transactional
    void testTopicCommList() throws Exception {
        int id = 13;
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Comments/" + id))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
        ForumTopicComm forumTopicComm = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ForumTopicComm.class);
        assertThat(forumTopicComm).isNotNull();
        assertThat(forumTopicComm.getId()).isEqualTo(Long.valueOf(id));

    }

    @Test
    @Transactional
    void testSectionList() throws Exception {
        int id = 1;
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Section/" + id))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        ForumSection forumSection = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ForumSection.class);
        assertThat(forumSection).isNotNull();
    }

    @Test
    @Transactional
    void testSubSectionList() throws Exception {
        int id = 2;
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/SubSection/" + id))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        ForumSubSection forumSubSection = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ForumSubSection.class);
        assertThat(forumSubSection).isNotNull();
    }

    //
    @Test
    @Transactional
    void testTopicsList() throws Exception {
        int id = 13;
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Topics/" + id))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        ForumTopic forumTopic = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ForumTopic.class);
        assertThat(forumTopic).isNotNull();

    }

    @Test
    @Transactional
    void getLastTopic() throws Exception {
        int number = 2;
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/ForumLastTopic/" + number))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        List<ForumTopic> forumTopic = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ForumTopic[].class));
        assertThat(forumTopic).isNotNull();
    }
    //endregion

    //region POST
    @Test
    @Transactional
    void addSection() throws Exception {
        NameDTO forumSection = new NameDTO();
        forumSection.setName("testowy");

        mockMvc.perform(post(controllerAddress + "/Section")
                .content(objectMapper.writeValueAsString(forumSection))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void addForumSubSection() throws Exception {
        int id = 1;
        NameDTO subSection = new NameDTO();
        subSection.setName("testowy");

        mockMvc.perform(post(controllerAddress + "/Section/" + id + "/SubSection")
                .content(objectMapper.writeValueAsString(subSection))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void addForumTopic() throws Exception {
        int id = 1;
        NameDTO topic = new NameDTO();
        topic.setName("testowy");

        mockMvc.perform(post(controllerAddress + "/SubSection/" + id + "/Topic")
                .content(objectMapper.writeValueAsString(topic))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void addForumTopicComm() throws Exception {
        int id = 1;
        NameDTO topicComm = new NameDTO();
        topicComm.setName("testowy");

        mockMvc.perform(post(controllerAddress + "/Topic/" + id + "/TopicComm")
                .content(objectMapper.writeValueAsString(topicComm))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createNewTopic() throws Exception {
        int id = 1;
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setTextTopic("Testowy text");
        topicDTO.setTitleTopic("testowy Topic");
        topicDTO.setSubSectionId(1L);

                mockMvc.perform(post(controllerAddress + "/SubSection/Topic")
                .content(objectMapper.writeValueAsString(topicDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    //endregion

    //region Delete
    @Test
    void deleteSection() throws Exception {

        int id=1;
        mockMvc.perform(delete(controllerAddress+"/Section/"+id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteSubSection() throws Exception {
        int id=1;
        mockMvc.perform(delete(controllerAddress+"/SubSection/"+id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteTopics() throws Exception {
        int id=13;
        mockMvc.perform(delete(controllerAddress+"/Topic/"+id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteTopicsComm() throws Exception {
        int id=13;
        mockMvc.perform(delete(controllerAddress+"/Comments/"+id))
                .andDo(print())
                .andExpect(status().isOk());
    }
    //endregion

    //region PUT
    @Test
    void updateSection() throws Exception {
        int id=1;
        NameDTO section = new NameDTO();
        section.setName("testowy");

        mockMvc.perform(put(controllerAddress+"/Section/"+id)
                .content(objectMapper.writeValueAsString(section))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateSubSection() throws Exception {
        int id=1;

        NameDTO subSection = new NameDTO();
        subSection.setName("testowy");

        mockMvc.perform(put(controllerAddress+"/SubSection/"+id)
                .content(objectMapper.writeValueAsString(subSection))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateForumTopic() throws Exception {
        int id=13;
        NameDTO forumTopic = new NameDTO();
        forumTopic.setName("testowy");

        mockMvc.perform(put(controllerAddress+"/ForumTopic/"+id)
                .content(objectMapper.writeValueAsString(forumTopic))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateForumTopicComm() throws Exception {
        int id=13;
        NameDTO ForumTopicComm = new NameDTO();
        ForumTopicComm.setName("testowy");

        mockMvc.perform(put(controllerAddress + "/ForumTopicComm/"+id)
                .content(objectMapper.writeValueAsString(ForumTopicComm))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //endregion
}

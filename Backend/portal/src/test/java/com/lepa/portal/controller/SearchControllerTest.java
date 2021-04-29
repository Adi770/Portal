package com.lepa.portal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lepa.portal.dto.search_component.CpuDTO;
import com.lepa.portal.dto.search_component.GpuDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("ADMIN")
@Slf4j
@Transactional
class SearchControllerTest {


    private final static String controllerAddress = "/api/v1/Portal/Search";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    //region Get
    @Test
    void getCpu() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Cpu"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void getGpu() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Gpu"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void getOneGpu() throws Exception {
        int id = 1;
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Gpu/" + id + "/FindCpu"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void getOneCpu() throws Exception {
        int id = 1;
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Cpu/" + id + "/FindGpu"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void topGpu() throws Exception {
        int id = 3;
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Gpu/Top"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void topCpu() throws Exception {
        int id = 3;
        MvcResult mvcResult = mockMvc.perform(get(controllerAddress + "/Cpu/Top"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    //endregion

    //region Put
    @Test
    void editCpu() throws Exception {

        CpuDTO cpuDTO = new CpuDTO();

        int id = 3;

        MvcResult mvcResult = mockMvc.perform(put(controllerAddress + "/Cpu/" + id)
                .content(objectMapper.writeValueAsString(cpuDTO)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void editGpu() throws Exception {

        GpuDTO gpuDTO = new GpuDTO();

        int id = 3;

        MvcResult mvcResult = mockMvc.perform(put(controllerAddress + "/Gpu/" + id)
                .content(objectMapper.writeValueAsString(gpuDTO)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    //endregion

    //region Post
    @Test
    void addCpu() throws Exception {
        CpuDTO cpu=new CpuDTO();

        MvcResult mvcResult = mockMvc.perform(post(controllerAddress + "/Cpu")
                .content(objectMapper.writeValueAsString(cpu))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void addGpu() throws Exception {
        GpuDTO gpu = new GpuDTO();

        MvcResult mvcResult = mockMvc.perform(post(controllerAddress + "/Gpu")
                .content(objectMapper.writeValueAsString(gpu))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void cpuToGpu() throws Exception {

        int idCpu = 3, idGpu = 3;

        MvcResult mvcResult = mockMvc.perform(post(controllerAddress + "/Cpu/" + idCpu + "/Gpu/" + idGpu))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    //endregion

    //region Delete
    @Test
    void deleteCpu() throws Exception {
        int id = 3;
        MvcResult mvcResult = mockMvc.perform(delete(controllerAddress + "/Cpu/" + id))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void deleteGpu() throws Exception {

        int id = 3;

        MvcResult mvcResult = mockMvc.perform(delete(controllerAddress + "/Gpu/" + id))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

//endregion
}

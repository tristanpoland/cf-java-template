package com.example.app.controller;

import com.example.app.service.DataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataService dataService;

    @Test
    void testIndexPage() throws Exception {
        Map<String, Object> mockData = new HashMap<>();
        Map<String, Object> mockSystemInfo = new HashMap<>();
        
        when(dataService.generateHistogramData(anyInt())).thenReturn(mockData);
        when(dataService.getSystemInfo()).thenReturn(mockSystemInfo);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("appName", "bins", "data", "systemInfo"));
    }

    @Test
    void testUpdateData() throws Exception {
        Map<String, Object> mockData = new HashMap<>();
        Map<String, Object> mockSystemInfo = new HashMap<>();
        
        when(dataService.generateHistogramData(anyInt())).thenReturn(mockData);
        when(dataService.getSystemInfo()).thenReturn(mockSystemInfo);

        mockMvc.perform(post("/update")
                        .param("bins", "25")
                        .param("appName", "Test App"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("bins", 25))
                .andExpect(model().attribute("appName", "Test App"));
    }
}
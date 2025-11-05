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
        mockData.put("bins", 30);
        mockData.put("labels", java.util.Arrays.asList("1", "2", "3"));
        mockData.put("counts", java.util.Arrays.asList(10, 20, 15));
        
        Map<String, Object> mockSystemInfo = new HashMap<>();
        mockSystemInfo.put("Java Version", "17.0.0");
        
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
        mockData.put("bins", 25);
        mockData.put("labels", java.util.Arrays.asList("1", "2", "3"));
        mockData.put("counts", java.util.Arrays.asList(10, 20, 15));
        
        Map<String, Object> mockSystemInfo = new HashMap<>();
        mockSystemInfo.put("Java Version", "17.0.0");
        
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
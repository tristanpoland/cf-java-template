package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.app.service.DataService;

@Controller
public class MainController {

    private final DataService dataService;

    public MainController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("appName", "Java Test App");
        model.addAttribute("bins", 30);
        model.addAttribute("data", dataService.generateHistogramData(30));
        model.addAttribute("systemInfo", dataService.getSystemInfo());
        return "index";
    }

    @PostMapping("/update")
    public String updateData(@RequestParam("bins") int bins, 
                           @RequestParam("appName") String appName,
                           Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("bins", bins);
        model.addAttribute("data", dataService.generateHistogramData(bins));
        model.addAttribute("systemInfo", dataService.getSystemInfo());
        return "index";
    }
}
package com.example.app.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DataService {

    public Map<String, Object> generateHistogramData(int bins) {
        Random random = new Random();
        List<Double> data = new ArrayList<>();
        
        // Generate 500 random numbers (normal distribution approximation)
        for (int i = 0; i < 500; i++) {
            data.add(random.nextGaussian());
        }
        
        // Calculate histogram
        double min = data.stream().mapToDouble(Double::doubleValue).min().orElse(-3.0);
        double max = data.stream().mapToDouble(Double::doubleValue).max().orElse(3.0);
        double binWidth = (max - min) / bins;
        
        List<Integer> counts = new ArrayList<>(Collections.nCopies(bins, 0));
        List<String> labels = new ArrayList<>();
        
        for (int i = 0; i < bins; i++) {
            double binStart = min + i * binWidth;
            double binEnd = min + (i + 1) * binWidth;
            labels.add(String.format("%.2f", (binStart + binEnd) / 2));
        }
        
        for (double value : data) {
            int binIndex = Math.min((int) ((value - min) / binWidth), bins - 1);
            if (binIndex >= 0) {
                counts.set(binIndex, counts.get(binIndex) + 1);
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("labels", labels);
        result.put("counts", counts);
        result.put("bins", bins);
        
        return result;
    }
    
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("Java Version", System.getProperty("java.version"));
        info.put("Java Runtime", System.getProperty("java.runtime.name"));
        info.put("Operating System", System.getProperty("os.name"));
        info.put("Host Name", getHostName());
        info.put("Current Time", LocalDateTime.now().toString());
        info.put("Available Processors", Runtime.getRuntime().availableProcessors());
        info.put("Max Memory", Runtime.getRuntime().maxMemory() / (1024 * 1024) + " MB");
        
        return info;
    }
    
    private String getHostName() {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "Unknown";
        }
    }
}
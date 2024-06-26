package com.group3.kindergartenmanagementsystem.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dyj83asmu");
        config.put("api_key", "513122117192341");
        config.put("api_secret", "gK51cmay_PQ3AbsNytlRDlBSNIE");
        config.put("secure", "true");
        return new Cloudinary(config);
    }
}

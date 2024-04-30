package com.group3.kindergartenmanagementsystem.service.impl;

import com.cloudinary.Cloudinary;
import com.group3.kindergartenmanagementsystem.service.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    Cloudinary cloudinary;
    @Override
    public Map upload(MultipartFile file) {
        Map data;
        try{
            data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
        } catch (IOException e){
            throw new RuntimeException("Upload image failed !");
        }
        return data;
    }
}

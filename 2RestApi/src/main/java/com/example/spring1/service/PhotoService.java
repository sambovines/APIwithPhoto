package com.example.spring1.service;

import com.example.spring1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoService {
    @Autowired
    private final UserService userService;

    public PhotoService(UserService userService) {
        this.userService = userService;
    }


    public String savePhoto(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();

        String fileName = Optional.of(originalFilename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(0, originalFilename.lastIndexOf(".")))
                .orElseThrow(() -> {
                    return new Exception("errorFileName");
                });
        String extention = Optional.of(originalFilename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(originalFilename.lastIndexOf(".") + 1))
                .orElseThrow(() -> {
                    return new Exception("errorFileName");
                });

        String filePath = "tmp/"
                .concat(String.valueOf(System.currentTimeMillis()) + UUID.randomUUID().toString())
                .concat("_")
                .concat(fileName)
                .concat(".")
                .concat(extention);

        String newFileName = System.getProperty("user.dir")
                .concat("/")
                .concat(filePath);

        file.transferTo(new File(newFileName));
        return filePath;
    }
}

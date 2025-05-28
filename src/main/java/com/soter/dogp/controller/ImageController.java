package com.soter.dogp.controller;

import com.soter.dogp.service.ImageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    ImageService imageService;
    @GetMapping("/{filename:.+}")
    public ResponseEntity<UrlResource> serveDogFile(@PathVariable String filename) {
        return imageService.serveImgFile(filename, "");
    }

    @GetMapping("/background/{filename:.+}")
    public ResponseEntity<UrlResource> serveBackground(@PathVariable String filename) {
        return imageService.serveImgFile(filename, "background");
    }

    @GetMapping("/posts/{filename:.+}")
    public ResponseEntity<UrlResource> servePostsFile(@PathVariable String filename) {
        return imageService.serveImgFile(filename, "posts");
    }
}
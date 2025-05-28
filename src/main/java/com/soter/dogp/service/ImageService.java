package com.soter.dogp.service;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class ImageService {
    //depois tratar de criar uma interface com um método para cada tipo de caminho de imagem, todas chamando a msm implementação aqui.
    private final Path imagePath = Paths.get("/root/midia/galeria-do-dogp/");
    private final Path imagePostPath = Paths.get("posts");
    private final Path bgPath = Paths.get("background");

    public ResponseEntity<UrlResource> serveImgFile(@PathVariable String filename, String fileFolder) {
        try {
            Path file = imagePath.resolve(fileFolder);
            file = file.resolve(filename);
            UrlResource resource = new UrlResource(file.toUri());
            String contentType = Files.probeContentType(file);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package kr.co.thereal.artgallery.domain.artImage.controller;

import kr.co.thereal.artgallery.domain.artImage.Repository.ImageRepository;
import kr.co.thereal.artgallery.domain.artImage.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @GetMapping("/upload")
    public String imageUploadForm() {
        return "image/Upload";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        imageService.saveImage(image);
        return "image/Upload";
    }
}

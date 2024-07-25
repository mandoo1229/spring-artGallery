package kr.co.thereal.artgallery.domain.artImage.controller;

import kr.co.thereal.artgallery.domain.artImage.Repository.ImageRepository;
import kr.co.thereal.artgallery.domain.artImage.entity.ImageEntity;
import kr.co.thereal.artgallery.domain.artImage.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ImageApiController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @GetMapping("/upload")
    public String imageUploadForm() {
        return "image/Upload";
    }

    @PostMapping("/upload")
    public Long uploadImage(@RequestParam("image")MultipartFile image) throws IOException {
        return imageService.saveImage(image);
    }

    @GetMapping("/view")
    public List<ImageEntity> viewList() {
        return imageRepository.findAll();
    }

    @GetMapping("/view/{id}")
    public Optional<ImageEntity> viewById(@PathVariable Long id) {
        return imageRepository.findById(id);
    }

}

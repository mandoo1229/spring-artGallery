package kr.co.thereal.artgallery.domain.artImage.controller;

import kr.co.thereal.artgallery.domain.artImage.Repository.ImageRepository;
import kr.co.thereal.artgallery.domain.artImage.entity.ImageEntity;
import kr.co.thereal.artgallery.domain.artImage.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

//@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ImageController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @GetMapping("/upload")
    public String imageUploadForm() {
        return "image/Upload";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image")MultipartFile image, @RequestParam("images")List<MultipartFile> images) throws IOException {
        imageService.saveImage(image);

        for (MultipartFile multipartFile : images) {
            imageService.saveImage(multipartFile);
        }
        return "redirect:/view";
    }

    @GetMapping("/view")
    public List<ImageEntity> view() {
        System.out.println("이미지" + imageRepository.findAll());
        return imageRepository.findAll();
    }


    @GetMapping("/images/{imageId}")
    @ResponseBody
    public Resource downloadImage(@PathVariable("imageId") Long id, Model model) throws IOException {

        ImageEntity image = imageRepository.findById(id).orElse(null);
        System.out.println("이미지 " + image);
        return new UrlResource("file:" + image.getSavedPath());
    }

    @GetMapping("/attach/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException {
        ImageEntity image = imageRepository.findById(id).orElse(null);

        UrlResource resource = new UrlResource("file:" + image.getSavedPath());

        String encodedFileName = UriUtils.encode(image.getOrgNm(), StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }

}

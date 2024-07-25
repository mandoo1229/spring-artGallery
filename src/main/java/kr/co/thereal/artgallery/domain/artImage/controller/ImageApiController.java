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

//@Controller
@RestController
@RequiredArgsConstructor
@Controller
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
    public List<ImageEntity> view() {
        System.out.println("이미지" + imageRepository.findAll());
        return imageRepository.findAll();
    }


//    @GetMapping("/images/{imageId}")
//    @ResponseBody
//    public Resource downloadImage(@PathVariable("imageId") Long id, Model model) throws IOException {
//
//        ImageEntity image = imageRepository.findById(id).orElse(null);
//        System.out.println("이미지 " + image);
//        return new UrlResource("file:" + image.getSavedPath());
//    }
//
//    @GetMapping("/attach/{id}")
//    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException {
//        ImageEntity image = imageRepository.findById(id).orElse(null);
//
//        UrlResource resource = new UrlResource("file:" + image.getSavedPath());
//
//        String encodedFileName = UriUtils.encode(image.getOrgNm(), StandardCharsets.UTF_8);
//
//        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";
//
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
//    }

}

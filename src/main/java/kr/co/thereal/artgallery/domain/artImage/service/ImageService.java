package kr.co.thereal.artgallery.domain.artImage.service;

import kr.co.thereal.artgallery.domain.artImage.Repository.ImageRepository;
import kr.co.thereal.artgallery.domain.artImage.entity.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {


    @Value("${logging.file.path}")
    private String imageDir;

    private final ImageRepository imageRepository;

    public Long saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        // 이미지 원본명
        String originName = file.getOriginalFilename();
        // 파일 이름으로 쓸 명 uuid로 생성
        String uuid = UUID.randomUUID().toString();
        //확장자 추출
        String extension = originName.substring(originName.lastIndexOf("."));
        // uuid와 확장자 결합
        String savedName = uuid + extension;
        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = imageDir + savedName;

        // 로컬에 이미지를 저장
        File destFile = new File(savedPath);
        file.transferTo(destFile);

        ImageEntity image = ImageEntity.builder()
                .orgNm(originName)
                .saveNm(savedName)
                .savedPath(savedPath)
                .build();

        ImageEntity savedImage = imageRepository.save(image);
//        System.out.println("파일" + savedImage);

        return savedImage.getId();

    }
}

package kr.co.thereal.artgallery.domain.artImage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Image")
@NoArgsConstructor
@Data
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private String orgNm;

    private String saveNm;

    private String savedPath;

    @Builder
    public ImageEntity(Long id, String orgNm, String saveNm, String savedPath){
        this.id = id;
        this.orgNm = orgNm;
        this.saveNm = saveNm;
        this.savedPath = savedPath;
    }
}

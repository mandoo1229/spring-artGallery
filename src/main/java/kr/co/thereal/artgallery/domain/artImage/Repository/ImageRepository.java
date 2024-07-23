package kr.co.thereal.artgallery.domain.artImage.Repository;

import kr.co.thereal.artgallery.domain.artImage.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}

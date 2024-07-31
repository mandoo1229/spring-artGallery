package kr.co.thereal.artgallery.domain.admin.repository;

import kr.co.thereal.artgallery.domain.admin.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    Optional<AdminEntity> findByLoginId(String loginId);
}

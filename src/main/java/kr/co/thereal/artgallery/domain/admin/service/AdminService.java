package kr.co.thereal.artgallery.domain.admin.service;

import jakarta.transaction.Transactional;
import kr.co.thereal.artgallery.domain.admin.dto.AdminDto;
import kr.co.thereal.artgallery.domain.admin.entity.AdminEntity;
import kr.co.thereal.artgallery.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    /*
    회원가입
     */

    public String signUp(AdminDto dto) {
        Optional<AdminEntity> findAdmin = adminRepository.findByLoginId(dto.getLoginId());
        if (findAdmin.isPresent()) {
            return "중복된 ID입니다.";
        }

        AdminEntity adminEntity = AdminEntity.builder()
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .name(dto.getName())
                .createdDate(dto.getCreateDate())
                .build();

        adminRepository.save(adminEntity);
        return "가입 완성";
    }

}

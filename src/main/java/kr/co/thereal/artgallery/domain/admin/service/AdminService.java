package kr.co.thereal.artgallery.domain.admin.service;

import jakarta.transaction.Transactional;
import kr.co.thereal.artgallery.domain.admin.dto.AdminDto;
import kr.co.thereal.artgallery.domain.admin.entity.AdminEntity;
import kr.co.thereal.artgallery.domain.admin.repository.AdminRepository;
import kr.co.thereal.artgallery.global.exception.InvalidInputException;
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

//    public String signUp(AdminDto dto) {
//        adminRepository.findByLoginId(dto.getLoginId())
//                        .orElseThrow(()-> new InvalidInputException("loginId", "중복입니다"));
//
//
//        AdminEntity adminEntity = dto.toEntity();
//
//
//        adminRepository.save(adminEntity);
//        return "가입 완성";
//    }


    public String signUp(AdminDto dto) {
        adminRepository.findByLoginId(dto.getLoginId())
                .ifPresent(admin -> {
                    throw new InvalidInputException("loginId", "중복입니다");
                });

        AdminEntity adminEntity = dto.toEntity();
        adminRepository.save(adminEntity);
        return "가입 완성";
    }

}

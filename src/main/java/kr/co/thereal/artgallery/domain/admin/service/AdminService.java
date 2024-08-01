package kr.co.thereal.artgallery.domain.admin.service;

import jakarta.transaction.Transactional;
import kr.co.thereal.artgallery.domain.admin.dto.AdminDto;
import kr.co.thereal.artgallery.domain.admin.entity.AdminEntity;
import kr.co.thereal.artgallery.domain.admin.entity.AdminRole;
import kr.co.thereal.artgallery.domain.admin.entity.Role;
import kr.co.thereal.artgallery.domain.admin.repository.AdminRepository;
import kr.co.thereal.artgallery.domain.admin.repository.AdminRoleRepository;
import kr.co.thereal.artgallery.global.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final AdminRoleRepository adminRoleRepository;
    private final PasswordEncoder passwordEncoder;

    /*
    회원가입
     */
    public String signUp(AdminDto dto) {

        // 아이디 중복체크 입니다.
        if (adminRepository.findByLoginId(dto.getLoginId()).isPresent()) {
            throw new InvalidInputException("loginId", "중복된 ID입니다.");
        }

        // 비밀번호 암호화 회원가입
        String passwords = passwordEncoder.encode(dto.getPassword());
        AdminEntity adminEntity = AdminEntity.builder()
                .loginId(dto.getLoginId())
                .password(passwords)
                .name(dto.getName())
                .createdDate(dto.getCreateDate())
                .build();
        adminRepository.save(adminEntity);

        /*
         * 계정 권한 설정
         */
        AdminRole adminRole = AdminRole.builder()
                .role(Role.MEMBER)
                .adminEntity(adminEntity)
                .build();
        adminRoleRepository.save(adminRole);

        System.out.println("데이터 접근" + adminRepository.save(adminEntity));

        return "가입 완성";
    }

}

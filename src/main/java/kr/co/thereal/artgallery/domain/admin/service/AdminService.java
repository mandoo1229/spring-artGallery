package kr.co.thereal.artgallery.domain.admin.service;

import jakarta.transaction.Transactional;
import kr.co.thereal.artgallery.domain.admin.dto.AdminDto;
import kr.co.thereal.artgallery.domain.admin.dto.LoginDto;
import kr.co.thereal.artgallery.domain.admin.entity.AdminEntity;
import kr.co.thereal.artgallery.domain.admin.repository.AdminRepository;
import kr.co.thereal.artgallery.global.authority.JwtTokenProvider;
import kr.co.thereal.artgallery.global.authority.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /*
     * 회원가입
     */
    public String signUp(AdminDto dto) {
        Optional<AdminEntity> findAdmin = adminRepository.findByAdminId(dto.getAdminId());
        if (findAdmin.isPresent()) {
            return "중복된 ID입니다.";
        }
        System.out.println("프린터 데이터");
        // 비밀번호를 인코딩하여 암호화 합니다.
        String password = passwordEncoder.encode(dto.getPassword());
//
        AdminEntity entity = AdminEntity.builder()
                .adminId(dto.getAdminId())
                .password(password)
                .adminName(dto.getAdminName())
                // 현재 시간 기준으로 저장을 합니다. (계정 생성 날짜)
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        adminRepository.save(entity);
        return "가입되었습니다.";
    }


    /*
    로그인 기능
     */

    public TokenInfo login(LoginDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getLoginId(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authentication);
    }
}

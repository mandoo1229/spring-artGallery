package kr.co.thereal.artgallery.global.service;


import kr.co.thereal.artgallery.domain.admin.service.AdminSecurityService;
import kr.co.thereal.artgallery.global.dto.AccessTokenDto;
import kr.co.thereal.artgallery.global.dto.RefreshTokenDto;
import kr.co.thereal.artgallery.global.dto.RefreshTokenValidateDto;
import kr.co.thereal.artgallery.global.jwt.JwtTokenProvider;
import kr.co.thereal.artgallery.global.repositry.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final AdminSecurityService adminSecurityService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AccessTokenDto generateAccessToken(String adminId) {
        UserDetails admin = adminSecurityService.loadUserByUsername(adminId);

        String userid = admin.getUsername();
        String roles = admin.getAuthorities().stream().toList().get(0).toString();
        AccessTokenDto accessTokenDto = JwtTokenProvider.generateAccessToken(userid, roles);
        return accessTokenDto;
    }

    public RefreshTokenDto generateRefreshToken(String adminId) {
        UserDetails admin = adminSecurityService.loadUserByUsername(adminId);

        String userid = admin.getUsername();
        String roles = admin.getAuthorities().stream().toList().get(0).toString();
        RefreshTokenDto refreshTokenDto = JwtTokenProvider.generateRefreshToken(userid, roles);
        saveRefreshToken(refreshTokenDto);
        return refreshTokenDto;
    }

    public void saveRefreshToken(RefreshTokenDto refreshTokenDto) {
        refreshTokenRepository.save(refreshTokenDto.toEntity());
    }

    public boolean validateRefreshToken(RefreshTokenValidateDto refreshTokenDto) {
        if (JwtTokenProvider.validateAccessToken(refreshTokenDto.getRefreshToken())) {
            System.out.println(refreshTokenDto.getRefreshToken());
            System.out.println(refreshTokenRepository.findByRefreshToken(refreshTokenDto.getRefreshToken()).get().getRefreshToken());
            if (refreshTokenDto.getRefreshToken().equals(refreshTokenRepository.findByRefreshToken(refreshTokenDto.getRefreshToken()).get().getRefreshToken())) {
                System.out.println("동일한 refresh token 존재");
                System.out.println(refreshTokenDto);
                return true;
            } else {
                System.out.println("동일한 토큰이 존재하지 않음");
                return false;
            }
        } else {
            System.out.println("refresh token 만료");
            return false;
        }
    }

    public String getAdminIdFromRefreshToken(RefreshTokenValidateDto refreshTokenValidateDto) {
        return refreshTokenRepository.findByRefreshToken(refreshTokenValidateDto.getRefreshToken()).get().getAdminId();
    }
}

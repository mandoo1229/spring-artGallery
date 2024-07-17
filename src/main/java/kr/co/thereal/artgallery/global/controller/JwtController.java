package kr.co.thereal.artgallery.global.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.thereal.artgallery.global.api.DefaultResponse;
import kr.co.thereal.artgallery.global.api.ResponseMessage;
import kr.co.thereal.artgallery.global.api.StatusCode;
import kr.co.thereal.artgallery.global.dto.RefreshTokenValidateDto;
import kr.co.thereal.artgallery.global.jwt.JwtTokenProvider;
import kr.co.thereal.artgallery.global.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class JwtController {

    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> getRefreshToken(HttpServletRequest request) {
        System.out.println("Request 들어옴" + request);
        System.out.println("Request Header" + request.getHeader("refresh-token"));
        RefreshTokenValidateDto refreshTokenDto = new RefreshTokenValidateDto(request.getHeader("refresh-token"));
        System.out.println(refreshTokenDto.getRefreshToken());

        if (tokenService.validateRefreshToken(refreshTokenDto)) {
            String userid = jwtTokenProvider.getUserIdByRefreshToken(refreshTokenDto.getRefreshToken());
            String roles = jwtTokenProvider.getAdminRoleByRefreshToken(refreshTokenDto.getRefreshToken());
            System.out.println(userid);
            System.out.println(roles);
            String newAccessToken = "Bearer " + JwtTokenProvider.generateAccessToken(userid, roles).getAccessToken();
            System.out.println("새로 발급 받은 access token" + newAccessToken);
            return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    DefaultResponse.res(
                            StatusCode.NOT_ACCEPTABLE,
                            ResponseMessage.LOG_OUT
                    ), HttpStatus.NOT_ACCEPTABLE
            );
        }
    }
}

package kr.co.thereal.artgallery.domain.admin.controller;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.thereal.artgallery.domain.admin.dto.AdminDto;
import kr.co.thereal.artgallery.domain.admin.service.AdminSecurityService;
import kr.co.thereal.artgallery.domain.admin.service.AdminService;
import kr.co.thereal.artgallery.global.dto.AccessTokenDto;
import kr.co.thereal.artgallery.global.dto.RefreshTokenDto;
import kr.co.thereal.artgallery.global.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApiController {
    private final PasswordEncoder passwordEncoder;
    private final AdminSecurityService adminSecurityService;
    private final TokenService tokenService;
    private final Gson gson = new Gson();
    private final AdminService adminService;

    @Operation(hidden = true)
    @PostMapping("/login")
    public ResponseEntity<?> checkLogin(@RequestBody Map<String, String> loginData) {
        System.out.println("loginData:" + loginData);
        AdminDto adminDto = adminService.getAdminByAdminId(loginData.get("adminId"));

        String adminId = adminDto.getUserid();

        if(!passwordEncoder.matches(loginData.get("password"), adminDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        AccessTokenDto accessTokenDto = tokenService.generateAccessToken(adminId);
        RefreshTokenDto refreshTokenDto = tokenService.generateRefreshToken(adminId);

        String accessToken = accessTokenDto.getAccessToken();
        String refreshToken = refreshTokenDto.getRefreshToken();

        Map<String, String> loginRes = new HashMap<>();
        loginRes.put("refresh-token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer " + accessToken);

        return new ResponseEntity<>(loginRes, headers, HttpStatus.OK);
    }


    @Operation(hidden = true)
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody AdminDto adminDto) {
        System.out.println(adminDto);
        System.out.println("제발 도착해라!");
        adminService.signUp(adminDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

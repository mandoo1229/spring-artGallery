package kr.co.thereal.artgallery.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.thereal.artgallery.domain.admin.service.AdminSecurityService;
import kr.co.thereal.artgallery.global.dto.AccessTokenDto;
import kr.co.thereal.artgallery.global.dto.RefreshTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenProvider {
    private final AdminSecurityService adminSecurityService;

    // JWT에서 사용할 시크릿 키를 생성해줌
    private static final String JWT_ACCESS_TOKEN_SECRET = "secretKey1";
    private static final String JWT_REFRESH_TOKEN_SECRET = "secretKey2";

    //토큰 유요시간 설정
    // Access Token 유효시간 30분
    private static final long JWT_ACCESS_EXPIRATION_TIME = 30 * 60 * 1000L;
    // Refresh Token 유효시간 7일
    private static final long JWT_REFRESH_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000L;
    private static final String TOKEN_HEADER_NAME = "Authorization";

    public static AccessTokenDto generateAccessToken(String adminId, String roles) {
        Date now = new Date();
        Date accessTokenExpiresDate = new Date(now.getTime() + JWT_ACCESS_EXPIRATION_TIME);
        Claims claims = Jwts.claims().setSubject(adminId);
        claims.put("roles", roles);
        String accessToken = Jwts.builder()
                .setClaims(claims)  // 사용자 정보 저장
                .setIssuedAt(now)   // 토큰 생성 시간
                .setExpiration(accessTokenExpiresDate)  // 토큰 만료시간 - 생성으로부터 30분
                .signWith(SignatureAlgorithm.HS256, JWT_ACCESS_TOKEN_SECRET)    // 암호화 알고리즘 설정
                .compact(); // 생성 완료

        return AccessTokenDto.builder().accessToken(accessToken).key(adminId).build();
    }

    public static RefreshTokenDto generateRefreshToken(String adminId, String roles) {
        Date now = new Date();
        Date refreshTokenExpiresDate = new Date(now.getTime() + JWT_REFRESH_EXPIRATION_TIME);
        Claims claims = Jwts.claims().setSubject(adminId);
        claims.put("roles", roles);

        String refreshToken = Jwts.builder()
                .setClaims(claims) // 사용자 정보 저장
                .setIssuedAt(now)   // 토큰 생성시간
                .setExpiration(refreshTokenExpiresDate) // 토큰 만료 시간 - 생성으로부터 14일
                .signWith(SignatureAlgorithm.HS256, JWT_REFRESH_TOKEN_SECRET)   //암호화 알고리즘
                .compact(); //생성완료

        return RefreshTokenDto.builder().refreshToken(refreshToken).adminId(adminId).build();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = adminSecurityService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(JWT_ACCESS_TOKEN_SECRET).parseClaimsJwt(token).getBody().getSubject();
    }

    public String getUserIdByRefreshToken(String token) {
        return Jwts.parser().setSigningKey(JWT_ACCESS_TOKEN_SECRET).parseClaimsJwt(token).getBody().getSubject();
    }

    public String getAdminRole(String token) {
        return (String) Jwts.parser().setSigningKey(JWT_ACCESS_TOKEN_SECRET).parseClaimsJwt(token).getBody().get("roles");
    }

    public String getAdminRoleByRefreshToken(String refreshToken) {
        return (String) Jwts.parser().setSigningKey(JWT_ACCESS_TOKEN_SECRET).parseClaimsJwt(refreshToken).getBody().get("roles");
    }

    // Request의 Header에서 Token 값을 가져온다.
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER_NAME);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }

        return null;
    }

    // 토큰의 유효성 + 만료일자 확인
    public static boolean validateAccessToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_ACCESS_TOKEN_SECRET).parseClaimsJws(accessToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }






}

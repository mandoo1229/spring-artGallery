package kr.co.thereal.artgallery.global.jwt;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.thereal.artgallery.domain.admin.dto.AdminGetAdminIdDto;
import kr.co.thereal.artgallery.domain.admin.dto.AdminGetRoleDto;
import kr.co.thereal.artgallery.global.api.ResponseMessage;
import kr.co.thereal.artgallery.global.dto.RefreshTokenValidateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.net.http.HttpRequest;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("[JWT] ::: doFilter()");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = jwtTokenProvider.resolveToken(httpRequest);

        System.out.println("JWT ::: resolveToken()" + token);

        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("===============================================");
            System.out.println("name : " + SecurityContextHolder.getContext().getAuthentication().getName());
            System.out.println("isAuthenticated : " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
            System.out.println("authority : " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            System.out.println("principal : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            System.out.println("credentials : " + SecurityContextHolder.getContext().getAuthentication().getCredentials());
            System.out.println("===============================================");

            System.out.println("토큰 유효");

            AdminGetAdminIdDto adminGetAdminIdDto = AdminGetAdminIdDto.builder().adminId(jwtTokenProvider.getUserPk(token)).build();
            System.out.println("adminGetAdminIdDto" + adminGetAdminIdDto);
            AdminGetRoleDto adminGetRoleDto = AdminGetRoleDto.builder().role(jwtTokenProvider.getAdminRole(token)).build();
            System.out.println("adminGetRoleDto" + adminGetRoleDto);
            httpRequest.setAttribute("AccessTokenValidation", "true");
            httpRequest.setAttribute("adminIdDto", adminGetAdminIdDto);
            httpRequest.setAttribute("adminRoleDto", adminGetRoleDto);
        } else if (token == null) {
            System.out.println("토큰이 존재하지 않습니다.");
        } else if (!jwtTokenProvider.validateAccessToken(token)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setHeader("responseMessage", ResponseMessage.ACCESS_TOKEN_EXPIRED);
        }
        chain.doFilter(request, response);
    }


    private RefreshTokenValidateDto getRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("refresh-Token");
        return new RefreshTokenValidateDto(refreshToken);
    }


}

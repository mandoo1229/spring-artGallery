package kr.co.thereal.artgallery.global.security;

import kr.co.thereal.artgallery.global.jwt.JwtTokenProvider;
import kr.co.thereal.artgallery.global.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private JwtTokenProvider jwtTokenProvider;
    private TokenService tokenService;

    // password 암호화
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws  Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrfConfig) ->
                        csrfConfig.disable())
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable())
                )
                .sessionManagement((sessionCreationPolicy)->
                        sessionCreationPolicy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests)->
                        authorizeRequests
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("**")).permitAll()
                )
                .formLogin((formLogin)->
                        formLogin.loginPage("/admin/login")
                                .defaultSuccessUrl("/home")
                )
                .logout((logoutConfig)->
                        logoutConfig.logoutSuccessUrl("/")
                );
        return http.build();
        }
    }


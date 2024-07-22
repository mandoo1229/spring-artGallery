package kr.co.thereal.artgallery.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity HttpSecurity) throws Exception {
        HttpSecurity
                .authorizeHttpRequests((authz)->
                        authz
                                .requestMatchers("/h2console/**").permitAll()
                                .requestMatchers("/")
                        )
                .formLogin(login -> login
                                .loginPage("/")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/main")
                        )
                .logout((logoutConfig)->
                        logoutConfig.logoutSuccessUrl("/"))
                .csrf((csrfConfig)-> csrfConfig.disable())
                .headers((headerConfig)-> headerConfig.disable())
                .sessionManagement((sessionManagementConfig)-> sessionManagementConfig.disable())
                .authorizeHttpRequests((authorizeRequest)->
                        authorizeRequest
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("**")).permitAll()
                        );
        return HttpSecurity.build();

    }

}

package kr.co.thereal.artgallery.domain.admin.service;

import jakarta.transaction.Transactional;
import kr.co.thereal.artgallery.domain.admin.entity.AdminEntity;
import kr.co.thereal.artgallery.domain.admin.repository.AdminRepository;
import kr.co.thereal.artgallery.global.authority.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailService implements UserDetailsService {
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminEntity adminEntity = adminRepository.findByAdminId(username)
                .orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return createUserDetails(adminEntity);
    }

    private UserDetails createUserDetails(AdminEntity adminEntity) {
        List<SimpleGrantedAuthority> authorities = adminEntity.getAdminRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                .toList();
        return new CustomUser(adminEntity.getId(), adminEntity.getAdminId(), adminEntity.getPassword(), authorities);
    }


}

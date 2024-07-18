package kr.co.thereal.artgallery.domain.admin.service;

import kr.co.thereal.artgallery.domain.admin.entity.Admin;
import kr.co.thereal.artgallery.domain.admin.repository.AdminRepository;
import kr.co.thereal.artgallery.global.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminSecurityService implements UserDetailsService {
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Optional<Admin>  _admin = this.adminRepository.findByUserid(userid);

        if(_admin.isEmpty()) {
            throw new UsernameNotFoundException("가입되어있지 않는 아이디 입니다.");
        }
        Admin admin = _admin.get();

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        System.out.println(admin.getUserid());

        return new User(admin.getUserid(), admin.getPassword(), authorityList);
    }
}

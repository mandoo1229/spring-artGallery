package kr.co.thereal.artgallery.admin.service;

import kr.co.thereal.artgallery.admin.entity.Admin;
import kr.co.thereal.artgallery.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public Admin create(String userId, String password) {
        Admin admin = new Admin();
        admin.setUserId(userId);
        admin.setPassword(passwordEncoder.encode(password));
        this.adminRepository.save(admin);
        return admin;
    }

//    public Long save(AdminSignupDto adminRequestDto) throws Exception {
//
//    }
}

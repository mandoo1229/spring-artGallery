package kr.co.thereal.artgallery.domain.admin.service;

import kr.co.thereal.artgallery.domain.admin.dto.AdminDto;
import kr.co.thereal.artgallery.domain.admin.entity.Admin;
import kr.co.thereal.artgallery.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminDto getAdminById(Long id) {
        Optional<Admin> _admin = adminRepository.findById(id);

        if (_admin.isEmpty()) {
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
        }
        AdminDto adminDto = new AdminDto(_admin.get());
        return adminDto;
    }

    public AdminDto getAdminByAdminId(String userid) {
        Optional<Admin> _admin = adminRepository.findByUserid(userid);
        if (_admin.isEmpty()) {
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
        }
        AdminDto adminDto = new AdminDto(_admin.get());
        return adminDto;
    }

    public void signUp(AdminDto adminDto) {
        Admin admin  = new Admin();
        admin.setUserid(adminDto.getUserid());
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));

        adminRepository.save(admin);
    }




}

package kr.co.thereal.artgallery.admin.service;

import kr.co.thereal.artgallery.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSecurityService{
    private final AdminRepository adminRepository;

}

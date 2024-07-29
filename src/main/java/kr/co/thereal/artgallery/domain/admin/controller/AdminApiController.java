package kr.co.thereal.artgallery.domain.admin.controller;

import kr.co.thereal.artgallery.domain.admin.dto.AdminDto;
import kr.co.thereal.artgallery.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminApiController {
    private final AdminService adminService;

    @PostMapping("/signup")
    public String signup(@RequestBody AdminDto dto) {
        return adminService.signUp(dto);
    }
}

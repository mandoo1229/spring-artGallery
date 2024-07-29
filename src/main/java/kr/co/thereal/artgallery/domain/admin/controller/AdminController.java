package kr.co.thereal.artgallery.domain.admin.controller;

import kr.co.thereal.artgallery.domain.admin.dto.AdminDto;
import kr.co.thereal.artgallery.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/user/signUn")
    public String joinForm() {
        return "admin/SignUn";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody AdminDto dto) {
        System.out.println("회원가입" + adminService.signUp(dto));
        return adminService.signUp(dto);
    }


}

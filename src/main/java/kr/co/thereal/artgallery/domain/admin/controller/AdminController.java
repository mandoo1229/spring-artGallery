package kr.co.thereal.artgallery.domain.admin.controller;

import jakarta.validation.Valid;
import kr.co.thereal.artgallery.domain.admin.dto.AdminDto;
import kr.co.thereal.artgallery.domain.admin.form.AdminRegisterForm;
import kr.co.thereal.artgallery.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;


    @PostMapping("/signup")
    public String signup(@ModelAttribute AdminDto adminDto, @Valid AdminRegisterForm adminRegisterForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/signup";
        }
        if (!adminRegisterForm.getPassword().equals(adminRegisterForm.getPassword())) {
            bindingResult.rejectValue("passwordCheck", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
        }

        try {
            adminService.signUp(adminDto);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
            return "admin/signup";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "admin/signup";
        }
        return "redirect:/admin/login";
    }
}

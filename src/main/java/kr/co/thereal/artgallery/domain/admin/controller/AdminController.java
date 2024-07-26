package kr.co.thereal.artgallery.domain.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {
    @GetMapping("/user/signUn")
    public String joinForm() {
        return "admin/SignUn";
    }



}

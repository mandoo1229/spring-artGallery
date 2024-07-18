package kr.co.thereal.artgallery.domain.admin.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AdminRegisterForm {
    @NotEmpty(message = "아이디를 입력해주세요")
    private String adminId;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;

    @NotEmpty(message = "패스워드를 한 번 더 입력해주세요.")
    private String passwordCheck;

}

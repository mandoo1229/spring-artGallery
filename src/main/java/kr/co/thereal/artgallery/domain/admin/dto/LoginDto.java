package kr.co.thereal.artgallery.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}

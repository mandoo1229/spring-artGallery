package kr.co.thereal.artgallery.domain.admin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminDto {
    private String loginId;
    private String password;
    private String name;
    private LocalDate createDate;
}

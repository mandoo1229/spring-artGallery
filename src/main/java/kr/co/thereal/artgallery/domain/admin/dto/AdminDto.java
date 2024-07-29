package kr.co.thereal.artgallery.domain.admin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminDto {

    private String adminId;
    private String password;
    private String adminName;
    private LocalDate createdDate;

}
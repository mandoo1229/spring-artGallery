package kr.co.thereal.artgallery.admin.dto;

import kr.co.thereal.artgallery.admin.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto {
    private String userId;
    private String password;
}

package kr.co.thereal.artgallery.domain.admin.dto;

import kr.co.thereal.artgallery.domain.admin.entity.Admin;
import kr.co.thereal.artgallery.global.security.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDto {
    private Long id;
    private String userid;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private UserRole role;

    public AdminDto(Admin admin) {
        this.id = admin.getId();
        this.userid = admin.getUserid();
        this.password = admin.getPassword();
        this.createdDate = admin.getCreatedDate();
        this.lastModifiedDate = admin.getLastModifiedDate();
        this.role = admin.getRole();
    }

    public Admin toEntity() {
        return Admin.builder()
                .id(id)
                .userid(userid)
                .password(password)
                .createdDate(createdDate)
                .lastModifiedDate(lastModifiedDate)
                .role(role)
                .build();
    }
}

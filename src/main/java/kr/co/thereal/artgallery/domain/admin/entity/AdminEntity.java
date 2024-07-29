package kr.co.thereal.artgallery.domain.admin.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Admin")
@NoArgsConstructor
@Data
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

    private String adminId;

    private String password;

    private String adminName;

    @CreatedDate
    private Timestamp createdDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "adminEntity")
    private List<AdminRole> adminRoles;

    @Builder
    public AdminEntity(Long id, String adminId, String password, String adminName, Timestamp createdDate, Set<AdminRole> adminRoles) {
        this.id = id;
        this.adminId = adminId;
        this.password = password;
        this.adminName = adminName;
        this.createdDate = createdDate;
        this.adminRoles = null;
    }
}
package kr.co.thereal.artgallery.domain.admin.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "admin")
@NoArgsConstructor
@Data
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true, updatable = false)
    private String loginId;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @CreatedDate
    private LocalDate createdDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private List<AdminRole> adminRoles;


    @Builder
    public AdminEntity(Long id, String loginId, String password, String name, LocalDate createdDate) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.createdDate = createdDate;
        this.adminRoles = null;
    }
}
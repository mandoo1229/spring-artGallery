package kr.co.thereal.artgallery.domain.admin.entity;

import jakarta.persistence.*;
import kr.co.thereal.artgallery.global.status.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class AdminRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Role role;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "id"))
    private AdminEntity adminEntity;


    @Builder
    private AdminRole(Long id, Role role, AdminEntity adminEntity) {
        this.id = id;
        this.role = role;
        this.adminEntity = adminEntity;
    }
}
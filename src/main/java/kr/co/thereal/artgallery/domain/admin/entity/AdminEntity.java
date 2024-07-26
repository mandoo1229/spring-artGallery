package kr.co.thereal.artgallery.domain.admin.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

@Entity
@Table(name = "Admin")
@NoArgsConstructor
@Data
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String adminId;

    private String password;

    private String adminName;

    @CreatedDate
    private Timestamp createdDate;

    @Builder
    public AdminEntity(Long id, String adminId, String password, String adminName, Timestamp createdDate) {
        this.id = id;
        this.adminId = adminId;
        this.password = password;
        this.adminName = adminName;
        this.createdDate = createdDate;
    }

}

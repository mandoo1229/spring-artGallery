package kr.co.thereal.artgallery.global.dto;

import kr.co.thereal.artgallery.global.entity.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {
    private Long id;
    private String refreshToken;
    private String adminId;

    public RefreshTokenDto(RefreshToken refreshToken) {
        this.id = refreshToken.getId();
        this.refreshToken = refreshToken.getRefreshToken();
        this.adminId = refreshToken.getAdminId();
    }

    public RefreshToken toEntity() {
        return RefreshToken.builder()
                .id(id)
                .refreshToken(refreshToken)
                .adminId(adminId)
                .build();
    }
}

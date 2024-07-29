package kr.co.thereal.artgallery.global.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
    private long adminId;
    private String adminName;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public CustomUser(Long adminId, String adminName, String password, Collection<? extends GrantedAuthority> authorities) {
        super(adminName, password, authorities);
        this.adminId = adminId;
    }

    public Long getAdminId() {
        return adminId;
    }
}

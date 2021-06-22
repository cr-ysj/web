package com.example.demo.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import java.io.Serializable;
import java.util.List;

@ToString
@Data
public class Auth extends BaseDomain implements Serializable , GrantedAuthority {
    private static final long serialVersionUID = 1L;
    private Long id;

    private Long roleId;
    // 权限
    private String auth;

    // 描述
    private String description;

    @Override
    public String getAuthority() {
        return auth;
    }
}

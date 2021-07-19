package com.example.demo.pojo.db.user;

import com.example.demo.pojo.db.auth.Auth;
import com.example.demo.pojo.BaseDomain;
import com.example.demo.pojo.db.role.Role;
import com.example.demo.pojo.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@JsonIgnoreProperties(value = {"handler"})
@Data
@ToString
public class User implements Serializable {

    public User(){

    }
    public User(String username){
        this.username=username;
    }

    private Long id;

    private String username;


    private String password;

    private String nikName;


    private String email;


    private String phone;

    private UserStatus userStatus;

    //角色列表
    public List<Role> roleList;
    //角色列表
    public List<Auth> authorities;

    //获取权限
    public Collection<GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(roleList==null){
            return grantedAuthorities;
        }
        for (int i = 0; i < roleList.size(); i++) {
            Role role= roleList.get(i);
            if(role!=null){
                List<Auth> authList = role.getAuthList();
                for (int j = 0; j <authList.size() ; j++) {
                    grantedAuthorities.add(authList.get(j));
                }
            }
        }
        return grantedAuthorities;
    }

    public Collection<GrantedAuthority> getAuthorities(List<Auth> authorities) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (int i = 0; i < authorities.size(); i++) {
            Auth auth= authorities.get(i);
            if(auth!=null){
                grantedAuthorities.add(auth);
            }
        }
        return grantedAuthorities;
    }
}

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
    private List<Role> roleList=new ArrayList<>();

    public List<Role> authorities=new ArrayList();
    //获取权限
    public Collection<GrantedAuthority> getAuthorities(List<Map> authorities){
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<Map> roleList =authorities;
        if(roleList==null){
            return grantedAuthorities;
        }
        for (int i = 0; i <authorities.size() ; i++) {
            Map role =(Map)authorities.get(i);
            if(role!=null){
                grantedAuthorities.add(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return (String)role.get("auth");
                    }
                });
            }
        }
        return grantedAuthorities;
    }
    //获取权限
    public Collection<GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<Role> roleList = this.getRoleList();
        if(roleList==null){
            return grantedAuthorities;
        }
        roleList.stream().forEach(role -> {
            if(role!=null){
                List<Auth> authList = role.getAuthList();
                for (int j = 0; j <authList.size() ; j++) {
                    grantedAuthorities.add(authList.get(j));
                }
            }
        });
        return grantedAuthorities;
    }
}

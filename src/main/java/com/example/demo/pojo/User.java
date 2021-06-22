package com.example.demo.pojo;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@ToString
public class User extends BaseDomain {

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


    //角色列表
    private List<Role> roleList=new ArrayList<>();





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

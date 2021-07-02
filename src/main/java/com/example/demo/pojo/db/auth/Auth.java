package com.example.demo.pojo.db.auth;

import com.example.demo.pojo.db.resource.Resource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.apache.ibatis.executor.loader.javassist.JavassistProxyFactory$EnhancedResultObjectProxyImpl and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.ahav.system.entity.SystemResult["data"]->com.ahav.system.entity.User_$$_jvste64_0["handler"])
 * 参考：
 * https://blog.csdn.net/jxchallenger/article/details/79307062
 * */
@JsonIgnoreProperties(value = {"handler"})
@ToString
@Data
public class Auth  implements Serializable , GrantedAuthority {
    private static final long serialVersionUID = 1L;
    private Long id;

    private Long roleId;
    // 权限
    private String auth;

    // 描述
    private String description;

    //资源
    private List<Resource> resources=new ArrayList<>();

    @Override
    public String getAuthority() {
        return auth;
    }
}

package com.example.demo.config.datasource;

import com.example.demo.pojo.enums.BaseEnum;
import com.example.demo.pojo.enums.DelStatus;
import com.example.demo.pojo.enums.JobStatus;
import com.example.demo.pojo.enums.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@MappedTypes(value = {JobStatus.class, UserStatus.class, DelStatus.class})
@Slf4j
public class EnumTypeHandler extends BaseTypeHandler<BaseEnum> {

    private Class<BaseEnum> type;

    public EnumTypeHandler() {
    }

    public EnumTypeHandler(Class<BaseEnum> type) {
        if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getInt(columnName));
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getInt(columnIndex));
    }

    @Override
    public BaseEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getInt(columnIndex));
    }

    private BaseEnum convert(int status) {
        BaseEnum[] baseEnums = type.getEnumConstants();
        for(BaseEnum baseEnum:baseEnums){
            if(status==baseEnum.getValue()){
                return  baseEnum;
            }
        }
        return null;
    }
}

/**
 * 
 */
package com.palmlink.core.database.ibatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

/**
 * @author Shihai.Fu
 * 
 */
public interface UserMapper extends SqlMapper {

    /**
     * @param id
     * @return
     */
    @Select("SELECT * FROM security.user WHERE id = #{userId}")
    @Results({ @Result(id = true, property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER), @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "aliaseName", column = "aliase_name", javaType = String.class, jdbcType = JdbcType.VARCHAR), @Result(property = "passwordHash", column = "password_hash", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "passwordSalt", column = "password_salt", javaType = String.class, jdbcType = JdbcType.VARCHAR), @Result(property = "passwordIteration", column = "password_iteration", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "appKey", column = "app_key", javaType = String.class, jdbcType = JdbcType.VARCHAR), @Result(property = "secretKey", column = "secret_key", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", javaType = Boolean.class, jdbcType = JdbcType.BIT), @Result(property = "pwdReset", column = "is_pwd_reset", javaType = Boolean.class, jdbcType = JdbcType.BIT) })
    User getUser(@Param("userId") long id);
}

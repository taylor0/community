package com.taylorzhou.community.mapper;


import com.taylorzhou.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserMapper
 * @Description UserMapper
 * @Author taylor
 * @Date 2019/8/25 16:46
 * @Version 1.0
 **/
//@Component
@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified) values(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified})")
    void insert(User user);
}

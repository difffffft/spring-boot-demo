package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import com.example.model.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
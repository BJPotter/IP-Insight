package com.example.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mp.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: Zhi Liu
 * Date: 2024/5/27 16:05
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

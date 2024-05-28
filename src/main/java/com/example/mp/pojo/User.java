package com.example.mp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * Author: Zhi Liu
 * Date: 2024/5/23 17:35
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Data
@TableName(value = "user")
public class User  {
    private String username;
    private String password;
}

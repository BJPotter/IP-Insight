package com.example.mp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Author: Zhi Liu
 * Date: 2024/5/23 17:35
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Data
@TableName(value = "user")
@ApiModel(description = "Details about the user")
public class User  {
    @ApiModelProperty(notes = "The username of the user")
    private String username;
    @ApiModelProperty(notes = "The password of the user")
    private String password;
}

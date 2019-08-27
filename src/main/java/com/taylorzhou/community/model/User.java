package com.taylorzhou.community.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName User
 * @Description User
 * @Author taylor
 * @Date 2019/8/25 16:21
 * @Version 1.0
 **/
@Getter
@Setter
public class User {
    private Integer id;
    private String account_id;
    private String name;
    private String token;
    private Long gmt_create;
    private Long gmt_modified;
}

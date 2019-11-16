package com.scs.web.blog.domain.dto;

import lombok.Data;

/**
 * @author mq_xu
 * @ClassName City
 * @Description 城市类，省会城市level为2，其余地级市level为3
 * @Date 20:45 2019/11/9
 * @Version 1.0
 **/
@Data
public class City {
    private String name;
    private String level;
    private String code;
}

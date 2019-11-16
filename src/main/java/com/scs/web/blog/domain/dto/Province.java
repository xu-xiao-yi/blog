package com.scs.web.blog.domain.dto;

import lombok.Data;

import java.util.List;


/**
 * @author mq_xu
 * @ClassName Province
 * @Description 省，level为1，
 * @Date 20:46 2019/11/9
 * @Version 1.0
 **/
@Data
public class Province {
    private String name;
    private String level;
    private String code;
    private List<City> cities;
}

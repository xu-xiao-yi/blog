package com.scs.web.blog.domain.dto;

import lombok.Data;

/**
 * @author mq_xu
 * @ClassName Address
 * @Description 地址类，由省和市组成
 * @Date 20:08 2019/11/9
 * @Version 1.0
 **/
@Data
public class Address {
    private String province;
    private String city;
}

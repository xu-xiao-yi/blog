package com.scs.web.blog.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @author mq_xu
 * @ClassName provinces
 * @Description 省份集合，包含了全国所有省市数据
 * @Date 20:49 2019/11/9
 * @Version 1.0
 **/
@Data
public class ProvinceList {
    private List<Province> provinceList;
}

package com.scs.web.blog.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scs.web.blog.domain.dto.City;
import com.scs.web.blog.domain.dto.Province;
import com.scs.web.blog.domain.dto.ProvinceList;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author mq_xu
 * @ClassName StingUtil
 * @Description 模拟构造用户数据的工具类
 * @Date 11:30 2019/11/9
 * @Version 1.0
 **/
public class DataUtil {
    private static Logger logger = LoggerFactory.getLogger(DataUtil.class);

    /**
     * 生成手机号
     *
     * @return
     */
    public static String getMobile() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder("139");
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(String.valueOf(random.nextInt(9)));
        }
        return stringBuilder.toString();
    }

    /**
     * 生成密码并用MD5加密
     *
     * @return
     */
    public static String getPassword() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(String.valueOf(random.nextInt(9)));
        }
        return DigestUtils.md5Hex(stringBuilder.toString());
    }

    /**
     * 生成性别
     *
     * @return
     */
    public static String getGender() {
        Random random = new Random();
        String[] data = new String[]{"男", "女"};
        int index = random.nextInt(2);
        return data[index];
    }

    /**
     * 生成生日
     *
     * @return
     */
    public static LocalDate getBirthday() {
        LocalDate now = LocalDate.now();
        Random random = new Random();
        int bound = random.nextInt(8888);
        return now.minusDays(bound);
    }

    /**
     * 生成地址
     * @return
     */
    public static String getAddress() {
        StringBuilder address = new StringBuilder();
        ClassLoader classLoader = DataUtil.class.getClassLoader();
        URL resource = classLoader.getResource("address.json");
        assert resource != null;
        String path = resource.getPath();
        File file = new File(path);
        Reader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            logger.error("文件找不到");
        }
        assert reader != null;
        BufferedReader br = new BufferedReader(reader);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                address.append(line);
            }
        } catch (IOException e) {
            logger.error("文件io异常");
        }
        Gson gson = new GsonBuilder().create();
        ProvinceList provinces = gson.fromJson(address.toString(), ProvinceList.class);
        List<Province> provinceList = provinces.getProvinceList();
        int size = provinceList.size();
        Random random = new Random();
        int index = random.nextInt(size);
        Province province = provinceList.get(index);
        List<City> cityList = province.getCities();
        size = cityList.size();
        index = random.nextInt(size);
        City city = cityList.get(index);
        return province.getName() + city.getName();
    }

    /**
     * 生成时间
     * @return
     */
    public static LocalDateTime getCreateTime(){
        LocalDateTime now = LocalDateTime.now();
        Random random = new Random();
        int bound = random.nextInt(999);
        return now.minusHours(bound);
    }

    /**
     *
     * @return
     */
    public static Long getUserId(){
        Random random = new Random();
        long bound = random.nextInt(61);
        return bound;
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("111"));
    }
}

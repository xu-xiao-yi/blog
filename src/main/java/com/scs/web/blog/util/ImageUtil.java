package com.scs.web.blog.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

/**
 * @author mq_xu
 * @ClassName VerifyCode
 * @Description 验证码生成
 * @Date 2019/11/18
 * @Version 1.0
 **/
public class ImageUtil {
    public static BufferedImage getImage(int width, int height, String content) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) img.getGraphics();
        Random random = new Random();
        Color color;
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        color = new Color(red, green, blue);
        graphics.setColor(color);
        graphics.fillRect(50, 50, width, height);
        red = random.nextInt(255);
        green = random.nextInt(255);
        blue = random.nextInt(255);
        color = new Color(red, green, blue);
        graphics.setColor(color);
        Font font = new Font("Serif", Font.BOLD, 16);
        graphics.setFont(font);
        graphics.drawString(content, width / 6, height / 3);
        graphics.rotate(1.5);
        return img;
    }

    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 10; i++) {
            //在缓冲区生成图片，用随机生成的字符串
            BufferedImage img = ImageUtil.getImage(120, 35, StringUtil.getRandomCode());
            //指定图片输出目录和文件
            String filePath = "D:/code" + i + ".jpg";
            File file = new File(filePath);
            //通过ImageIO的write方法，将图片以指定格式输出到指定文件
            ImageIO.write(img, "jpg", file);
        }

    }
}

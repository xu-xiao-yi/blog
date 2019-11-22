package com.scs.web.blog.verify;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName ImageUtil
 * @Description 图像工具类
 * @Author mq_xu
 * @Date 2019/11/20
 **/
public class ImageUtil {
    /**
     * 将字符串绘制成指定大小的矩形图片
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage getImage(String content, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        Color foreColor = new Color(26, 160, 52);
        Color bgColor = new Color(60, 63, 65);
        g.setColor(bgColor);
        g.fillRect(0, 0, width, height);
        g.setPaint(foreColor);
        Font font = new Font("微软雅黑", Font.BOLD, 30);
        g.setFont(font);
        g.drawString(content, width / 3, height / 2);
        return img;
    }

    public static void main(String[] args) throws IOException {
        //1.生成验证码
        String code = StringUtil.getRandomString();
        //2.生成图片
        BufferedImage img = ImageUtil.getImage(code, 200, 100);
        //3.将img通过字节输出流输出到指定目录
        File file = new File("D:/code.jpg");
        ImageIO.write(img, "jpg", file);
    }
}

package com.scs.web.blog.controller;

import com.scs.web.blog.util.StringUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author mqxu
 */
@WebServlet(urlPatterns = {"/api/code"})
public class CodeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取随机验证码
        String code = StringUtil.getRandomCode();
        //存入全局
        this.getServletContext().setAttribute("code", code);
        int width = 130;
        int height = 35;
        Random random = new Random();
        Color color = new Color(130, 182, 45);
        // 1.创建图片缓存区,传参为宽高和图片类型
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //2.获取画笔并绘画
        Graphics g = bi.getGraphics();
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.DARK_GRAY);
        g.drawString(code, width / 4, height / 2);
        //3.输出图片
        ServletOutputStream out = resp.getOutputStream();
        ImageIO.write(bi, "JPG", out);
        out.flush();
        out.close();
    }
}

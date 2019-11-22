package com.scs.web.blog.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.listener.MySessionContext;
import com.scs.web.blog.service.UserService;
import com.scs.web.blog.util.Result;
import com.scs.web.blog.util.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author mq_xu
 * @ClassName UserController
 * @Description 用户控制器
 * @Date 2019/11/9
 * @Version 1.0
 **/
@WebServlet(urlPatterns = {"/api/user", "/api/user/*"})
public class UserController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService = ServiceFactory.getUserServiceInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI().trim();
        if ("/api/user".equals(uri)) {
            String page = req.getParameter("page");
            String keywords = req.getParameter("keywords");
            String count = req.getParameter("count");
            if (page != null) {
                getUsersByPage(resp, Integer.parseInt(page), Integer.parseInt(count));
            } else if (keywords != null) {
                getUsersByKeywords(resp, keywords);
            } else {
                getHotUsers(req, resp);
            }
        } else {
            getUser(req, resp);
        }
    }

    private void getHotUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new GsonBuilder().create();
        Result result = userService.getHotUsers();
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }

    private void getUsersByPage(HttpServletResponse resp, int page, int count) throws IOException {
        Gson gson = new GsonBuilder().create();
        Result result = userService.selectByPage(page, count);
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }

    private void getUsersByKeywords(HttpServletResponse resp, String keywords) throws IOException {
        Gson gson = new GsonBuilder().create();
        Result result = userService.selectByKeywords(keywords);
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }


    private void getUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String info = req.getPathInfo().trim();
        //取得路径参数
        String id = info.substring(info.indexOf("/") + 1);
        resp.getWriter().println(id);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI().trim();
        if ("/api/user/sign-in".equals(uri)) {
            signIn(req, resp);
        } else if ("/api/user/sign-up".equals(uri)) {
            signUp(req, resp);
        } else if ("/api/user/check".equals(uri)) {
            check(req, resp);
        }
    }

    private void signIn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        logger.info("登录用户信息：" + stringBuilder.toString());
        Gson gson = new GsonBuilder().create();
        UserDto userDto = gson.fromJson(stringBuilder.toString(), UserDto.class);
        String inputCode = userDto.getCode();
        String sessionId = req.getParameter("token");
        System.out.println(sessionId);
        MySessionContext myc = MySessionContext.getInstance();
        HttpSession session = myc.getSession(sessionId);
        String correctCode = session.getAttribute("code").toString();
        PrintWriter out = resp.getWriter();
        if (inputCode.equalsIgnoreCase(correctCode)) {
            Result result = userService.signIn(userDto);
            out.print(gson.toJson(result));
        } else {
            Result result = Result.failure(ResultCode.USER_VERIFY_CODE_ERROR);
            out.print(gson.toJson(result));
        }
        out.close();
    }

    private void check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("验证账号");
    }

    private void signUp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("注册");
    }
}

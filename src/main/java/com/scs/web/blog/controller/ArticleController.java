package com.scs.web.blog.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.service.ArticleService;
import com.scs.web.blog.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author mq_xu
 * @ClassName ArticleController
 * @Description 文章控制器
 * @Date 2019/11/11
 * @Version 1.0
 **/
@WebServlet(urlPatterns = {"/api/article", "/api/article/*"})
public class ArticleController extends HttpServlet {
    private ArticleService articleService = ServiceFactory.getArticleServiceInstance();
    private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //取得请求地址
        String uri = req.getRequestURI().trim();
        if ("/api/article".equals(uri)) {
            String page = req.getParameter("page");
            if (page != null) {
                getArticlesByPage(req, resp);
            } else {
                getHotArticles(req, resp);
            }
        } else {
            getArticle(req, resp);
        }
    }

    private void getHotArticles(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();
        Result result = articleService.getHotArticles();
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }

    private void getArticlesByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        resp.getWriter().print("第" + page + "页");
    }

    private void getArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String info = req.getPathInfo().trim();
        //取得路径参数
        String id = info.substring(info.indexOf("/") + 1);
        Result result = articleService.getArticle(Long.parseLong(id));
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}

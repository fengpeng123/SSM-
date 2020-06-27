package com.controller;

import com.entity.News;
import com.entity.User;
import com.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class NewsController {

    @Autowired
    private NewsService newsService;

    //显示所有新闻
    @RequestMapping("/ShowNews")
    public void ShowNews(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        try {
            List<News> newsList = newsService.QueryNews();
            for (News s:newsList
            ) {
                System.out.println(s.getNewsId());
            }

            request.setAttribute("News", newsList);
            request.getRequestDispatcher("ShowNews.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/AddNews")
    public void addNew(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Context-Type","text/html;charset=utf-8");
        News news =new News();
        news.setNewsId(Integer.valueOf(request.getParameter("newsid")));
        news.setNewsAuthor(request.getParameter("author"));
        news.setNewsContent(request.getParameter("Content"));
        news.setNewsTitle(request.getParameter("title"));
        NewsService newsService=new NewsService();
        try {
            newsService.AddNews(news);
            request.getRequestDispatcher("ShowNews").forward(request,response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/UpdateNews")
    public void editNew(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Context-Type", "text/html;charset=utf-8");
        News news = new News();
        news.setNewsId(Integer.valueOf(request.getParameter("newsid")));
        news.setNewsAuthor(request.getParameter("author"));
        news.setNewsContent(request.getParameter("Content"));
        news.setNewsTitle(request.getParameter("title"));
        NewsService newsService = new NewsService();
        try {
            newsService.UpdateNews(news);
            request.getRequestDispatcher("ShowNews").forward(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/deleteNew")
    public void deleteNew(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int newsID=Integer.valueOf(request.getParameter("newsid"));
        NewsService newsService=new NewsService();
        try {
            newsService.DeleteNews(newsID);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("com.controller.ShowNewsServlet").forward(request,response);
    }

    @RequestMapping("/viewNew")
    public void viewNew(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int newsID=Integer.valueOf(request.getParameter("newsid"));
        NewsService newsService=new NewsService();
        try {
            News news=newsService.QueryIndividualNews(newsID);
            request.setAttribute("news",news);
            request.getRequestDispatcher("NewsDetail.jsp").forward(request,response);
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
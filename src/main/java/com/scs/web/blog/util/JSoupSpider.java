package com.scs.web.blog.util;

import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.Topic;
import com.scs.web.blog.entity.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mq_xu
 * @ClassName JSoupSpider
 * @Description JSoup爬虫，抓取数据
 * @Date 9:13 2019/11/7
 * @Version 1.0
 **/
public class JSoupSpider {
    private static Logger logger = LoggerFactory.getLogger(JSoupSpider.class);

    public static List<User> getUsers() {
        Document document = null;
        List<User> userList = new ArrayList<>(100);
        for (int i = 1; i <= 3; i++) {
            try {
                document = Jsoup.connect("https://www.jianshu.com/recommendations/users?utm_source=desktop&utm_medium=index-users&page=" + i).get();
            } catch (IOException e) {
                logger.error("连接失败");
            }
            Elements divs = document.getElementsByClass("col-xs-8");
            divs.forEach(div -> {
                Element wrapDiv = div.child(0);
                Element link = wrapDiv.child(0);
                Elements linkChildren = link.children();
                String introduction = linkChildren.get(2).text();
                if (!introduction.equals("") && introduction != null) {
                    User user = new User();
                    user.setMobile(DataUtil.getMobile());
                    user.setPassword(DataUtil.getPassword());
                    user.setGender(DataUtil.getGender());
                    user.setAvatar("https:" + linkChildren.get(0).attr("src"));
                    user.setNickname(linkChildren.get(1).text());
                    user.setIntroduction(introduction);
                    user.setBirthday(DataUtil.getBirthday());
                    user.setAddress(DataUtil.getAddress());
                    user.setCreateTime(DataUtil.getCreateTime());
                    userList.add(user);
                }

            });
        }
        return userList;
    }

    public static List<Article> getArticles() {
        Document document = null;
        List<Article> articleList = new ArrayList<>(100);
        for (int i = 1; i < 10; i++) {
            try {
                document = Jsoup.connect("https://www.jianshu.com/c/87b50a03a96e?order_by=top&count=50&page=" + i).get();
            } catch (IOException e) {
                logger.error("连接失败");
            }
            Elements divs = document.getElementsByClass("have-img");
            divs.forEach(div -> {
                String articleUrl = div.child(0).attr("href");
                Document document1 = null;
                try {
                    document1 = Jsoup.connect("https://www.jianshu.com" + articleUrl).get();
                } catch (IOException e) {
                    logger.error("连接失败");
                }
                Element articleElement = document1.getElementsByClass("_2rhmJa").first();
                Article article = new Article();
                article.setContent(articleElement.html());

                Elements elements = div.children();
                Element linkElement = elements.get(0);
                Element divElement = elements.get(1);
                article.setUserId(DataUtil.getUserId());
                article.setTitle(divElement.child(0).text());
                article.setSummary(divElement.child(1).text());
                String img = "https:" + linkElement.child(0).attr("src");
                int index = img.indexOf("?");
                article.setThumbnail(img.substring(0, index));
                Elements metaChildren = divElement.child(2).children();
                String comments = metaChildren.get(2).text();
                String likes = metaChildren.last().text();
                try {
                    article.setComments(Integer.parseInt(comments));
                    article.setLikes(Integer.parseInt(likes));
                } catch (NumberFormatException e) {
                    logger.error("格式转换异常");
                }
                article.setCreateTime(DataUtil.getCreateTime());
                articleList.add(article);
            });
        }
        System.out.println(articleList.size());
        return articleList;
    }

    public static List<Topic> getTopics() {
        List<Topic> topicList = new ArrayList<>(20);
        Document document = null;
//        for (int i = 1; i <= 3; i++) {
        try {
            document = Jsoup.connect("https://www.zhihu.com/special/all").get();
        } catch (IOException e) {
            logger.error("连接失败");
        }
        Elements lis = document.select(".SpecialListCard");
        System.out.println(lis.size());
//            lis.forEach(li -> {
//                Topic topic = new Topic();
//                topic.setName(li.child(1).child(0).text());
//                topic.setLogo(li.child(0).child(0).attr("src"));
//                String metaInfo = li.child(2).child(0).text();
//                String[] result = StringUtil.getDigital(metaInfo);
//                topic.setArticles(Integer.parseInt(result[0]));
//                topic.setFollows(Integer.parseInt(result[1]));
//                topicList.add(topic);
//            });
//        }
        return topicList;
    }

    public static void main(String[] args) {
        List<Topic> topicList = JSoupSpider.getTopics();
//        topicList.forEach(System.out::println);
    }
}

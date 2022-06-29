package top.gochiusa.newsapi;

import org.json.JSONObject;
import top.gochiusa.newsapi.dao.ArticleDao;
import top.gochiusa.newsapi.dao.Database;
import top.gochiusa.newsapi.entity.Article;
import top.gochiusa.newsapi.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/content/article")
public class ArticleContentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/text;charset=UTF-8");

        int page, limit;
        try {
            page = Integer.parseInt(req.getParameter("page"));
            limit = Integer.parseInt(req.getParameter("limit"));
            if (page < 0) {
                throw new NumberFormatException("page smaller than 0");
            }
        } catch (NumberFormatException e) {
            resp.getWriter().println(JsonUtil.errorMessage("参数非法"));
            return;
        }

        Database database = Database.getInstance();
        ArticleDao articleDao = database.getArticleDao();

        List<JSONObject> result = new ArrayList<>();
        int totalItems = articleDao.getArticleCount();
        int lastIndex = page * limit;

        if (lastIndex >= totalItems || limit < 1) {
            // 没有更多数据了，返回空数组
            resp.getWriter().println(JsonUtil.produceContentResponse(result, page, limit,
                    lastIndex + limit < totalItems));
            return;
        }
        List<Article> articles = articleDao.selectByLimitDescTime(lastIndex, limit);
        articles.forEach(article -> {
            JSONObject cache = new JSONObject();
            JsonUtil.bindArticle(cache, article);
            result.add(cache);
        });
        resp.getWriter().println(JsonUtil.produceContentResponse(result, page, limit,
                lastIndex + limit < totalItems));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

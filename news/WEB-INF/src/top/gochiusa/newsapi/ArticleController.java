package top.gochiusa.newsapi;

import top.gochiusa.newsapi.dao.Database;
import top.gochiusa.newsapi.entity.Article;
import top.gochiusa.newsapi.util.Contract;
import top.gochiusa.newsapi.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/article/upload")
public class ArticleController extends HttpServlet {

    private static final String USER_ID_IS_NULL = "缺少上传用户的id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/text;charset=UTF-8");
        resp.getWriter().println(JsonUtil.errorMessage("此接口仅支持POST"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/text;charset=UTF-8");

        String cache = req.getParameter("userId");
        if (cache == null || cache.isEmpty()) {
            resp.getWriter().println(JsonUtil.errorMessage(USER_ID_IS_NULL));
            return;
        }
        long userId;
        try {
            userId = Long.parseLong(cache);
        } catch (NumberFormatException e) {
            resp.getWriter().println(JsonUtil.errorMessage(USER_ID_IS_NULL));
            return;
        }

        String content = req.getParameter("content");
        String images = req.getParameter("images");
        String title = req.getParameter("title");
        boolean isSuccess = Database.getInstance().getArticleDao().insertArticle(
                new Article(userId, content, images, title)
        );
        if (isSuccess) {
            resp.getWriter().println("{\"code\": 1}");
        } else {
            resp.getWriter().println(JsonUtil.errorMessage(Contract.DATABASE_UPDATE_ERROR));
        }

    }
}

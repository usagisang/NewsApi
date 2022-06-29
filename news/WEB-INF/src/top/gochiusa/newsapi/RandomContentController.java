package top.gochiusa.newsapi;

import org.json.JSONArray;
import org.json.JSONObject;
import top.gochiusa.newsapi.dao.Database;
import top.gochiusa.newsapi.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/content/random")
public class RandomContentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/text;charset=UTF-8");

        int limit;
        String s = req.getParameter("limit");
        if (s == null) {
            limit = 5;
        } else {
            try {
                limit = Math.min(Math.max(Integer.parseInt(s), 2), 10);
            } catch (NumberFormatException e) {
                limit = 5;
            }
        }

        Database database = Database.getInstance();
        int articleCount = database.getArticleDao().getArticleCount();
        int videoCount = database.getVideoDao().getVideoCount();
        List<JSONObject> result = new ArrayList<>();

        // 数据太少
        if (articleCount + videoCount <= limit) {
            result.addAll(getAll(database));
        } else {
            int split = (int) (Math.random() * limit);
            // 确定如何划分数据
            int articles, videos;
            if (articleCount < split || articleCount < limit - split) {
                // 划分不平衡
                videos = limit - articleCount;
                articles = articleCount;
            } else if (videoCount < split || videoCount < limit - split) {
                // 划分不平衡
                videos = videoCount;
                articles = limit - videoCount;
            } else {
                articles = split;
                videos = limit - split;
            }
            database.getArticleDao().selectByLimit(
                    (int) (Math.random() * (articleCount - articles + 1)), articles
            ).forEach(article -> {
                JSONObject obj = new JSONObject();
                JsonUtil.bindArticle(obj, article);
                obj.put("type", 1);
                result.add(obj);
            });
            database.getVideoDao().selectByLimit(
                    (int) (Math.random() * (videoCount - videos + 1)), videos
            ).forEach(video -> {
                JSONObject obj = new JSONObject();
                JsonUtil.bindVideo(obj, video);
                obj.put("type", 2);
                result.add(obj);
            });
        }
        Collections.shuffle(result);
        JSONObject obj = new JSONObject();
        obj.put("code", 1);
        obj.put("result",  new JSONArray(result));
        resp.getWriter().println(obj);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private Set<JSONObject> getAll(Database database) {
        Set<JSONObject> jsonObjects = new HashSet<>();
        database.getArticleDao().selectAll().forEach(article -> {
            JSONObject obj = new JSONObject();
            JsonUtil.bindArticle(obj, article);
            obj.put("type", 1);
            jsonObjects.add(obj);
        });
        database.getVideoDao().selectAll().forEach(video -> {
            JSONObject obj = new JSONObject();
            JsonUtil.bindVideo(obj, video);
            obj.put("type", 2);
            jsonObjects.add(obj);
        });
        return jsonObjects;
    }
}

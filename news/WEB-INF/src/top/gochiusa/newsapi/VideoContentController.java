package top.gochiusa.newsapi;

import org.json.JSONObject;
import top.gochiusa.newsapi.dao.Database;
import top.gochiusa.newsapi.dao.VideoDao;
import top.gochiusa.newsapi.entity.Video;
import top.gochiusa.newsapi.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/content/video")
public class VideoContentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/text;charset=UTF-8");

        int page, limit;
        try {
            limit = Integer.parseInt(req.getParameter("limit"));
            page = Integer.parseInt(req.getParameter("page"));
            if (page < 0) {
                throw new NumberFormatException("page smaller than 0");
            }
        } catch (NumberFormatException e) {
            resp.getWriter().println(JsonUtil.errorMessage("参数非法"));
            return;
        }
        Database database = Database.getInstance();
        VideoDao videoDao = database.getVideoDao();

        List<JSONObject> result = new ArrayList<>();
        int totalItems = videoDao.getVideoCount();
        int lastIndex = page * limit;

        if (lastIndex >= totalItems || limit < 1) {
            // 没有更多数据了，返回空数组
            resp.getWriter().println(JsonUtil.produceContentResponse(result, page, limit,
                    lastIndex + limit < totalItems));
            return;
        }
        List<Video> videos = videoDao.selectByLimitDescTime(lastIndex, limit);
        videos.forEach(video -> {
            JSONObject cache = new JSONObject();
            JsonUtil.bindVideo(cache, video);
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

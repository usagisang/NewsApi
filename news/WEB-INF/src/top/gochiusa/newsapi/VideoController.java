package top.gochiusa.newsapi;

import top.gochiusa.newsapi.dao.Database;
import top.gochiusa.newsapi.entity.Video;
import top.gochiusa.newsapi.util.Contract;
import top.gochiusa.newsapi.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/video/upload")
public class VideoController extends HttpServlet {

    private static final String USER_ID_IS_NULL = "缺少上传用户的id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/text;charset=UTF-8");

        String cache = req.getParameter("userId");
        String title = req.getParameter("title");
        long userId;
        if (cache == null || cache.isEmpty()) {
            resp.getWriter().println(JsonUtil.errorMessage(USER_ID_IS_NULL));
            return;
        }
        try {
            userId = Long.parseLong(cache);
        } catch (NumberFormatException e) {
            resp.getWriter().println(JsonUtil.errorMessage(USER_ID_IS_NULL));
            return;
        }
        if (title == null) {
            resp.getWriter().println(JsonUtil.errorMessage("title不能为空"));
            return;
        }

        String videoUrl = req.getParameter("videoUrl");


        boolean isSuccess = Database.getInstance().getVideoDao().insertVideo(new Video(
                userId, videoUrl, title
        ));
        if (isSuccess) {
            resp.getWriter().println("{\"code\": 1}");
        } else {
            resp.getWriter().println(JsonUtil.errorMessage(Contract.DATABASE_UPDATE_ERROR));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

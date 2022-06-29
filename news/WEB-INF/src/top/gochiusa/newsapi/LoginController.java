package top.gochiusa.newsapi;

import org.json.JSONObject;
import top.gochiusa.newsapi.dao.Database;
import top.gochiusa.newsapi.entity.User;
import top.gochiusa.newsapi.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 获取请求的属性值
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        resp.setContentType("text/text;charset=UTF-8");

        User user = Database.getInstance().getUserDao().login(username, password);
        if (user != null) {
            JSONObject object = new JSONObject();
            object.put("code", 1);
            user.bindJSONObject(object);
            resp.getWriter().println(object);
        } else {
            resp.getWriter().println(JsonUtil.errorMessage("登录失败"));
        }
    }
}

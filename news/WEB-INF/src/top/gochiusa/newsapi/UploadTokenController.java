package top.gochiusa.newsapi;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.json.JSONObject;
import top.gochiusa.newsapi.util.Contract;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@WebServlet("/content/token")
public class UploadTokenController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/text;charset=UTF-8");
        JSONObject result = new JSONObject();

        try {
            String key = req.getParameter("key");

            Scanner scanner = new Scanner(new File(getServletContext().getRealPath("/")
                    + Contract.RELATIVE_RES_PATH + "token.json"));
            JSONObject settings = new JSONObject(scanner.nextLine());

            String accessKey = settings.getString("accessKey");
            String secretKey = settings.getString("secretKey");
            String bucket = settings.getString("bucket");

            StringMap putPolicy = new StringMap();
            putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
            long expireSeconds = 3600;

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket, key, expireSeconds, putPolicy);

            result.put("code", 1);
            result.put("token", upToken);
        } catch (Exception e) {
            result.put("code", 0);
            result.put("message", e.getMessage());
        }
        resp.getWriter().println(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

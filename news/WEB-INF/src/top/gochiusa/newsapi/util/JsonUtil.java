package top.gochiusa.newsapi.util;

import org.json.JSONArray;
import org.json.JSONObject;
import top.gochiusa.newsapi.entity.Article;
import top.gochiusa.newsapi.entity.Video;

import java.util.List;

public final class JsonUtil {

    public static String errorMessage(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", message);
        return jsonObject.toString();
    }

    public static String produceContentResponse(List<JSONObject> result, int page, int limit, boolean hasNext) {
        JSONObject outerObj = new JSONObject();
        outerObj.put("page", page);
        outerObj.put("limit", limit);
        outerObj.put("hasNext", hasNext);
        outerObj.put("code", 1);
        JSONArray resultArray = new JSONArray(result);
        outerObj.put("result", resultArray);
        return outerObj.toString();
    }

    public static void bindArticle(JSONObject obj, Article article) {
        obj.put("userId", article.getAuthorId());
        obj.put("images", article.getImages());
        obj.put("uploadTime", article.getUploadTime());
        obj.put("content", article.getContent());
        obj.put("title", article.getTitle());
    }

    public static void bindVideo(JSONObject obj, Video video) {
        obj.put("userId", video.getAuthorId());
        obj.put("videoUrl", video.getVideoUrl());
        obj.put("uploadTime", video.getUploadTime());
        obj.put("title", video.getTitle());
    }
}

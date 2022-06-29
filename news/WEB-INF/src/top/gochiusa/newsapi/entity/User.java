package top.gochiusa.newsapi.entity;

import org.json.JSONObject;

public class User {
    private String username;
    private String nickname;
    private long id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User(String username, String nickname, long id) {
        this.username = username;
        this.nickname = nickname;
        this.id = id;
    }

    public User() {
    }

    public void bindJSONObject(JSONObject obj) {
        obj.put("username", username);
        obj.put("userId", id);
        obj.put("nickname", nickname);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", id=" + id +
                '}';
    }
}

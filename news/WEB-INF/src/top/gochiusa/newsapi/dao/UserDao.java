package top.gochiusa.newsapi.dao;

import top.gochiusa.newsapi.entity.User;

public interface UserDao {

    User login(String username, String password);
}

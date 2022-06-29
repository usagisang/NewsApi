package top.gochiusa.newsapi.dao;

import top.gochiusa.newsapi.entity.User;
import top.gochiusa.simple.dao.JdbcTemplate;

public final class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbc;

    public UserDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public User login(String username, String password) {
        return jdbc.query(
                "SELECT * FROM user WHERE username=? AND password=?",
                ps -> {
                    ps.setString(1, username);
                    ps.setString(2, password);
                },
                rs -> {
                    if (rs.next()) {
                        return new User(
                                rs.getString("username"),
                                rs.getString("nickname"),
                                rs.getLong("userId")
                        );
                    } else {
                        return null;
                    }
                }
        );
    }
}

package top.gochiusa.newsapi.dao;

import top.gochiusa.simple.dao.ConnectionPool;
import top.gochiusa.simple.dao.JdbcTemplate;

public final class Database {
    private final JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private VideoDao videoDao;
    private ArticleDao articleDao;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoImpl(jdbcTemplate);
        }
        return userDao;
    }

    public ArticleDao getArticleDao() {
        if (articleDao == null) {
            articleDao = new ArticleDaoImpl(jdbcTemplate);
        }
        return articleDao;
    }

    public VideoDao getVideoDao() {
        if (videoDao == null) {
            videoDao = new VideoDaoImpl(jdbcTemplate);
        }
        return videoDao;
    }

    public static Database getInstance() {
        return Singleton.db;
    }

    private Database() {
        // 在Servlet内需要手动加载驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        jdbcTemplate = new JdbcTemplate(new ConnectionPool(
                "jdbc:mysql://localhost:3306/toutiao?serverTimezone=Asia/Shanghai&useSSL=false&characterEncoding=utf-8",
                "", "",
                2, 10
        ));
    }

    private static final class Singleton {
        private static final Database db = new Database();
    }
}

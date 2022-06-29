package top.gochiusa.newsapi.dao;

import top.gochiusa.newsapi.entity.Article;
import top.gochiusa.newsapi.util.JdbcUtil;
import top.gochiusa.newsapi.util.Utils;
import top.gochiusa.simple.dao.JdbcTemplate;
import top.gochiusa.simple.dao.base.RowMapper;

import java.util.List;

public class ArticleDaoImpl implements ArticleDao {

    private static final String ARTICLE_TABLE_NAME = "article";
    private final JdbcTemplate jdbc;

    public ArticleDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean insertArticle(Article article) {
        int record = jdbc.update(
                "INSERT INTO " + ARTICLE_TABLE_NAME +
                        " (userId, content, images, title, uploadTime) VALUES (?,?,?,?,?)",
                ps -> {
                    int index = 0;
                    ps.setLong(++index, article.getAuthorId());
                    ps.setString(++index, article.getContent());
                    ps.setString(++index, article.getImages());
                    ps.setString(++index, article.getTitle());
                    ps.setString(++index, Utils.getCurrentDate());
                }
        );
        return record > 0;
    }

    @Override
    public int getArticleCount() {
        return jdbc.query("SELECT COUNT(*) as nums FROM " + ARTICLE_TABLE_NAME,
                resultSet -> {
                    if (resultSet.next()) {
                        return resultSet.getInt("nums");
                    } else {
                        return 0;
                    }
                });
    }

    @Override
    public List<Article> selectByLimit(int startIndex, int limit) {
        // select * from article as a inner join
        // (select id from article order by id limit 0, 1) as b on a.id = b.id order by a.id;
        RowMapper<Article> rowMapper = (rs, i) -> new Article(
                rs.getLong("id"),
                rs.getLong("userId"),
                rs.getString("content"),
                rs.getString("images"),
                rs.getString("title"),
                rs.getString("uploadTime")
        );
        if (startIndex > 0) {
            return jdbc.query(JdbcUtil.createLimitSql(ARTICLE_TABLE_NAME,
                    startIndex - 1, limit, "id"), rowMapper);
        } else {
            return jdbc.query("SELECT * FROM " + ARTICLE_TABLE_NAME + " LIMIT " + limit, rowMapper);
        }
    }

    @Override
    public List<Article> selectAll() {
        return jdbc.query("SELECT * FROM " + ARTICLE_TABLE_NAME,
                (rs, i) -> new Article(
                        rs.getLong("id"),
                        rs.getLong("userId"),
                        rs.getString("content"),
                        rs.getString("images"),
                        rs.getString("title"),
                        rs.getString("uploadTime")
                ));
    }

    @Override
    public List<Article> selectByLimitDescTime(int startIndex, int limit) {
        RowMapper<Article> rowMapper = (rs, i) -> new Article(
                rs.getLong("id"),
                rs.getLong("userId"),
                rs.getString("content"),
                rs.getString("images"),
                rs.getString("title"),
                rs.getString("uploadTime")
        );
        if (startIndex > 0) {
            return jdbc.query(JdbcUtil.createLimitSqlDesc(ARTICLE_TABLE_NAME,
                    startIndex - 1, limit, "uploadTime"), rowMapper);
        } else {
            return jdbc.query("SELECT * FROM " + ARTICLE_TABLE_NAME + " ORDER BY uploadTime DESC LIMIT "
                    + limit, rowMapper);
        }
    }
}

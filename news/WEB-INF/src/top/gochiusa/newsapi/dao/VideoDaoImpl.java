package top.gochiusa.newsapi.dao;

import top.gochiusa.newsapi.entity.Video;
import top.gochiusa.newsapi.util.JdbcUtil;
import top.gochiusa.newsapi.util.Utils;
import top.gochiusa.simple.dao.JdbcTemplate;
import top.gochiusa.simple.dao.base.RowMapper;

import java.util.List;

public class VideoDaoImpl implements VideoDao {
    private final JdbcTemplate jdbc;

    private static final String VIDEO_TABLE_NAME = "video";

    public VideoDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean insertVideo(Video video) {
        int record = jdbc.update(
                "INSERT INTO " + VIDEO_TABLE_NAME + " (videoUrl, userId, uploadTime, title) VALUES (?,?,?,?)",
                ps -> {
                    int index = 0;
                    ps.setString(++index, video.getVideoUrl());
                    ps.setLong(++index, video.getAuthorId());
                    ps.setString(++index, Utils.getCurrentDate());
                    ps.setString(++index, video.getTitle());
                }
        );
        return record > 0;
    }

    @Override
    public List<Video> selectByLimit(int startIndex, int limit) {
        RowMapper<Video> rowMapper = (rs, i) -> new Video(
                rs.getLong("id"),
                rs.getLong("userId"),
                rs.getString("videoUrl"),
                rs.getString("uploadTime"),
                rs.getString("title")
        );
        if (startIndex > 0) {
            return jdbc.query(JdbcUtil.createLimitSql(VIDEO_TABLE_NAME, startIndex - 1,
                    limit, "id"), rowMapper);
        } else {
            return jdbc.query("SELECT * FROM " + VIDEO_TABLE_NAME + " LIMIT " + limit, rowMapper);
        }
    }

    @Override
    public int getVideoCount() {
        return jdbc.query("SELECT COUNT(*) as nums FROM " + VIDEO_TABLE_NAME,
                resultSet -> {
                    if (resultSet.next()) {
                        return resultSet.getInt("nums");
                    } else {
                        return 0;
                    }
                });
    }

    @Override
    public List<Video> selectAll() {
        return jdbc.query("SELECT * FROM " + VIDEO_TABLE_NAME,
                (rs, i) -> new Video(
                        rs.getLong("id"),
                        rs.getLong("userId"),
                        rs.getString("videoUrl"),
                        rs.getString("uploadTime"),
                        rs.getString("title")
                ));
    }

    @Override
    public List<Video> selectByLimitDescTime(int startIndex, int limit) {
        RowMapper<Video> rowMapper = (rs, i) -> new Video(
                rs.getLong("id"),
                rs.getLong("userId"),
                rs.getString("videoUrl"),
                rs.getString("uploadTime"),
                rs.getString("title")
        );
        if (startIndex > 0) {
            return jdbc.query(JdbcUtil.createLimitSqlDesc(VIDEO_TABLE_NAME, startIndex - 1,
                    limit, "uploadTime"), rowMapper);
        } else {
            return jdbc.query("SELECT * FROM " + VIDEO_TABLE_NAME + " ORDER BY uploadTime DESC LIMIT " +
                    limit, rowMapper);
        }
    }
}

package top.gochiusa.newsapi.dao;

import top.gochiusa.newsapi.entity.Video;

import java.util.List;

public interface VideoDao {
    boolean insertVideo(Video video);

    List<Video> selectByLimit(int startIndex, int limit);

    int getVideoCount();

    List<Video> selectAll();

    List<Video> selectByLimitDescTime(int startIndex, int limit);
}

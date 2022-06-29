package top.gochiusa.newsapi.dao;

import top.gochiusa.newsapi.entity.Article;

import java.util.List;

public interface ArticleDao {

    boolean insertArticle(Article article);

    int getArticleCount();

    List<Article> selectByLimit(int startIndex, int limit);

    List<Article> selectAll();

    List<Article> selectByLimitDescTime(int startIndex, int limit);
}

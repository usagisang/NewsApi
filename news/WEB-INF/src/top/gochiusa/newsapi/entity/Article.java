package top.gochiusa.newsapi.entity;

public class Article {
    private long id;
    private long authorId;
    private String content;
    private String images;
    private String title;

    private String uploadTime;

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Article() {
    }

    public Article(long authorId, String content, String images, String title) {
        this(0L, authorId, content, images, title);
    }

    public Article(long id, long authorId, String content, String images, String title) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.images = images;
        this.title = title;
    }

    public Article(long id, long authorId, String content, String images, String title, String uploadTime) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.images = images;
        this.title = title;
        this.uploadTime = uploadTime;
    }
}

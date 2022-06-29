package top.gochiusa.newsapi.entity;

public class Video {
    private long id;
    private long authorId;
    private String videoUrl;
    private String uploadTime;
    private String title;

    public Video(long id, long authorId, String videoUrl, String uploadTime, String title) {
        this.id = id;
        this.authorId = authorId;
        this.videoUrl = videoUrl;
        this.uploadTime = uploadTime;
        this.title = title;
    }

    public Video(long authorId, String videoUrl, String title) {
        this.authorId = authorId;
        this.videoUrl = videoUrl;
        this.title = title;
    }

    public Video() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}

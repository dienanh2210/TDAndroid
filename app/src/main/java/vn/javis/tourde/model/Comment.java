package vn.javis.tourde.model;

import java.util.Date;

public class Comment {

    private String userName;
    private String userAvatar;
    private String content;
    private Date postDate;

    public Comment(String userName, String userAvatar, String content, Date postDate) {
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.content = content;
        this.postDate = postDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getContent() {
        return content;
    }

    public Date getPostDate() {
        return postDate;
    }
}

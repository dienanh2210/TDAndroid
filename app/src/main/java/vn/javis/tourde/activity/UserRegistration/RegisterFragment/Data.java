package vn.javis.tourde.activity.UserRegistration.RegisterFragment;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    private String title;
    private List<String> content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
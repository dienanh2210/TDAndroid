package vn.javis.tourde.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable {

    private String title;
    private List<String> content;
    private boolean isMarked;
    private int positionMarked;

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public int getPositionMarked() {
        return positionMarked;
    }

    public void setPositionMarked(int positionMarked) {
        this.positionMarked = positionMarked;
    }

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
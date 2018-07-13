package vn.javis.tourde.model;

import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup;

import java.util.List;


public class MultiCheckGenre extends MultiCheckExpandableGroup {

  private boolean isMonth = false;
  public MultiCheckGenre(String title, List<String> items) {
    super(title, items);
  }

  public MultiCheckGenre(String title, List<String> items, boolean isMonth) {
    super(title, items);
    this.isMonth = isMonth;
  }

  public boolean isMonth() {
    return  isMonth;
  }

}


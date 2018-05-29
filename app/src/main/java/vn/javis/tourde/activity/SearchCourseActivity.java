package vn.javis.tourde.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.javis.tourde.R;
import vn.javis.tourde.fragment.PrefectureSearchFragment;
import vn.javis.tourde.fragment.RegisterFragment;
import vn.javis.tourde.fragment.SearchCourseFragment;

public class SearchCourseActivity extends BaseActivity {



    private List<String> mListContent = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        openPage(new SearchCourseFragment(), true, true);
    }

    public void openPage(android.support.v4.app.Fragment fragment, boolean isBackStack, boolean isAnimation) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        if(isAnimation)
            tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        tx.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (isBackStack)
            tx.addToBackStack(null);
        tx.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("onBackPressed", "true");
    }

    public void setmLstContent(List<String> mLstContent) {
        this.mListContent = mLstContent;
        Log.i("seach activity 51: ", this.mListContent.toString());
    }
    public List<String> getmListContent() {
        return mListContent;
    }
    public void onBackCLickToList(HashMap map) {

        Intent intent = new Intent(this, CourseListActivity.class);
        intent.putExtra("listContent", mListContent.toArray());
        intent.putExtra("searchValue", map);
        intent.putExtra("searching", "true");
        startActivity(intent);
    }

}

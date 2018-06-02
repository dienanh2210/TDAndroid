package vn.javis.tourde.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class YourScrollableViewPager extends ViewPager {
    private static final int MATCH_PARENT = 1073742592;

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    private int currentPageNumber;
    private View mCurrentView;
    private int pageCount;

    public YourScrollableViewPager(Context context) {
        super(context);
        prepareUI();
    }

    public YourScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        prepareUI();
    }

    private void prepareUI() {
        setOffscreenPageLimit(pageCount);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        if (mCurrentView == null) {
            if (getChildCount() != 0) {
                View child = getChildAt(currentPageNumber);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height) height = h;
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mCurrentView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int h = mCurrentView.getMeasuredHeight();
        if (h > height) height = h;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("WrongCall")
    public void onPrevPage() {
        onMeasure(MATCH_PARENT, 0);
    }

    @SuppressLint("WrongCall")
    public void onNextPage() {
        onMeasure(MATCH_PARENT, 0);
    }
    public void measureCurrentView(View currentView) {
        mCurrentView = currentView;
        requestLayout();
    }

    public int measureFragment(View view) {
        if (view == null)
            return 0;

        view.measure(0, 0);
        return view.getMeasuredHeight();
    }
}

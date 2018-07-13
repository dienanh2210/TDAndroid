package vn.javis.tourde.customlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vn.javis.tourde.R;


/**
 * Project : TourDe
 * Author :QuanPV
 * Date : 12/04/2018
 * Description :
 */
public class TourDeTabLayout extends RelativeLayout {

    public interface SCTabChangeListener {
        void onTabChange(int index, boolean isScroll);
    }

    float tabHeight, tabMarginLeft, tabMarginRight, tabTextSize;
    CharSequence[] tabTitles;

    private int tabActiveColor, tabTextColor, tabTextActiveColor, tabNormalColor, selectIndex;

    private LinearLayout mainTab;
    private ArrayList<View> subViews;
    private Context mContext;


    public void setOnTabChangeListener(SCTabChangeListener listener) {
        this.listener = listener;
    }

    public SCTabChangeListener listener;

    public TourDeTabLayout(Context context) {
        super(context);
        mContext = context;
        initUI();

    }

    public TourDeTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initUI();
        initData(attrs);
    }

    public TourDeTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initUI();
        initData(attrs);
    }

    private void initUI() {
        inflate(getContext(), R.layout.widget_tab_layout, this);
        mainTab = findViewById(R.id.mainTab);
    }


    private void initData(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TourDeTabLayout);
        tabHeight = typedArray.getDimension(R.styleable.TourDeTabLayout_tabHeight, 50);
        tabMarginLeft = typedArray.getDimension(R.styleable.TourDeTabLayout_tabMarginLeft, 0);
        tabMarginRight = typedArray.getDimension(R.styleable.TourDeTabLayout_tabMarginRight, 0);
        selectIndex = typedArray.getInt(R.styleable.TourDeTabLayout_tabSelectIndex, 0);
        tabTextColor = typedArray.getInt(R.styleable.TourDeTabLayout_tabTextColor, getResources().getColor(R.color.color_text_tab));
        tabActiveColor = typedArray.getInt(R.styleable.TourDeTabLayout_tabActiveColor, getResources().getColor(R.color.colorPrimary));
        tabNormalColor = typedArray.getInt(R.styleable.TourDeTabLayout_tabNormalColor, getResources().getColor(R.color.colorPrimary));
        tabTextActiveColor = typedArray.getInt(R.styleable.TourDeTabLayout_tabTextActiveColor, getResources().getColor(R.color.color_text_tab));
        tabTextColor = typedArray.getInt(R.styleable.TourDeTabLayout_tabTextColor, getResources().getColor(R.color.color_text_tab));
        tabTextSize = typedArray.getDimensionPixelSize(R.styleable.TourDeTabLayout_tabTextSize, 10);
        try {
            CharSequence[] entries = typedArray.getTextArray(R.styleable.TourDeTabLayout_tabItems);
            if (entries != null) {
                tabTitles = entries;
            }
        } finally {
            typedArray.recycle();
        }

        LinearLayout.LayoutParams paramMain = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramMain.rightMargin = (int) tabMarginRight;
        paramMain.leftMargin = (int) tabMarginLeft;
        mainTab.setLayoutParams(paramMain);


        subViews = new ArrayList<>();
        int index = 0;
        for (CharSequence tab : tabTitles) {

            View subView = inflate(getContext(), R.layout.tab_content, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, (int) tabHeight);
            params.weight = 1;

            subView.setLayoutParams(params);
            TextView title = subView.findViewById(R.id.tv_content_tab);
            title.setText(tab);

            title.setGravity(Gravity.CENTER);
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
            title.setText(tab);
            title.setTextColor(tabTextColor);
            mainTab.addView(subView);

            View divide = new View(mContext);

            LinearLayout.LayoutParams param_divide = new LinearLayout.LayoutParams((int) (TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 1.0f, mContext.getResources().getDisplayMetrics())), LinearLayout.LayoutParams.MATCH_PARENT);
            divide.setLayoutParams(param_divide);
            divide.setBackgroundColor(getResources().getColor(R.color.Gray));
            if (index < tabTitles.length - 1)
                mainTab.addView(divide);

            subViews.add(subView);
            final int tempIndex = index;
            subView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    highLightTab(tempIndex);
                    if (listener != null)
                        listener.onTabChange(tempIndex, true);
                }
            });


            index++;
        }
        highLightTab(selectIndex);
    }

    public void highLightTab(int index) {
        selectIndex = index;
        if (subViews.size() > 0) {
            for (int i = 0; i < subViews.size(); i++) {
                View title = subViews.get(i);
                if (i == selectIndex) {
                    if (title instanceof TextView) {
                        ((TextView) title).setTextColor(tabTextActiveColor);
                        ((TextView) title).setTypeface(null, Typeface.BOLD);
                    } else {
                        ((TextView) title.findViewById(R.id.tv_content_tab)).setTextColor(tabTextActiveColor);
                        ((TextView) title.findViewById(R.id.tv_content_tab)).setTypeface(null, Typeface.BOLD);
                        title.findViewById(R.id.v_divide_bottom).setVisibility(GONE);
                    }
                    title.setBackgroundColor(tabActiveColor);
                } else {
                    if (title instanceof TextView)
                    {   ((TextView) title).setTextColor(tabTextColor);
                    ((TextView) title).setTypeface(null, Typeface.NORMAL);
                    }
                    else {
                        ((TextView) title.findViewById(R.id.tv_content_tab)).setTextColor(tabTextColor);
                        ((TextView) title.findViewById(R.id.tv_content_tab)).setTypeface(null, Typeface.NORMAL);
                        title.findViewById(R.id.v_divide_bottom).setVisibility(VISIBLE);
                    }
                    title.setBackgroundColor(tabNormalColor);
                }
            }
        }
    }

}

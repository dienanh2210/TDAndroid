package vn.javis.tourde.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListCommentAdapter;
import vn.javis.tourde.apiservice.CommentsAPI;
import vn.javis.tourde.model.Comment;
import vn.javis.tourde.model.Review;

public class TabCommentFragment extends BaseFragment {

    @BindView(R.id.comment_recylerview)
    RecyclerView lstCommentRecyleView;
    @BindView(R.id.btn_post_comment)
    ImageButton btnPostComment;
    ListCommentAdapter listCommentAdapter;
    CourseListActivity mActivity;
    List<Review> listReview;

    public static TabCommentFragment instance(List<Review> listReview) {
        TabCommentFragment fragment = new TabCommentFragment();
        fragment.listReview = listReview;
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lstCommentRecyleView.setLayoutManager(layoutManager);
        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showCommentPost();
            }
        });
        setRecyler();
    }


    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.tab_comment, container, false);
    }

    public void setListReview(List<Review> listReview) {
        this.listReview = listReview;
    }

    public void setRecyler() {
        listCommentAdapter = new ListCommentAdapter(listReview, getActivity());
        lstCommentRecyleView.setAdapter(listCommentAdapter);
    }
}

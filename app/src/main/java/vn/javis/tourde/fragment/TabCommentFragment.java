package vn.javis.tourde.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import vn.javis.tourde.adapter.ListCommentAdapter;
import vn.javis.tourde.model.Comment;

public class TabCommentFragment extends BaseFragment{

    @BindView(R.id.comment_recylerview)
    RecyclerView lstCommentRecyleView;

    @BindView(R.id.btn_post_comment)
    ImageButton btnPostComment;

    ListCommentAdapter listCommentAdapter;
    @Override
    public void onStart() {
        super.onStart();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lstCommentRecyleView.setLayoutManager(layoutManager);

        List<Comment> list_courses =new ArrayList<>();
        listCommentAdapter = new ListCommentAdapter(list_courses, getActivity());
        lstCommentRecyleView.setAdapter(listCommentAdapter);

        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.tab_comment, container, false);
    }


}

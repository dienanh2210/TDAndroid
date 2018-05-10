package vn.javis.tourde.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.utils.ProcessDialog;

public class CourseDriveFragment extends BaseFragment {
    @BindView(R.id.btn_start)
    ImageView btnStart;
    @BindView(R.id.btn_back_drive)
    ImageButton btnBack;
    @BindView(R.id.btn_back_detail)
    Button getBtnBackDetail;
    @BindView(R.id.btn_show_map)
    Button btnMapWay;
    CourseListActivity mAcitivity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAcitivity = (CourseListActivity) getActivity();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcessDialog.showDialogConfirm(getContext(), "","走行開始しますか？", new ProcessDialog.OnActionDialogClickOk() {
                    @Override
                    public void onOkClick() {
                       mAcitivity.ShowCountDown();
                    }
                });
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAcitivity.onBackPressed();
            }
        });
        getBtnBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAcitivity.onBackPressed();
            }
        });

    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_course_drive, container, false);
    }

}

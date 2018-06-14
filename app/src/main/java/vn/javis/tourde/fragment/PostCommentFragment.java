package vn.javis.tourde.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.LoginSNSActivity;
import vn.javis.tourde.apiservice.CommentsAPI;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.ProcessDialog;

public class PostCommentFragment extends BaseFragment {

    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;
    @BindView(R.id.edt_text)
    EditText edt_text;
    @BindView(R.id.btn_choose)
    Button btnPostComment;
    @BindView(R.id.tv_back_basicInfo)
    TextView tvBackToDetail;

    private Dialog dialog;
    CourseListActivity mActivity;
    int courseID = 0;

    private boolean isReached = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mActivity = (CourseListActivity) getActivity();
        courseID = getArguments().getInt(CourseListActivity.COURSE_DETAIL_ID);
        mRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRatingBar.setRating(0);
            }
        });
    //    edt_text.setInputType(InputType.TYPE_CLASS_TEXT);
      //  edt_text.setImeActionLabel("Done", EditorInfo.IME_ACTION_DONE);

        edt_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                return false;
            }

        });


        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment();
            }
        });
        tvBackToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.ShowCourseDetailByTab(1);
            }
        });
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.post_comment_fragment, container, false);
    }

    void postComment() {
        final String token = LoginFragment.getmUserToken();
        if(token !="" && token !=null)
        {
            final int rating =(int) mRatingBar.getRating();
            final String comment = edt_text.getText().toString();
            if (comment != null && comment != "") {
                //check commented this course
                CommentsAPI.postCheckCourseReview(token, courseID, new ServiceCallback() {
                    @Override
                    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException
                    {

                        JSONObject jsonObject = (JSONObject) response;
                        // Log.d("logout", jsonObject.toString());
                        Log.d("postCommentFrag 107", "" + jsonObject);
                        if (jsonObject.has("success")) {
                            String reviewed = jsonObject.get("reviewed").toString();
                            if (reviewed == "true") {
                                ProcessDialog.showDialogConfirm(mActivity,R.layout.dialog_confirm2, "", "前回のコメントと評価は上書きされますがよろしいですか？", new ProcessDialog.OnActionDialogClickOk() {
                                    @Override
                                    public void onOkClick()
                                    {
                                        CommentsAPI.postReviewCourse(token, courseID, rating, comment, new ServiceCallback() {
                                            @Override
                                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                                //post comment success return to current page
                                             //   mActivity.onBackPressed();
                                                mActivity.ShowCourseDetailByTab(1);

                                            }

                                            @Override
                                            public void onError(VolleyError error) {
                                                Log.i("postcomment error 123: ", error.getMessage());
                                            }
                                        });
                                    }
                                });
                            } else {
                                CommentsAPI.postReviewCourse(token, courseID, rating, comment, new ServiceCallback() {
                                    @Override
                                    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                        //post comment success return to current page
                                        mActivity.onBackPressed();
                                        Log.i("post comment 118", response.toString());
                                    }

                                    @Override
                                    public void onError(VolleyError error) {
                                        Log.i("postcomment error 123: ", error.getMessage());
                                    }
                                });
                            }
                        } else {

                        }

                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });

            }
        }
        else {
            ProcessDialog.showDialogConfirm(mActivity,R.layout.dialog_confirm2, R.color.colorTextF4F6F8,R.color.SkyBlue,"", "ログインしてください", new ProcessDialog.OnActionDialogClickOk() {
                @Override
                public void onOkClick() {
                    Intent intent = new Intent(mActivity, LoginSNSActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
}

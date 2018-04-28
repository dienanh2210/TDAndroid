package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.apiservice.CommentsAPI;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;

public class PostCommentFragment extends BaseFragment {

    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;
    @BindView(R.id.edt_text)
    EditText edt_text;
    @BindView(R.id.btn_choose)
    Button btnPostComment;
    @BindView(R.id.tv_back_basicInfo)
    TextView tvBackToDetail;

    CourseListActivity mActivity;


    private boolean isReached = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mActivity = (CourseListActivity) getActivity();
        mRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRatingBar.setRating(0);
            }
        });

        edt_text.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void afterTextChanged(Editable s) {

                for (int i = s.length() - 1; i >= 0; i--) {
                    if (s.charAt(i) == '\n') {
                        s.delete(i, i + 1);
                        return;
                    }
                }
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
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.post_comment_fragment, container, false);
    }

    void postComment() {
        String token = LoginFragment.getmUserToken();
        int course_id = mActivity.getmCourseID();
        int rating = mRatingBar.getNumStars();
        String comment = edt_text.getText().toString();
        if (comment != null) {
            CommentsAPI.postReviewCourse(token, course_id, rating, comment, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                    //post comment success return to current page
                    mActivity.onBackPressed();
                    Log.i("post comment",response.toString());
                }

                @Override
                public void onError(VolleyError error) {
                    Log.i("post comment error: ", error.getMessage());
                }
            });
        }
    }
}

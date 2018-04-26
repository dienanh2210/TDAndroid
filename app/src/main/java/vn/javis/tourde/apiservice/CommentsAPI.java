package vn.javis.tourde.apiservice;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.javis.tourde.model.Comment;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.services.TourDeService;
import vn.javis.tourde.volley.VolleyCustomRequest;

public class CommentsAPI {
    private static CommentsAPI instance;

    public static CommentsAPI getInstance() {
        if(instance==null)
            instance = new CommentsAPI();
        return instance;
    }

    public List<Comment> getCommentsForTest() {
        List<Comment> comments = new ArrayList<>();
        Comment model1 = new Comment("月曜ライダー", 1, 3, "志摩周遊の拠点となる近鉄志摩線「鵜方」駅。 特急電車の停車駅である、アクセスの上でも利便性が高いのに加え、観光案内所やコンビニも駅構内に設置されており、情報収集や補給物の調達にも");
        Comment model2 = new Comment("水曜ライダー", 1, 4, "志摩周遊の拠点となる近鉄志摩線「鵜方」駅。 特急電車の停車駅である、アクセスの上でも利便性が高いのに加え、観光案内所やコンビニも駅構内に設置されており、情報収集や補給物の調達にも");
        Comment model3 = new Comment("木曜ライダー", 1, 2, "志摩周遊の拠点となる近鉄志摩線「鵜方」駅。 特急電車の停車駅である、アクセスの上でも利便性が高いのに加え、観光案内所やコンビニも駅構内に設置されており、情報収集や補給物の調達にも");
        comments.add(model1);
        comments.add(model2);
        comments.add(model3);
        return comments;
    }
    public static void postReviewCourse(String token, int course_id, int rating, String comment, ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("course_id", String.valueOf(course_id));
        params.put("rating", String.valueOf(rating));
        params.put("comment", comment);
        String url = ApiEndpoint.BASE_URL + ApiEndpoint.POST_CREATE_ACCOUNT;
        TourDeService.postWithAuth(ApiEndpoint.POST_REVIEW_COURSE, params, callback);
    }
}

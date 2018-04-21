package vn.javis.tourde.apiservice;

import java.util.ArrayList;
import java.util.List;

import vn.javis.tourde.model.Comment;

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
}

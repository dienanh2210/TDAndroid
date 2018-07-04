package vn.javis.tourde.utils;

import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetUsersShowAPICustomService {
    @GET("/1.1/users/show.json")
    Call<User> showProfile(@Query("user_id") long userId);



}

package vn.javis.tourde.activity;

import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import vn.javis.tourde.activity.BasePresenter;

public interface LoginPresenter extends BasePresenter{

    void onSuccess();

    void onCancel();

    void onError(Exception e);
}

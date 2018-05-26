package vn.javis.tourde.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.model.Account;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        if (SharedPreferencesUtils.getInstance(this).getStringValue("Email").equals("")) {
            if (SharedPreferencesUtils.getInstance(this).getStringValue("Tutorial").equals(""))
                changeActivity();
            else
                changeCourseListPage();
        }
        else
            changeCourseListActivity();


    }

    void changeActivity() {
        Intent intent = new Intent(this, ViewPageActivity.class);
        startActivity(intent);
    }


    void changeCourseListPage(){
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);
    }

    void changeCourseListActivity() {
       loginToApp();
        //openPage(new LoginFragment());
    }

    public void loginToApp() {
        final boolean gender = false;
        String email = SharedPreferencesUtils.getInstance(getApplicationContext()).getStringValue("Email");
        String password = SharedPreferencesUtils.getInstance(getApplicationContext()).getStringValue("Pass");
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            LoginAPI.loginEmail(email, password, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) {
                    JSONObject jsonObject = (JSONObject) response;
                    if (jsonObject.has("success")) {
//                        Intent intent = new Intent( getActivity(), MenuPageLoginActivity.class );
//                        startActivity( intent );
                        Intent intent = new Intent(MainActivity.this,CourseListActivity.class);
                        startActivity(intent);
                        intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                        MainActivity.this.setResult(Activity.RESULT_OK, intent);
                        //     getActivity().finish();
                        if (jsonObject.has("token")) {
                            try {
                                LoginFragment.setmUserToken(jsonObject.getString("token"));
                                getAccount();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        //Log.d(edt_emaillogin.toString(), edt_passwordlogin.toString() + "error");
                    }

                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
    }

    public void getAccount() {

        LoginAPI.pushToken(LoginFragment.getmUserToken(), new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (jsonObject.has("account_id")) {
                    setmAccount(Account.getData(jsonObject.toString()));
                }
            }

            @Override
            public void onError(VolleyError error) {
            }
        });
    }

    public static Account getmAccount() {
        return getmAccount();
    }

    public static void setmAccount(Account mAccount) {
        LoginFragment.setmAccount(mAccount);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    public void openPage(Fragment fragment) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tx.addToBackStack(null);
        tx.commit();

    }


}






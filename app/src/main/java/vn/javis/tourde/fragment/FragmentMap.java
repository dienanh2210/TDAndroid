package vn.javis.tourde.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;

public class FragmentMap extends BaseFragment{
    View mView;
    CourseListActivity mActivity;

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.map_fragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.navitime, R.id.googleMap})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.navitime:
                String packageName = "com.navitime.local.navitime";
                // String packageName = "NAVITIME: 地図・ルート検索";
                launchNewActivity(getContext(),packageName);
                break;
            case R.id.googleMap:
                String uri = "http://maps.google.com/maps?daddr=" + 12f + "," + 2f + " (" + "Where the party is at" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                try
                {
                    startActivity(intent);
                }
                catch(ActivityNotFoundException ex)
                {
                    try
                    {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(unrestrictedIntent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        Toast.makeText(getContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.resume:

                break;
        }
    }
    public void launchNewActivity(Context context, String packageName) {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
            intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        }
        if (intent == null) {
            try {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.navitime.local.navitime&hl=ja" + packageName));
                context.startActivity(intent);
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.navitime.local.navitime&hl=ja " + packageName)));

            }
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}

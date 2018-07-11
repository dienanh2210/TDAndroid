package vn.javis.tourde.fragment;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.LineString;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.MainActivity;
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.model.SpotData;

public class FragmentMap extends BaseFragment implements OnMapReadyCallback {
    View mView;
    CourseListActivity mActivity;
    private GoogleMap mMap;
    String mapUrl;
    List<Spot> list_spot;

    public static FragmentMap instance(List<Spot> list_spot) {
        FragmentMap frg = new FragmentMap();
        frg.list_spot = list_spot;
        return frg;
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate( R.layout.map_fragment, container, false );
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        mapUrl = mActivity.getMapUrl();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace( R.id.map, SupportMapFragment.newInstance(), "map" );
        transaction.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentByTag( "map" );
        mapFragment.getMapAsync( this );
    }

    @OnClick({R.id.navitime, R.id.googleMap})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.navitime:
                String packageName = "com.navitime.local.navitime";
                launchNewActivity( getContext(), packageName );
                break;
            case R.id.googleMap:
                try {
                    String uri1 = "";
                    String   uri = "" ;
                    for (Spot spot : list_spot) {
                        float latitude = Float.parseFloat( spot.getLatitude() );
                        float longtitude = Float.parseFloat( spot.getLongitude() );
                        uri1 += "/" + latitude + "," + longtitude;
//                        uri += "/" +  mActivity.getLatitudeNetWork() + "," + mActivity.getLongitudeNetWork();
                        Log.i( "googlemap", uri1 );
                    }
                    Intent intent = new Intent( android.content.Intent.ACTION_VIEW, Uri.parse( "https://www.google.com/maps/dir" + uri1 ) );
//                   String uri = String.format( "geo:%f,%f?q=%f,%f(Quanpv+hehe))", 0f, 0f, mActivity.getLatitudeNetWork(), mActivity.getLongitudeNetWork() );
//                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    Log.i( "URI", "https://www.google.com/maps/dir" + uri1 );
                    intent.setPackage( "com.google.android.apps.maps" );
                    startActivity( intent );
                } catch (ActivityNotFoundException ex) {
                 //   Toast.makeText( getContext(), "Please install a maps application", Toast.LENGTH_LONG ).show();
                }


                break;
            case R.id.resume:

                break;
        }
    }

    private void navigate(List<Spot> list_spot) {
        String uri = "";
        for (Spot latLng : list_spot) {
            if (TextUtils.isEmpty( uri )) {
                uri = String.format(
                        "http://maps.google.com/maps?saddr=%s, %s",
                        String.valueOf( latLng.getLatitude() ).replace( ",", "." ),
                        String.valueOf( latLng.getLongitude() ).replace( ",", "." )
                );
            } else {
                if (!uri.contains( "&daddr" )) {
                    uri += String.format(
                            "&daddr=%s, %s",
                            String.valueOf( latLng.getLatitude() ).replace( ",", "." ),
                            String.valueOf( latLng.getLongitude() ).replace( ",", "." )
                    );
                } else {
                    uri += String.format(
                            "+to:%s, %s",
                            String.valueOf( latLng.getLatitude() ).replace( ",", "." ),
                            String.valueOf( latLng.getLongitude() ).replace( ",", "." )
                    );
                }
            }
        }
        Intent intent = new Intent( android.content.Intent.ACTION_VIEW, Uri.parse( uri ) );
        startActivity( intent );
    }

    public void launchNewActivity(Context context, String packageName) {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
            intent = context.getPackageManager().getLaunchIntentForPackage( packageName );
        }
        if (intent == null) {
            try {
                intent = new Intent( Intent.ACTION_VIEW );
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                intent.setData( Uri.parse( "https://play.google.com/store/apps/details?id=com.navitime.local.navitime&hl=ja" + packageName ) );
                context.startActivity( intent );
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "https://play.google.com/store/apps/details?id=com.navitime.local.navitime&hl=ja " + packageName ) ) );

            }
        } else {
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity( intent );

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        startDemo();
    }

    private void startDemo() {
        try {
            mMap = getMap();
            //retrieveFileFromResource();
            retrieveFileFromUrl();
        } catch (Exception e) {
            Log.e( "Exception caught", e.toString() );
        }
    }

    private void retrieveFileFromUrl() {
        if (mapUrl != null)
            new DownloadKmlFile( mapUrl ).execute();
    }

    private void moveCameraToKml(KmlLayer kmlLayer) {
        try {

            //Retrieve the first container in the KML layer
            KmlContainer container = kmlLayer.getContainers().iterator().next();
            //Retrieve a nested container within the first container
            //container = container.getContainers().iterator().next();
            //Retrieve the first placemark in the nested container
            KmlPlacemark placemark = container.getPlacemarks().iterator().next();
            //Retrieve a polygon object in a placemark
            LineString polygon = (LineString) placemark.getGeometry();
            //Create LatLngBounds of the outer coordinates of the polygon
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng latLng : polygon.getGeometryObject()) {
                builder.include( latLng );
            }

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            getMap().moveCamera( CameraUpdateFactory.newLatLngBounds( builder.build(), width, height, 1 ) );
        } catch (Exception e) {

        }
    }

    private class DownloadKmlFile extends AsyncTask<String, Void, byte[]> {
        private final String mUrl;

        public DownloadKmlFile(String url) {
            mUrl = url;
        }

        protected byte[] doInBackground(String... params) {
            try {
                InputStream is = new URL( mUrl ).openStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[16384];
                while ((nRead = is.read( data, 0, data.length )) != -1) {
                    buffer.write( data, 0, nRead );
                }
                buffer.flush();
                return buffer.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(byte[] byteArr) {
            try {
                if (mMap == null || byteArr == null)
                    return;
                KmlLayer kmlLayer = new KmlLayer( mMap, new ByteArrayInputStream( byteArr ),
                        getActivity() );
                kmlLayer.addLayerToMap();
                kmlLayer.setOnFeatureClickListener( new KmlLayer.OnFeatureClickListener() {
                    @Override
                    public void onFeatureClick(Feature feature) {

                    }
                } );
                moveCameraToKml( kmlLayer );
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected GoogleMap getMap() {
        return mMap;
    }
}

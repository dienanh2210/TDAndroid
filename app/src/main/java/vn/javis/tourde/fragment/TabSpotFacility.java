package vn.javis.tourde.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.model.SpotEquipment;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;

public class TabSpotFacility extends BaseFragment implements View.OnClickListener {

    CourseListActivity activity;
    int spotId;
    //Overriden method onCreateView
    @BindView(R.id.bt_spot_page)
    Button bt_spot_page;
    @BindView(R.id.toilet_1)
    TextView toilet_1;
    @BindView(R.id.toilet_2)
    TextView toilet_2;
    @BindView(R.id.parking_1)
    TextView parking_1;
    @BindView(R.id.parking_2)
    TextView parking_2;
    @BindView(R.id.accomodation_1)
    TextView accomodation_1;
    @BindView(R.id.accomodation_2)
    TextView accomodation_2;

    @BindView(R.id.bath_1)
    TextView bath_1;
    @BindView(R.id.bath_2)
    TextView bath_2;
    @BindView(R.id.shower_1)
    TextView shower_1;
    @BindView(R.id.shower_2)
    TextView shower_2;
    @BindView(R.id.locker_1)
    TextView locker_1;
    @BindView(R.id.locker_2)
    TextView locker_2;
    @BindView(R.id.dress_1)
    TextView dress_1;
    @BindView(R.id.dress_2)
    TextView dress_2;
    @BindView(R.id.bike_delivey_1)
    TextView bike_delivey_1;
    @BindView(R.id.bike_delivey_2)
    TextView bike_delivey_2;
    @BindView(R.id.tour_info_1)
    TextView tour_info_1;
    @BindView(R.id.tour_info_2)
    TextView tour_info_2;
    @BindView(R.id.cycle_rack_1)
    TextView cycle_rack_1;
    @BindView(R.id.cycle_rack_2)
    TextView cycle_rack_2;
    @BindView(R.id.bike_rented_1)
    TextView bike_rented_1;
    @BindView(R.id.bike_rented_2)
    TextView bike_rented_2;
    @BindView(R.id.cycling_guide_1)
    TextView cycling_guide_1;
    @BindView(R.id.cycling_guide_2)
    TextView cycling_guide_2;
    @BindView(R.id.tool_rental_1)
    TextView tool_rental_1;
    @BindView(R.id.tool_rental_2)
    TextView tool_rental_2;
    @BindView(R.id.floor_pump_rental_1)
    TextView floor_pump_rental_1;
    @BindView(R.id.floor_pump_rental_2)
    TextView floor_pump_rental_2;
    @BindView(R.id.mechanic_maintenance_1)
    TextView mechanic_maintenance_1;
    @BindView(R.id.mechanic_maintenance_2)
    TextView mechanic_maintenance_2;

    String token = LoginFragment.getmUserToken();
    CourseListActivity mActivity;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
//        bt_spot_page.setOnClickListener(this);
//        insertInfo();
bt_spot_page.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(token!="")
            insertInfo();
        else
            mActivity.showDialogWarning();
    }
} );

    }

    void insertInfo() {
         spotId = getArguments().getInt(CourseListActivity.SPOT_ID);
        Log.d("tab spot facility 54", "" + spotId);
        SpotDataAPI.getSpotEquipmentReview(spotId, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                // Log.d("logout", jsonObject.toString());
                Log.d("tab spot facility 100", "" + jsonObject);
                if (!jsonObject.has("error")) //success
                {
                    SpotEquipment model = SpotEquipment.getData(jsonObject.toString());
                    toilet_1.setText(model.getToiletOk());
                    toilet_2.setText(model.getToiletNg());
                    parking_1.setText(model.getParkingOk());
                    parking_2.setText(model.getParkingNg());
                    accomodation_1.setText(model.getAccommodationOk());
                    accomodation_2.setText(model.getAccommodationNg());
                    bath_1.setText(model.getBathOk());
                    bath_2.setText(model.getBathNg());
                    shower_1.setText(model.getShowerOk());
                    shower_2.setText(model.getShowerNg());
                    locker_1.setText(model.getLockerOk());
                    locker_2.setText(model.getLockerNg());
                    dress_1.setText(model.getDressingRoomOk());
                    dress_2.setText(model.getDressingRoomNg());
                    bike_delivey_1.setText(model.getBicycleDeliveryOk());
                    bike_delivey_2.setText(model.getBicycleDeliveryNg());
                    tour_info_1.setText(model.getTouristInformationOk());
                    tour_info_2.setText(model.getTouristInformationNg());
                    cycle_rack_1.setText(model.getCycleRackOk());
                    cycle_rack_2.setText(model.getCycleRackNg());
                    bike_rented_1.setText(model.getBicycleRentalOk());
                    bike_rented_2.setText(model.getBicycleRentalNg());
                    cycling_guide_1.setText(model.getCyclingGuideOk());
                    cycling_guide_2.setText(model.getCyclingGuideNg());
                    tool_rental_1.setText(model.getToolRentalOk());
                    tool_rental_2.setText(model.getToolRentalNg());
                    floor_pump_rental_1.setText(model.getFloorPumpRentalOk());
                    floor_pump_rental_2.setText(model.getFloorPumpRentalNg());
                    mechanic_maintenance_1.setText(model.getMechanicMaintenanceOk());
                    mechanic_maintenance_2.setText(model.getMechanicMaintenanceNg());

                } else //false
                {

                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (CourseListActivity) getActivity();
    }


    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.tab_spot_facility, container, false);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_spot_page:
               activity.showSpotFacilitiesFragment(spotId);
                break;

        }
    }
}

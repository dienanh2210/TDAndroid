package vn.javis.tourde.model;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SpotEquipment {

    @SerializedName("toilet_ok")
    @Expose
    private String toiletOk;
    @SerializedName("toilet_ng")
    @Expose
    private String toiletNg;
    @SerializedName("parking_ok")
    @Expose
    private String parkingOk;
    @SerializedName("parking_ng")
    @Expose
    private String parkingNg;
    @SerializedName("accommodation_ok")
    @Expose
    private String accommodationOk;
    @SerializedName("accommodation_ng")
    @Expose
    private String accommodationNg;
    @SerializedName("bath_ok")
    @Expose
    private String bathOk;
    @SerializedName("bath_ng")
    @Expose
    private String bathNg;
    @SerializedName("shower_ok")
    @Expose
    private String showerOk;
    @SerializedName("shower_ng")
    @Expose
    private String showerNg;
    @SerializedName("locker_ok")
    @Expose
    private String lockerOk;
    @SerializedName("locker_ng")
    @Expose
    private String lockerNg;
    @SerializedName("dressing_room_ok")
    @Expose
    private String dressingRoomOk;
    @SerializedName("dressing_room_ng")
    @Expose
    private String dressingRoomNg;
    @SerializedName("bicycle_delivery_ok")
    @Expose
    private String bicycleDeliveryOk;
    @SerializedName("bicycle_delivery_ng")
    @Expose
    private String bicycleDeliveryNg;
    @SerializedName("tourist_information_ok")
    @Expose
    private String touristInformationOk;
    @SerializedName("tourist_information_ng")
    @Expose
    private String touristInformationNg;
    @SerializedName("cycle_rack_ok")
    @Expose
    private String cycleRackOk;
    @SerializedName("cycle_rack_ng")
    @Expose
    private String cycleRackNg;
    @SerializedName("bicycle_rental_ok")
    @Expose
    private String bicycleRentalOk;
    @SerializedName("bicycle_rental_ng")
    @Expose
    private String bicycleRentalNg;

    public static SpotEquipment getData(String data) {
        return new Gson().fromJson(data, SpotEquipment.class);
    }
    public String getToiletOk() {
        return toiletOk;
    }

    public void setToiletOk(String toiletOk) {
        this.toiletOk = toiletOk;
    }

    public String getToiletNg() {
        return toiletNg;
    }

    public void setToiletNg(String toiletNg) {
        this.toiletNg = toiletNg;
    }

    public String getParkingOk() {
        return parkingOk;
    }

    public void setParkingOk(String parkingOk) {
        this.parkingOk = parkingOk;
    }

    public String getParkingNg() {
        return parkingNg;
    }

    public void setParkingNg(String parkingNg) {
        this.parkingNg = parkingNg;
    }

    public String getAccommodationOk() {
        return accommodationOk;
    }

    public void setAccommodationOk(String accommodationOk) {
        this.accommodationOk = accommodationOk;
    }

    public String getAccommodationNg() {
        return accommodationNg;
    }

    public void setAccommodationNg(String accommodationNg) {
        this.accommodationNg = accommodationNg;
    }

    public String getBathOk() {
        return bathOk;
    }

    public void setBathOk(String bathOk) {
        this.bathOk = bathOk;
    }

    public String getBathNg() {
        return bathNg;
    }

    public void setBathNg(String bathNg) {
        this.bathNg = bathNg;
    }

    public String getShowerOk() {
        return showerOk;
    }

    public void setShowerOk(String showerOk) {
        this.showerOk = showerOk;
    }

    public String getShowerNg() {
        return showerNg;
    }

    public void setShowerNg(String showerNg) {
        this.showerNg = showerNg;
    }

    public String getLockerOk() {
        return lockerOk;
    }

    public void setLockerOk(String lockerOk) {
        this.lockerOk = lockerOk;
    }

    public String getLockerNg() {
        return lockerNg;
    }

    public void setLockerNg(String lockerNg) {
        this.lockerNg = lockerNg;
    }

    public String getDressingRoomOk() {
        return dressingRoomOk;
    }

    public void setDressingRoomOk(String dressingRoomOk) {
        this.dressingRoomOk = dressingRoomOk;
    }

    public String getDressingRoomNg() {
        return dressingRoomNg;
    }

    public void setDressingRoomNg(String dressingRoomNg) {
        this.dressingRoomNg = dressingRoomNg;
    }

    public String getBicycleDeliveryOk() {
        return bicycleDeliveryOk;
    }

    public void setBicycleDeliveryOk(String bicycleDeliveryOk) {
        this.bicycleDeliveryOk = bicycleDeliveryOk;
    }

    public String getBicycleDeliveryNg() {
        return bicycleDeliveryNg;
    }

    public void setBicycleDeliveryNg(String bicycleDeliveryNg) {
        this.bicycleDeliveryNg = bicycleDeliveryNg;
    }

    public String getTouristInformationOk() {
        return touristInformationOk;
    }

    public void setTouristInformationOk(String touristInformationOk) {
        this.touristInformationOk = touristInformationOk;
    }

    public String getTouristInformationNg() {
        return touristInformationNg;
    }

    public void setTouristInformationNg(String touristInformationNg) {
        this.touristInformationNg = touristInformationNg;
    }

    public String getCycleRackOk() {
        return cycleRackOk;
    }

    public void setCycleRackOk(String cycleRackOk) {
        this.cycleRackOk = cycleRackOk;
    }

    public String getCycleRackNg() {
        return cycleRackNg;
    }

    public void setCycleRackNg(String cycleRackNg) {
        this.cycleRackNg = cycleRackNg;
    }

    public String getBicycleRentalOk() {
        return bicycleRentalOk;
    }

    public void setBicycleRentalOk(String bicycleRentalOk) {
        this.bicycleRentalOk = bicycleRentalOk;
    }

    public String getBicycleRentalNg() {
        return bicycleRentalNg;
    }

    public void setBicycleRentalNg(String bicycleRentalNg) {
        this.bicycleRentalNg = bicycleRentalNg;
    }

}




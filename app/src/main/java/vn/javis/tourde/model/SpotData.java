package vn.javis.tourde.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpotData {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("tag")
    @Expose
    private List<String> tag = null;

    public static Data getData(String data) {
        return new Gson().fromJson(data, Data.class);
    }

    public static SpotData getSpotData(String data) {
        return new Gson().fromJson(data, SpotData.class);
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public class Data {

        @SerializedName("spot_id")
        @Expose
        private String spotId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("top_image")
        @Expose
        private String topImage;
        @SerializedName("catch_phrase")
        @Expose
        private String catchPhrase;
        @SerializedName("introduction")
        @Expose
        private String introduction;
        @SerializedName("zip_code")
        @Expose
        private String zipCode;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("site_url")
        @Expose
        private String siteUrl;
        @SerializedName("tel")
        @Expose
        private String tel;
        @SerializedName("google_map_url")
        @Expose
        private String googleMapUrl;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("insert_datetime")
        @Expose
        private String insertDatetime;

        public String getSpotId() {
            return spotId;
        }

        public void setSpotId(String spotId) {
            this.spotId = spotId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTopImage() {
            return topImage;
        }

        public void setTopImage(String topImage) {
            this.topImage = topImage;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public void setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSiteUrl() {
            return siteUrl;
        }

        public void setSiteUrl(String siteUrl) {
            this.siteUrl = siteUrl;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getGoogleMapUrl() {
            return googleMapUrl;
        }

        public void setGoogleMapUrl(String googleMapUrl) {
            this.googleMapUrl = googleMapUrl;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getInsertDatetime() {
            return insertDatetime;
        }

        public void setInsertDatetime(String insertDatetime) {
            this.insertDatetime = insertDatetime;
        }

    }
}

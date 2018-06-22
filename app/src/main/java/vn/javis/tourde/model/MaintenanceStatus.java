package vn.javis.tourde.model;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MaintenanceStatus implements Serializable{

    @SerializedName("status")
    @Expose
    private String status;
    public static MaintenanceStatus getData(String data) {
        return new Gson().fromJson(data, MaintenanceStatus.class);
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}



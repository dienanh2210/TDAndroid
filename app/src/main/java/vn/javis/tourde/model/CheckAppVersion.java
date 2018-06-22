package vn.javis.tourde.model;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckAppVersion implements Serializable{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("check")
    @Expose
    private Integer check;

    public static CheckAppVersion getData(String data) {
        return new Gson().fromJson(data, CheckAppVersion.class);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }

}



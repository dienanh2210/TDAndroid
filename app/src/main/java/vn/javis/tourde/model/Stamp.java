package vn.javis.tourde.model;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Stamp {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("gained")
    @Expose
    private Boolean gained;
    @SerializedName("image")
    @Expose
    private String image;

    public static Stamp getData(String data) {
        return new Gson().fromJson(data, Stamp.class);
    }
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getGained() {
        return gained;
    }

    public void setGained(Boolean gained) {
        this.gained = gained;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

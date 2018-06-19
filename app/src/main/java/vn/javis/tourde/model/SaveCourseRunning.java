package vn.javis.tourde.model;

import java.util.ArrayList;
import java.util.List;

public class SaveCourseRunning {
    private int courseID;
    private double start_longtitude;
    private double start_latitude;
    private long timeRunning;
    private List<CheckedSpot> lstCheckedSpot = new ArrayList<>();

    public SaveCourseRunning(int courseID, double start_latitude, double start_longtitude) {
        this.courseID = courseID;
        this.start_longtitude = start_longtitude;
        this.start_latitude = start_latitude;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public double getStart_longtitude() {
        return start_longtitude;
    }

    public void setStart_longtitude(double start_longtitude) {
        this.start_longtitude = start_longtitude;
    }

    public double getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(double start_latitude) {
        this.start_latitude = start_latitude;
    }

    public long getTimeRunning() {
        return timeRunning;
    }

    public void setTimeRunning(long timeRunning) {
        this.timeRunning = timeRunning;
    }

    public List<CheckedSpot> getLstCheckedSpot() {
        return lstCheckedSpot;
    }

    public void setLstCheckedSpot(List<CheckedSpot> lstCheckedSpot) {
        this.lstCheckedSpot = lstCheckedSpot;
    }
    public void addCheckedSpot(int spotID, String title, int order, String imgUrl,boolean isChecked){
        CheckedSpot model =new CheckedSpot(spotID,title,order,imgUrl,isChecked);
        lstCheckedSpot.add(model);
    }
   public class CheckedSpot {
        private int spotID;
        private String title;
        private int orderNumber;
        private String topImage;
        private String time;
        private boolean checked;
        private double avarageSpeed;


        public CheckedSpot(int spotID, String title, int order, String imgUrl,boolean isChecked) {
            this.spotID = spotID;
            this.title = title;
            this.orderNumber = order;
            this.topImage = imgUrl;
            checked =isChecked;
        }

        public int getSpotID() {
            return spotID;
        }

        public void setSpotID(int spotID) {
            this.spotID = spotID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrder() {
            return orderNumber;
        }

        public void setOrder(int order) {
            this.orderNumber = order;
        }

        public String getImgUrl() {
            return topImage;
        }

        public void setImgUrl(String imgUrl) {
            this.topImage = imgUrl;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public double getAvarageSpeed() {
            return avarageSpeed;
        }

        public void setAvarageSpeed(double avarageSpeed) {
            this.avarageSpeed = avarageSpeed;
        }
    }
}

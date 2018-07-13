package vn.javis.tourde.model;

import java.util.ArrayList;
import java.util.List;

public class SaveCourseRunning {
    private int courseID;
    private double start_longtitude;
    private double start_latitude;
    private double last_longtitude;
    private double last_latitude;
    private long timeRunning;
    private long lastCheckedTime;
    private boolean isFinished;
    private int lastCheckedOrder;
    private String imgUrlGoal;
    private int goalSpotId;
    private String goal_title;
    private String allDistance;
    private float avarageSpeed;
    private int highestCheckedSpot;


    public SaveCourseRunning(int courseID, double start_latitude, double start_longtitude) {
        this.courseID = courseID;
        this.start_longtitude = start_longtitude;
        this.start_latitude = start_latitude;
    }

    public int getHighestCheckedSpot() {
        return highestCheckedSpot;
    }

    public void setHighestCheckedSpot(int highestCheckedSpot) {
        this.highestCheckedSpot = highestCheckedSpot;
    }

    public String getImgUrlGoal() {
        return imgUrlGoal;
    }

    public void setImgUrlGoal(String imgUrlGoal) {
        this.imgUrlGoal = imgUrlGoal;
    }

    public double getLast_longtitude() {
        return last_longtitude;
    }

    public void setLast_longtitude(double last_longtitude) {
        this.last_longtitude = last_longtitude;
    }

    public double getLast_latitude() {
        return last_latitude;
    }

    public void setLast_latitude(double last_latitude) {
        this.last_latitude = last_latitude;
    }

    public int getGoalSpotId() {
        return goalSpotId;
    }

    public void setGoalSpotId(int goalSpotId) {
        this.goalSpotId = goalSpotId;
    }

    public String getGoal_title() {
        return goal_title;
    }

    public void setGoal_title(String goal_title) {
        this.goal_title = goal_title;
    }

    public String getAllDistance() {
        return allDistance;
    }

    public void setAllDistance(String allDistance) {
        this.allDistance = allDistance;
    }

    public float getAvarageSpeed() {
        return avarageSpeed;
    }

    public void setAvarageSpeed(float avarageSpeed) {
        this.avarageSpeed = avarageSpeed;
    }

    public long getLastCheckedTime() {
        return lastCheckedTime;
    }

    public void setLastCheckedTime(long lastCheckedTime) {
        this.lastCheckedTime = lastCheckedTime;
    }

    public int getLastCheckedOrder() {
        return lastCheckedOrder;
    }

    public void setLastCheckedOrder(int lastCheckedOrder) {
        this.lastCheckedOrder = lastCheckedOrder;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    private List<CheckedSpot> lstCheckedSpot = new ArrayList<>();


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

    public void addCheckedSpot(int spotID, String title, int order, String imgUrl, boolean isChecked) {
        CheckedSpot model = new CheckedSpot(spotID, title, order, imgUrl, isChecked);
        lstCheckedSpot.add(model);
    }

    public void resetAllSpot() {
        for (int i = 1; i < lstCheckedSpot.size(); i++) {
            lstCheckedSpot.get(i).setChecked(false);
        }
    }

    public class CheckedSpot {
        private int spotID;
        private String title;
        private int orderNumber;
        private String topImage;
        private String time;
        private boolean checked;
        private double avarageSpeed;
        private boolean isHighestChecked;

        public CheckedSpot(int spotID, String title, int order, String imgUrl, boolean isChecked) {
            this.spotID = spotID;
            this.title = title;
            this.orderNumber = order;
            this.topImage = imgUrl;
            checked = isChecked;
        }

        public int getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(int orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getTopImage() {
            return topImage;
        }

        public void setTopImage(String topImage) {
            this.topImage = topImage;
        }

        public boolean isHighestChecked() {
            return isHighestChecked;
        }

        public void setHighestChecked(boolean highestChecked) {
            isHighestChecked = highestChecked;
        }

        public boolean isTurnOnShortLink() {
            return turnOnShortLink;
        }

        public void setTurnOnShortLink(boolean turnOnShortLink) {
            this.turnOnShortLink = turnOnShortLink;
        }

        private boolean turnOffAnim;
        private boolean turnOnShortLink;

        public boolean isTurnOffAnim() {
            return turnOffAnim;
        }

        public void setTurnOffAnim(boolean turnOffAnim) {
            this.turnOffAnim = turnOffAnim;
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

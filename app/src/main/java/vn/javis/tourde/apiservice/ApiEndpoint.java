package vn.javis.tourde.apiservice;

/**
 * Created by QuanPV on 3/30/18.
 */

public class ApiEndpoint {

    //public static final String BASE_URL ="http://www.app-tour-de-nippon.jp/api";
    public static final String BASE_URL ="https://www.app-tour-de-nippon.jp/test/api";
    public static String GET_COURSE_LIST = "/get/getCourseList/";
    public static String GET_COURSE_DATA = "/get/getCourseData/";
    public static String POST_CREATE_ACCOUNT = "/post/createAccount/";
    public static String POST_LOGIN_ACCOUNT = "/post/loginAccountEmail/";
    public static String POST_LOGOUT_ACCOUNT = "/post/logoutAccount/";
    public static String POST_LOGIN_SNS = "/post/loginAccountSNS/";
    public  static String GET_GET_ACCOUNT ="/get/getAccount/";
    public  static String POST_REVIEW_COURSE ="/post/reviewCourse/";
    public  static String POST_CHECK_COURSE_REVIEW ="/post/checkCourseReview/";
    public  static String GET_FAVORITE_COURSE_APP  ="/get/getFavoriteCourseListApp/";
    public  static String POST_INSERT_FAVORITE_COURSE_APP ="/post/insertFavoriteCourseApp/";
    public  static String POST_DELETE_FAVORITE_COURSE_APP ="/post/deleteFavoriteCourseApp/";
    public  static String GET_SPOT_DATA ="/get/getSpotData/";
    public  static String GET_SPOT_EQUIPMENT_REVIEW ="/get/getSpotEquipmentReview";
    public  static String POST_IMAGE ="/post/postImage/";
    public  static String POST_COURSE_IMAGE ="/post/postCourseImage/";
    public  static String POST_COURSE_LOG ="/post/postCourseLog/";
    public  static String POST_CHECK_IN_STAMP ="/post/checkInStamp/";
    public  static String POST_RESET_PASSWORD ="/post/resetPassword/";
    public static String SNS_ID = "sns_id";
    public static String SNS_KIND = "sns_kind";

}

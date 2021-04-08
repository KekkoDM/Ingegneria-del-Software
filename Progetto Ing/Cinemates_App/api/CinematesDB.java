package com.example.cinemates.api;

public class CinematesDB {
    private static final String BASE_URL = "https://15.161.38.136/";
    //private static final String BASE_URL = "https://cinematesphp.000webhostapp.com/cinematesREST/";
    public static final String REGISTER_URL = BASE_URL + "utente/register.php";
    public static final String RECOVERY_PASSWORD = BASE_URL + "utente/recoveryPassword.php";
    public static final String LOGIN_URL = BASE_URL + "utente/login.php";
    public static final String CHECK_EMAIL = BASE_URL + "utente/checkEmail.php";
    public static final String UPDATE_URL = BASE_URL + "utente/updatePassword.php";
    public static final String DELETE_URL = BASE_URL + "utente/delete.php";
    public static final String FRIENDS_URL = BASE_URL + "utente/getFriendsList.php";
    public static final String GENERAL_NOTIFICATION_URL = BASE_URL + "notifica/getGeneralNotifications.php";
    public static final String FOLLOW_NOTIFICATION_URL = BASE_URL + "notifica/getFollowNotifications.php";
    public static final String ACCEPT_FOLLOW_NOTIFICATION_URL = BASE_URL + "notifica/acceptFollowNotification.php";
    public static final String REJECT_FOLLOW_NOTIFICATION_URL = BASE_URL + "notifica/rejectFollowNotification.php";
    public static final String SEND_FOLLOW_NOTIFICATION_URL = BASE_URL + "notifica/sendFollowNotification.php";
    public static final String SEARCH_USER = BASE_URL + "utente/searchUser.php";
    public static final String LIST_FAVORITES_URL = BASE_URL + "film/getListFavorites.php";
    public static final String LIST_TO_SEE_URL = BASE_URL + "film/getListToSee.php";
    public static final String ADD_TO_LIST_URL = BASE_URL + "film/addToList.php";
    public static final String CHECK_EXIST_IN_LIST = BASE_URL + "film/checkExistInList.php";
    public static final String REMOVE_FROM_LIST = BASE_URL + "film/removeFromList.php";
    public static final String SHARED_CONTENT = BASE_URL + "utente/seeSharedContents.php";
    public static final String SEND_REPORT = BASE_URL + "segnalazione/sendReport.php";
    public static final String REMOVE_FRIEND = BASE_URL + "utente/removeFriend.php";
    public static final String SEND_REACTION = BASE_URL + "recensione/sendReaction.php";
    public static final String GET_REACTION = BASE_URL + "recensione/getReaction.php";
    public static final String GET_COMMENTS = BASE_URL + "recensione/getComments.php";
    public static final String SEND_COMMENT = BASE_URL + "recensione/sendComment.php";
    public static final String REPORT_COUNT = BASE_URL + "segnalazione/getReportCount.php";
}
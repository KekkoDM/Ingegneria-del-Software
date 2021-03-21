package com.example.cinemates.api;

public class CinematesDB {
    private static final String BASE_URL = "https://cinematesphp.000webhostapp.com/cinematesREST/";
    public static final String REGISTER_URL = BASE_URL + "utente/register.php";
    public static final String RECOVERY_PASSWORD = BASE_URL + "utente/recoveryPassword.php";
    public static final String LOGIN_URL = BASE_URL + "utente/login.php";
    public static final String UPDATE_URL = BASE_URL + "utente/updatePassword.php";
    public static final String DELETE_URL = BASE_URL + "utente/delete.php";
    public static final String FRIENDS_URL = BASE_URL + "utente/getFriendsList.php";
    public static final String GENERAL_NOTIFICATION_URL = BASE_URL + "notifica/getGeneralNotifications.php";
    public static final String FOLLOW_NOTIFICATION_URL = BASE_URL + "notifica/getFollowNotifications.php";
    public static final String ACCEPT_FOLLOW_NOTIFICATION_URL = BASE_URL + "notifica/acceptFollowNotification.php";
    public static final String REJECT_FOLLOW_NOTIFICATION_URL = BASE_URL + "notifica/rejectFollowNotification.php";
    public static final String SEND_FOLLOW_NOTIFICATION_URL = BASE_URL + "notifica/sendFollowNotification.php";
    public static final String SEARCH_USER = BASE_URL + "utente/searchUser.php";
}

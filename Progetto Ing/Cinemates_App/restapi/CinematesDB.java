package com.example.cinemates.restapi;

public class CinematesDB {
    private static final String BASE_URL = "https://cinematesphp.000webhostapp.com/cinematesREST/";

    public static final String REGISTER_URL = BASE_URL + "utente/register.php";
    public static final String RECOVERY_PASSWORD = BASE_URL + "utente/recoveryPassword.php";
    public static final String LOGIN_URL = BASE_URL + "utente/login.php";
    public static final String UPDATE_URL = BASE_URL + "utente/updatePassword.php";
    public static final String DELETE_URL = BASE_URL + "utente/delete.php";
    public static final String FRIENDS_URL = BASE_URL + "utente/getFriendsList.php";
    public static final String NOTIFICATION_URL = BASE_URL + "notifica/getNotifications.php";
}

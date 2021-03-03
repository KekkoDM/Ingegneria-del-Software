package com.example.cinemates.restapi;

public class CinematesDB {
    private static final String BASE_URL = "https://cinematesphp.000webhostapp.com/cinematesREST/";
    public static final String REGISTER_URL = BASE_URL + "utenteCRUD/create.php";
    public static final String LOGIN_URL = BASE_URL + "utenteCRUD/read.php";
    public static final String UPDATE_URL = BASE_URL + "utenteCRUD/update.php";
    public static final String DELETE_URL = BASE_URL + "utenteCRUD/delete.php";
    public static final String FRIENDS_URL = BASE_URL + "utenteCRUD/myfriends.php";
}

package com.vbd.mapexam.config;

/**
 * Created by tandat on 11/13/2017.
 */

public class Config {

    public static String USER_INFO_RF_NAME = "user_info";
    public static String SEARCH_ALL_URL = "http://192.168.10.91:88/api/map/search?key={key}";
    public static String FIND_SHORTED_PATH_URL = "http://192.168.10.91:88/api/map/FindShortPath?type={type}";
    public static String GET_TOKEN_URL = "http://192.168.10.91:88/token";
    public static String FIND_ALL_POINT_URL = "http://192.168.10.91:88/api/map/findallpoints";
    public static String GET_ALL_STREET_SAVED_URL = "http://192.168.10.91:88/api/map/LoadStreetSaved";
    public static String DELETE_POINT_URL = "http://192.168.10.91:88/api/map/DeletePoint?id={id}";
    public static String INSERT_POINT_URL = "http://192.168.10.91:88/api/map/InsertPoint?NamePoint={NamePoint}&Lat={Lat}&Lg={Lg}&Inform={Inform}&Address={Address}";
    public static String UPDATE_POINT_URL = "http://192.168.10.91:88/api/map/UpdatePoint?id={id}&NamePoint={NamePoint}&Address={Address}&Inform={Inform}";
    public static String INSERT_STREET_URL = "http://192.168.10.91:88/api/map/CreateStreet?name={name}";
    public static String DELETE_STREET_URL = "http://192.168.10.91:88/api/map/DeleteStreet?id={id}";
}

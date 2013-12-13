package com.example.maptest;

/**
 * Created with IntelliJ IDEA.
 * User: akhil
 * Date: 24/10/13
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class RouteConfig {
    public static int SOURCE_DESTINATION_ONLY;
    public static int MULTIPLE_LOCATIONS;

    public static  int ONE_KM_PER_HOUR = 1*1000;
    private int speed ;// abc/KM
    private int mode ;//

    public RouteConfig(int speed, int mode) {
        this.speed = speed;
        this.mode = mode;
    }
}
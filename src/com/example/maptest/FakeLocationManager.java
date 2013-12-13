package com.example.maptest;

/**
 * Created with IntelliJ IDEA.
 * User: akhil
 * Date: 25/10/13
 * Time: 8:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class FakeLocationManager {
    public static FakeLocationManager instance;
    private Route mRoute;
    private RouteConfig mRouteConfig;

    public void setmRoute(Route mRoute) {
        this.mRoute = mRoute;
    }

    public void setmRouteConfig(RouteConfig mRouteConfig) {
        this.mRouteConfig = mRouteConfig;
    }

    private FakeLocationManager() {
    }

    public static FakeLocationManager getInstance() {
        if (instance == null) {
            instance = new FakeLocationManager();
        }
        return instance;
    }


    public Route getRoute() {
        return mRoute;  //To change body of created methods use File | Settings | File Templates.
    }

    public RouteConfig getRouteConfig() {
        return mRouteConfig;
    }
}

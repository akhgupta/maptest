package com.example.maptest;

import android.location.Location;
import android.os.SystemClock;
import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: akhil
 * Date: 25/10/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Route {
    private int currentPoint = -1;
//    private TestLocation startLocation;
//    private TestLocation destinationLocation;
    private LinkedList<TestLocation> points;

    public Route(TestLocation startLocation, TestLocation destinationLocation, RouteConfig routeConfig) {
//        this.startLocation = startLocation;
//        this.destinationLocation = destinationLocation;

        points = new LinkedList<TestLocation>();
//        points.addFirst(startLocation);
//        points.addLast(destinationLocation);
    }

    public void addPoint(int index, TestLocation location) {
        if ((index == -1) || index >= points.size())
            points.add(points.size() - 1, location);
        else
            points.add(index, location);
    }

//    public TestLocation getStartPoint() {
//        return startLocation;
//    }
//
//    public TestLocation getDestinationLocation() {
//        return destinationLocation;
//    }
    public void addPoints(List<LatLng> latLngs)
    {
        for (LatLng latLng: latLngs)
            points.add(new TestLocation(latLng.latitude,latLng.longitude));

    }

    public Location getNextLocation() {
        if (currentPoint < points.size()-1) {
            currentPoint++;
            TestLocation testLocation = points.get(currentPoint);

            Location location = new Location("fused");
            location.setTime(System.currentTimeMillis());
            location.setAccuracy(3.0f);
            try {
//                location.setElapsedRealtimeNanos(SystemClock.elapsedRealtime()*1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
//            location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
            location.setLatitude(testLocation.lat);
            location.setLongitude(testLocation.lng);
            return location;
        }
        return null;
    }

    public LinkedList<TestLocation> getRoute() {
        return points;
    }
}

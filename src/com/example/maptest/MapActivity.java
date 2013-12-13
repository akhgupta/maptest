package com.example.maptest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements LocationListener {
    private Handler mHander;
    private SupportMapFragment mapfrag;
    private GoogleMap map;
    private Polyline polyline;
    private List<LatLng> points = new ArrayList<LatLng>();
    private LocationManager locMgr;
    private MyLocationSource myLocationSource;

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocationSource = new MyLocationSource();


        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Route route = new Route(null,null,null);
                route.addPoints(points);
                FakeLocationManager.getInstance().setmRoute(route);
                FakeLocationManager.getInstance().setmRouteConfig(new RouteConfig(1000,RouteConfig.MULTIPLE_LOCATIONS));
                startService(new Intent(MapActivity.this,FakeRouteSimulatorService.class));
            }
        });

        mHander = new Handler();
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                mapfrag = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfrag));
                map = mapfrag.getMap();
                map.setMyLocationEnabled(true);
                map.setLocationSource(myLocationSource);
                locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, MapActivity.this);
//                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.512746, 77.03629), 15));
                map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                    map.clear();
                    map.addMarker(new MarkerOptions().position(latLng).draggable(true).flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)).anchor(0, 0));
                    points.clear();
                    polyline = map.addPolyline(new PolylineOptions()
                                .width(5)
                                .color(Color.GREEN));
                    }
                });

                map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {
                        points.add(marker.getPosition());
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                        points.add(marker.getPosition());
                        polyline.setPoints(points);

                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        points.add(marker.getPosition());
                    }
                });
            }
        }, 2000);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(myLocationSource!=null && myLocationSource.mOnLocationChangedListener!=null)
            myLocationSource.mOnLocationChangedListener.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderEnabled(String provider) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderDisabled(String provider) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

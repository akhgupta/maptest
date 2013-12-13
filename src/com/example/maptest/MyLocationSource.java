package com.example.maptest;

import com.google.android.gms.maps.LocationSource;

/**
 * Created with IntelliJ IDEA.
 * User: akhil
 * Date: 25/10/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyLocationSource implements LocationSource {
    public OnLocationChangedListener mOnLocationChangedListener;

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mOnLocationChangedListener=onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

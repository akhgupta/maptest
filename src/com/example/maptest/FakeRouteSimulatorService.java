package com.example.maptest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: akhil
 * Date: 25/10/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class FakeRouteSimulatorService extends Service implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    private RouteConfig routeConfig;
    private Route route;
    private LocationManager locMgr;
    private LocationClient locationClient;
    private Handler mHandler;
    private Timer mTimer;
    private MyTimerTask moveAheadTask;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);    //To change body of overridden methods use File | Settings | File Templates.

        routeConfig = FakeLocationManager.getInstance().getRouteConfig();
        route = FakeLocationManager.getInstance().getRoute();

        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            locMgr.addTestProvider("fused",false,false,false,false,false,false,false,0,5);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            locMgr.addTestProvider("flp", false, false, false, false, false, false, false, 0, 5);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        locMgr.addTestProvider(LocationManager.GPS_PROVIDER,false,false,false,false,false,false,false,0,5);
        locMgr.addTestProvider(LocationManager.NETWORK_PROVIDER,false,false,false,false,false,false,false,0,5);

        try {
            locMgr.setTestProviderEnabled("flp", true);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        try {
            locMgr.setTestProviderEnabled("fused", true);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        locMgr.setTestProviderEnabled(LocationManager.GPS_PROVIDER,true);
        locMgr.setTestProviderEnabled(LocationManager.NETWORK_PROVIDER,true);

        locationClient = new LocationClient(this,this,this);
        locationClient.connect();
        mHandler=new Handler();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onConnected(Bundle bundle) {
        locationClient.setMockMode(true);
        mTimer = new Timer();
        moveAheadTask = new MyTimerTask();
        mTimer.schedule(moveAheadTask,0,1000);
    }

    @Override
    public void onDisconnected() {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.

        if(mTimer!=null)
            mTimer.cancel();

        if(locationClient!=null && locationClient.isConnected())
        {
            locationClient.setMockMode(false);
            locationClient.disconnect();
        }

        if(locMgr!=null)
        {
            locMgr.setTestProviderEnabled("flp", false);
            locMgr.setTestProviderEnabled("fused", false);
            locMgr.setTestProviderEnabled(LocationManager.GPS_PROVIDER,false);
            locMgr.setTestProviderEnabled(LocationManager.NETWORK_PROVIDER,false);

            //locMgr.removeTestProvider("flp");
//            locMgr.removeTestProvider("fused");
//            locMgr.removeTestProvider(LocationManager.GPS_PROVIDER);
//            locMgr.removeTestProvider(LocationManager.NETWORK_PROVIDER);
        }
    }

    private class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            Location location =route.getNextLocation();
            if(location==null)
            {
                stopSelf();
                return;
            }
            location.setProvider("fused");
            locationClient.setMockLocation(location);
            locMgr.setTestProviderLocation("fused",location);

            try {
                location.setProvider("flp");
                locMgr.setTestProviderLocation("flp",location);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            location.setProvider(LocationManager.GPS_PROVIDER);
            locMgr.setTestProviderLocation(LocationManager.GPS_PROVIDER,location);

            location.setProvider(LocationManager.NETWORK_PROVIDER);
            locMgr.setTestProviderLocation(LocationManager.NETWORK_PROVIDER,location);
        }
    }
}

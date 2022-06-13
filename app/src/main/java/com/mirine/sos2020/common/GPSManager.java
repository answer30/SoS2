package com.mirine.sos2020.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSManager implements LocationListener {
    private static final int REQUEST_CODE_LOCATION = 2;




    private LocationManager locationManager;
    private Context context;
    private Location location;


    private static GPSManager instance;

    private GPSManager(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static GPSManager getInstance(Context context) {
        if(instance == null) {
            instance = new GPSManager(context);
        }
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startLocationCheck() {

        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this); //중계기를 기준으로 빠르고 넓은 범위로 내 위치를 인식한 다.
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this); //gps 기준으로 정확한
        }

    }

    public void removeLocationListener() {
        locationManager.removeUpdates(this);
    }

    private Location getMyLocation() {
        Location currentLocation = null;
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
        }

        return currentLocation;
    }



    private String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(context, "네트워크 이상으로 주소 확인 불가", Toast.LENGTH_LONG).show();
            return "네트워크 이상으로 주소 확인 불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(context, "GPS 좌표 확인 불가", Toast.LENGTH_LONG).show();
            return "GPS 좌표 확인 불가";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(context, "주소 확인 불가", Toast.LENGTH_LONG).show();
            return "주소 확인 불가";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }

    public String getAddress() {
        if(location == null) {
            location = getMyLocation();
        }

        return getCurrentAddress(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onLocationChanged(Location location) {
        GPSManager.this.location = location;


        if(!location.hasAccuracy()){
            return;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

}


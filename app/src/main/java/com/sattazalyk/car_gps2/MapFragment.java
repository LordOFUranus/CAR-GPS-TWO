package com.sattazalyk.car_gps2;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.maps.GeoApiContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import static android.content.Context.MODE_PRIVATE;


public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;

    SupportMapFragment mapFragment;

    private Button btn_point_map;
    private TextView tv_geocode, tv_pointcounter_map, tv_user_name;
    private Switch sw_locationupdates, sw_gps;

    private boolean sw_locationupdates_isChecked;
    private boolean sw_gps_isChecked;


    private LatLng u;

    //Location Request  - настройки для FusedLocationProviderClient

    LocationRequest locationRequest;

    // Google API локационные сервисы
    FusedLocationProviderClient fusedLocationProviderClient;

    LocationCallback locationCallBack;


    //Скорость запросов
    public static final int DEFAULT_UPDATE_INTERVAL = 10;
    public static final int FAST_UPDATE_INTERVAL = 5;

    private static final int PERMISSION_FINE_LOCATION = 99;


    List<Location> locationLogList;
    Location locationLog;

    Polyline userPolyline;
    MarkerOptions uMarker;
    Marker uPostion = null;

    //SharePref

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH_UPDATE = "sw_locationupdates";
    public static final String SWITCH_GPS = "sw_gps";


    private GeoApiContext mGeoApiContext = null;

    public void initializationUI(View view) {
        tv_geocode = view.findViewById(R.id.tv_geocode);
        tv_pointcounter_map = view.findViewById(R.id.tv_pointcounter_map);
        sw_gps = view.findViewById(R.id.sw_gps_map);
        sw_locationupdates = view.findViewById(R.id.sw_locationsupdates_map);
        btn_point_map = view.findViewById(R.id.btn_point_map);
    }

    private void initializationLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(100 * DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(100 * FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                updateUIValues(locationResult.getLastLocation());
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializationLocationRequest();
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initializationUI(view);

        sw_gps.setOnClickListener(v -> {
            if (sw_gps.isChecked()) {
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                saveData();
                //tv_sensor.setText("Используется GPS-датчик");
            } else {
                locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                saveData();
                //tv_sensor.setText("Используется башни и Wi-Fi");
            }
        });

        sw_locationupdates.setOnClickListener(v -> {
            if (sw_locationupdates.isChecked()) {
                saveData();
                startLocationUpdates();
            } else {
                stopLocationUpdate();
                saveData();
            }
        });

        loadData();

        btn_point_map.setOnClickListener(v -> {
            GPS_Data gps_data = (GPS_Data) getActivity().getApplicationContext();
            locationLogList = gps_data.getLocationPoints();
            locationLogList.add(locationLog);
            updateGPS();
        });

        GPS_Data gps_data = (GPS_Data) getActivity().getApplicationContext();
        locationLogList = gps_data.getLocationPoints();

        //объявление карты
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        updateGPS();
        return view;
    }


    private void startLocationUpdates() {
        //tv_update.setText("Локация отслеживается");

        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mapFragment.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper());
        updateGPS();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(u, 17f));
    }

    private void stopLocationUpdate() {

      /*tv_speed.setText("Не отслеживается");
        tv_lat.setText("Не отслеживается");
        tv_lon.setText("Не отслеживается");
        tv_accuracy.setText("Не отслеживается");;*/

        tv_pointcounter_map.setText("-");
        tv_geocode.setText("Не отслеживается");
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_FINE_LOCATION:
                ;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                } else {
                    Toast.makeText(getActivity(), "Этому приложению нужно разрешение на местоположение",
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    }


    private void updateGPS() {
        //получение разрешения местоположения

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(mapFragment.getActivity()));
        if (ActivityCompat.checkSelfPermission(mapFragment.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //предоставление разрешения
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
                u = new LatLng(location.getLatitude(), location.getLongitude());
                locationLog = location;

                uMarker =
                        new MarkerOptions()
                                .position(u)
                                .flat(true).title("Ваше Местоположение");
                onLocationChanged(location);
                updateUIValues(location);
            });
        } else {
            //не получено разрешение
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            }
        }

    }

    private void updateUIValues(Location location) {
        /*tv_lat.setText(String.valueOf(location.getLatitude()));
        tv_lon.setText(String.valueOf(location.getLongitude()));
        tv_accuracy.setText(String.valueOf(location.getAccuracy()));
        if(location.hasSpeed()){
            tv_speed.setText(String.valueOf(location.getSpeed()));
        }
        else tv_speed.setText("Нет данных о скорости");*/

        Geocoder geocoder = new Geocoder(getActivity());

        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            tv_geocode.setText(addressList.get(0).getAddressLine(0));
        } catch (Exception e) {
            tv_geocode.setText("Невозможно получить адрес");
        }

        GPS_Data gps_data = (GPS_Data) getActivity().getApplicationContext();
        locationLogList = gps_data.getLocationPoints();
        tv_pointcounter_map.setText(Integer.toString(locationLogList.size()));

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (Location l : locationLogList) {
            LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Lat: " + l.getLatitude() + " Lon: " + l.getLongitude() + " #" + locationLogList.indexOf(l) + 1);
            mMap.addMarker(markerOptions);
        }

    }



    //shared pref
    public void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(SWITCH_UPDATE, sw_locationupdates.isChecked());
        editor.putBoolean(SWITCH_GPS, sw_gps.isChecked());
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        sw_locationupdates_isChecked = sharedPreferences.getBoolean(SWITCH_UPDATE, false);
        sw_gps_isChecked = sharedPreferences.getBoolean(SWITCH_GPS, false);

        sw_locationupdates.setChecked(sw_locationupdates_isChecked);
        sw_gps.setChecked(sw_gps_isChecked);
    }

    @Override
    public void onLocationChanged(@NonNull @NotNull Location location) {
        if(uPostion!=null){
            uPostion.remove();
        }
        uPostion = mMap.addMarker(uMarker);
        uPostion.setPosition(u);
    }
}
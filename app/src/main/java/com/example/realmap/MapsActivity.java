package com.example.realmap;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.realmap.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    Button buttonKebab;
    Button buttonStore;
    Button papildomas;

    String kebabline = "";
    double kebablat;
    double kebablng;

    String storeline = "";
    double storelat;
    double storelng;

    String papline = "";
    double paplat;
    double paplng;
    String papline2 = "";
    double paplat2;
    double paplng2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //read kebabas.txt
        try {
            InputStream inputStream = getAssets().open("kebabas.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            kebabline = new String(buffer);
            inputStream.close();
        }catch (IOException e){

        }

        String[] kebablatlng =  kebabline.split(",");
        kebablat = Double.parseDouble(kebablatlng[0]);
        kebablng = Double.parseDouble(kebablatlng[1]);

        //read parduotuve.txt
        try {
            InputStream inputStream = getAssets().open("parduotuve.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            storeline = new String(buffer);
            inputStream.close();
        }catch (IOException e){

        }

        String[] storelatlng =  storeline.split(",");
        storelat = Double.parseDouble(storelatlng[0]);
        storelng = Double.parseDouble(storelatlng[1]);

        //read papildomas.txt
        try {
            InputStream inputStream = getAssets().open("papildomas.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            papline = new String(buffer);

            inputStream.close();
        }catch (IOException e){

        }

        String[] paplatlng =  papline.split(",");
        paplat = Double.parseDouble(paplatlng[0]);
        paplng = Double.parseDouble(paplatlng[1]);

        //read papildomas2.txt
        try {
            InputStream inputStream = getAssets().open("papildomas2.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            papline2 = new String(buffer);

            inputStream.close();
        }catch (IOException e){

        }

        String[] paplatlng2 =  papline2.split(",");
        paplat2 = Double.parseDouble(paplatlng2[0]);
        paplng2 = Double.parseDouble(paplatlng2[1]);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buttonKebab = (Button) findViewById(R.id.button1);
        buttonStore = (Button) findViewById(R.id.button2);
        papildomas = (Button) findViewById(R.id.button3);

        buttonKebab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add marker
                LatLng kebab = new LatLng(kebablat, kebablng);
                mMap.addMarker(new MarkerOptions().position(kebab).title("Geriausia kebabinė"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(kebab));

            }
        });

        buttonStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add marker

                LatLng store = new LatLng(storelat, storelng);
                mMap.addMarker(new MarkerOptions().position(store).title("Geriausia parduotuvė"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(store));

            }
        });

        papildomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng pap = new LatLng(paplat, paplng);
                mMap.addMarker(new MarkerOptions().position(pap).title("Papildomas laukas"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pap));
                LatLng pap2 = new LatLng(paplat2, paplng2);
                mMap.addMarker(new MarkerOptions().position(pap2).title("Papildomas laukas2"));
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //Current location
        //mMap.setMyLocationEnabled(true);


        //Zoom
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        // Center map on Kaunas
        LatLng kaunas = new LatLng(54.899371487631626, 23.923480698567097);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kaunas));

        //LatLng currentL = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        //mMap.addMarker(new MarkerOptions().position(currentL).title("Jūs"));

    }


}
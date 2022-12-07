package edu.northeastern.cs5520fa22groupproject;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import edu.northeastern.cs5520fa22groupproject.model.EasyLifeUserDetails;

public class EasyLifeMakingPost extends AppCompatActivity {

    EditText editText;
    Button button;
    CheckBox checkBox;
    FirebaseUser user;
    LocationRequest locationRequest;
    String temp_location = "Earth";
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        setContentView(R.layout.activity_making_post);
        editText = findViewById(R.id.tv_postbox);
        checkBox = findViewById(R.id.location_checkbox);
        button = findViewById(R.id.btn_making_post);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/EasyLife");
                String context = editText.getText().toString();
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (context.length() == 0) {
                    Toast.makeText(EasyLifeMakingPost.this, "Tell us your story!", Toast.LENGTH_LONG).show();
                }
                if(checkBox.isChecked()) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (ActivityCompat.checkSelfPermission(EasyLifeMakingPost.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                            if(isGPSEnabled()){

                                LocationServices.getFusedLocationProviderClient(EasyLifeMakingPost.this)
                                        .requestLocationUpdates(locationRequest, new LocationCallback() {
                                            @Override
                                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                                super.onLocationResult(locationResult);

                                                LocationServices.getFusedLocationProviderClient(EasyLifeMakingPost.this)
                                                        .removeLocationUpdates(this);

                                                if (locationResult != null && locationResult.getLocations().size() >0){

                                                    int index = locationResult.getLocations().size() - 1;
                                                    latitude = locationResult.getLocations().get(index).getLatitude();
                                                    longitude = locationResult.getLocations().get(index).getLongitude();
                                                    Geocoder geocoder = new Geocoder(EasyLifeMakingPost.this, Locale.getDefault());
                                                    try {
                                                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                                                        temp_location = addresses.get(0).getAddressLine(0);
                                                        Log.d("Location =====", temp_location);

                                                        HashMap<String, Object> map = new HashMap<>();
                                                        map.put("context", context);
                                                        map.put("icon", EasyLifeUserDetails.getImageURL());
                                                        map.put("user", EasyLifeUserDetails.getUsername());
                                                        map.put("location", temp_location);
                                                        databaseReference.child("Posts").push().setValue(map);
                                                        finish();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }, Looper.getMainLooper());
                            }else{
                                turnOnGps();
                                
                            }
                        }else{
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }
                    }
                } else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("context", context);
                    map.put("icon", EasyLifeUserDetails.getImageURL());
                    map.put("user", EasyLifeUserDetails.getUsername());
                    Log.d("Location saving in database=====", temp_location);
                    map.put("location", temp_location);
                    databaseReference.child("Posts").push().setValue(map);
                    finish();
                }
            }
        });
    }

    private void turnOnGps() {



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(EasyLifeMakingPost.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(EasyLifeMakingPost.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }
}
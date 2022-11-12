package edu.northeastern.cs5520fa22groupproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.cs5520fa22groupproject.R;

public class EasyLife extends AppCompatActivity {


    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_easylife);

        FirebaseApp.initializeApp(this);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }
}
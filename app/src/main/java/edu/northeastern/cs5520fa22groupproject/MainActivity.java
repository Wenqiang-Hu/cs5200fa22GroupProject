package edu.northeastern.cs5520fa22groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button atYourService = findViewById((R.id.atyourservice));
        atYourService.setOnClickListener(this);

        Button stickButton = findViewById(R.id.button_Stick_It);
        stickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stickButton = new Intent(MainActivity.this, StartActivity.class);
                startActivity(stickButton);
            }
        });

        Button aboutMe = findViewById((R.id.aboutButton));
        aboutMe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent aboutMe = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutMe);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int theId = v.getId();
        if (theId == R.id.atyourservice) {
            Intent clicky = new Intent(MainActivity.this, AtYourService.class);
            startActivity(clicky);
        }

    }
}